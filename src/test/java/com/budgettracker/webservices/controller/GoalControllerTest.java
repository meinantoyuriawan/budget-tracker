package com.budgettracker.webservices.controller;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GoalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ExpensesRepo expensesRepo;

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private GoalRepo goalRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
//        be3ca79c-44f5-4b90-ae62-aa38800ac4c5
//        test
        goalRepo.deleteAll();
        scheduleRepo.deleteAll();
        expensesRepo.deleteAll();
        accountRepo.deleteAll();
        userRepo.deleteAll();
//        push users
        Users users = new Users();
        users.setId("be3ca79c-44f5-4b90-ae62-aa38800ac4c5");
        users.setEmail("meinantoyuriawan@gmail.com");
        users.setPassword("password");
        users.setName("Meinanto");
        users.setUsername("meinantoy");

        userRepo.save(users);

        Accounts accounts = new Accounts();
        accounts.setId("test");
        accounts.setAccountName("nama");
        accounts.setAccountType("tipe");
        accounts.setUsers(users);

        accountRepo.save(accounts);

        Accounts accounts2 = new Accounts();
        accounts2.setId("test1");
        accounts2.setAccountName("nama2");
        accounts2.setAccountType("tipe2");
        accounts2.setUsers(users);

        accountRepo.save(accounts2);

//        Expenses expenses = new Expenses();
//        expenses.setId("testExpenses");
//        expenses.setUserId("be3ca79c-44f5-4b90-ae62-aa38800ac4c5");
//        expenses.setDate(LocalDate.of(2023, 10, 2));
//        expenses.setAccounts(accounts);
//        expenses.setAmount(20000L);
//        expenses.setExpensesType("Food");
//        expenses.setDescription("some description");
//
//        expensesRepo.save(expenses);
//
//        Expenses expenses2 = new Expenses();
//        expenses2.setId("testExpenses2");
//        expenses2.setUserId("be3ca79c-44f5-4b90-ae62-aa38800ac4c5");
//        expenses2.setDate(LocalDate.of(2023, 10, 2));
//        expenses2.setAccounts(accounts);
//        expenses2.setAmount(20000L);
//        expenses2.setExpensesType("Food");
//        expenses2.setDescription("some description");
//
//        expensesRepo.save(expenses2);
    }

    @Test
    void testCreateGoalSuccess() throws Exception{
        GoalRequest request = new GoalRequest();
        request.setTime("Weekly");
        request.setAccount("test");
        request.setAmount(200000L);

        mockMvc.perform(
                post("/api/goal/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<GoalResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());

                    assertEquals(request.getTime(), response.getData().getTime());
                    assertEquals(request.getAccount(), response.getData().getAccount());
                    assertEquals(request.getAmount(), response.getData().getAmount());
                }
        );
    }

    @Test
    void testCreateGoalUserNotExist() throws Exception {
        // User doesn't exist
        GoalRequest request = new GoalRequest();
        request.setTime("Weekly");
        request.setAccount("test");
        request.setAmount(200000L);

        // userId = be3ca79c-44f5-4b90-ae62-aa38800ac4c5
        mockMvc.perform(
                post("/api/goal/user-test/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<GoalResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }
    // push bad request

    void pushData() throws Exception {
        String userId = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Users users = userRepo.findById(userId).orElseThrow();

        for (int i=0; i<6; i++) {
            Goal newGoal = new Goal();
            newGoal.setId("testExp-" + i);
            newGoal.setUsers(users);
            newGoal.setByTime("Monthly");
            newGoal.setAmount(500000L);

            if (i%2 == 0) {
                newGoal.setByAccount("test");
            } else {
                newGoal.setByAccount("test1");
            }

            goalRepo.save(newGoal);
        }
    }

    // test GetAll
    @Test
    void testGetAll() throws Exception {
        pushData();

        // get all accounts
        mockMvc.perform(
                get("/api/goals/be3ca79c-44f5-4b90-ae62-aa38800ac4c5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<GoalResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                    assertEquals(6, response.getData().size());
                }
        );

    }

    @Test
    void testGetByAcc() throws Exception {
        pushData();
        mockMvc.perform(
                get("/api/goals/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/account/test1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<GoalResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                    assertEquals(3, response.getData().size());
                }
        );
    }

    @Test
    void testGetAllAccNotExist() throws Exception {
        pushData();
        mockMvc.perform(
                get("/api/goals/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/account/randomAcc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<GoalResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

    // test Get one
    @Test
    void testGetOne() throws Exception {
        pushData();
        mockMvc.perform(
                get("/api/goal/testExp-1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<GoalResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                }
        );
    }

    @Test
    void testGetOneGoalNotExist() throws Exception {
        pushData();
        mockMvc.perform(
                get("/api/goal/notExist-1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<GoalResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

    // test Put
    @Test
    void testEditGoal() throws Exception {
        pushData();

//        String expId = "testExp-1";

        GoalRequest request = new GoalRequest();
        request.setTime("Daily");
        request.setAccount("test");
        request.setAmount(20000L);


        MvcResult res =
                mockMvc.perform(
                        put("/api/goal/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/update/testExp-1")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andExpectAll(
                        status().isOk()
                ).andDo(
                        result -> {
                            WebResponse<GoalResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                            });
                            assertNull(response.getError());

//                    assertEquals(request.getDate(), response.getData());
                            //
                            assertEquals(request.getTime(), response.getData().getTime());
                            assertEquals(request.getAccount(), response.getData().getAccount());
                            assertEquals(request.getAmount(), response.getData().getAmount());

                        }
                ).andReturn();
        // I want to get the date better
        String content = res.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    void testEditGoalNotExist() throws Exception {
        pushData();

//        String expId = "testExp-1";

        GoalRequest request = new GoalRequest();
        request.setTime("Daily");
        request.setAccount("test");
        request.setAmount(20000L);


        mockMvc.perform(
                put("/api/goal/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/update/NoGoal-1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<ExpensesResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

    @Test
    void testEditGoalUserNotExist() throws Exception {
        pushData();

//        String expId = "testExp-1";

        GoalRequest request = new GoalRequest();
        request.setTime("Daily");
        request.setAccount("test");
        request.setAmount(20000L);


        mockMvc.perform(
                put("/api/goal/NoUser/update/NoGoal-1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<ExpensesResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

    @Test
    void testDeleteGoal() throws Exception {
        String userId = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Users users = userRepo.findById(userId).orElseThrow();


        Goal newGoal = new Goal();
        newGoal.setId("testGoal");
        newGoal.setUsers(users);
        newGoal.setByTime("Monthly");
        newGoal.setAmount(500000L);
        newGoal.setByAccount("test");


        goalRepo.save(newGoal);


        mockMvc.perform(
                delete("/api/goal/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/delete/testGoal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                    assertEquals("Ok", response.getData());

                    assertFalse(goalRepo.existsById("testGoal"));
                }
        );
    }

    @Test
    void testDeleteExpNotExist() throws Exception {
        String userId = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Users users = userRepo.findById(userId).orElseThrow();


        Goal newGoal = new Goal();
        newGoal.setId("testGoal");
        newGoal.setUsers(users);
        newGoal.setByTime("Monthly");
        newGoal.setAmount(500000L);
        newGoal.setByAccount("test");


        goalRepo.save(newGoal);
        mockMvc.perform(
                delete("/api/goal/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/delete/randomGoal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

}
