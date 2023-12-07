package com.budgettracker.webservices.controller;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.repository.AccountRepo;
import com.budgettracker.webservices.repository.ExpensesRepo;
import com.budgettracker.webservices.repository.UserRepo;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpensesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ExpensesRepo expensesRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
//        be3ca79c-44f5-4b90-ae62-aa38800ac4c5
//        test
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
    }

    @Test
    void testCreateExpSuccess() throws Exception{
        AddExpsensesRequest request = new AddExpsensesRequest();
        request.setDate("07/05/2023");
        request.setAccount("test");
        request.setAmount(20000L);
        request.setType("Urgent");
        request.setDescription("I got injured");

        mockMvc.perform(
                post("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ExpensesResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());

                    assertEquals(request.getDate(), response.getData().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    //
                    assertEquals(request.getAccount(), response.getData().getAccount());
                    assertEquals(request.getAmount(), response.getData().getAmount());
                    assertEquals(request.getType(), response.getData().getType());
                    assertEquals(request.getDescription(), response.getData().getDescription());

                }
        );
    }

    @Test
    void testCreateExpUserNotExist() throws Exception {
        // User doesn't exist
        AddExpsensesRequest request = new AddExpsensesRequest();
        request.setDate("07/05/2023");
        request.setAccount("test");
        request.setAmount(20000L);
        request.setType("Urgent");
        request.setDescription("I got injured");
        // userId = be3ca79c-44f5-4b90-ae62-aa38800ac4c5
        mockMvc.perform(
                post("/api/expenses/user-test/create")
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
    // push bad request

    // push data for test GetAll
    void pushData() throws Exception {
        String userId = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Accounts accounts1 = accountRepo.findById("test").orElseThrow();
        Accounts accounts2 = accountRepo.findById("test1").orElseThrow();

        for (int i=0; i<6; i++){
            Expenses newExp = new Expenses();
            newExp.setId("testExp-" + i);
            newExp.setUserId(userId);
            newExp.setDate(LocalDate.of(2023, 5, 6 + i));
            newExp.setAmount(20000L + (10*i));
            newExp.setExpensesType("Utilities");
            newExp.setDescription("Description");

            if (i%2 == 0) {
                newExp.setAccounts(accounts1);
            } else {
                newExp.setAccounts(accounts2);
            }

            expensesRepo.save(newExp);
        }
    }
    // test GetAll
    @Test
    void testGetAll() throws Exception {
        pushData();

        // get all accounts
        mockMvc.perform(
                get("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5?limit=10&offset=0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ExpensesResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
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
                get("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/account/test?limit=10&offset=0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ExpensesResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
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
                get("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/account/randomAcc?limit=10&offset=0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
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
    // test Put
    @Test
    void testEditExp() throws Exception {
        pushData();

//        String expId = "testExp-1";

        UpdateExpensesRequest request = new UpdateExpensesRequest();
        request.setDate("07/05/2020");
        request.setAccount("test");
        request.setAmount(80000L);
        request.setType("Urgent");
        request.setDescription("New Description");

        MvcResult res =
        mockMvc.perform(
                put("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/update/testExp-1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ExpensesResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());

//                    assertEquals(request.getDate(), response.getData());
                    //
                    assertEquals(request.getAccount(), response.getData().getAccount());
                    assertEquals(request.getAmount(), response.getData().getAmount());
                    assertEquals(request.getType(), response.getData().getType());
                    assertEquals(request.getDescription(), response.getData().getDescription());
                }
        ).andReturn();
        // I want to get the date better
        String content = res.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    void testEditExpAccNotExist() throws Exception {
        pushData();

//        String expId = "testExp-1";

        UpdateExpensesRequest request = new UpdateExpensesRequest();
        request.setDate("07/05/2020");
        request.setAccount("not exist");
        request.setAmount(80000L);
        request.setType("Urgent");
        request.setDescription("New Description");

        mockMvc.perform(
                put("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/update/testExp-1")
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
    void testEditExpNotExist() throws Exception {
        pushData();

//        String expId = "testExp-1";

        UpdateExpensesRequest request = new UpdateExpensesRequest();
        request.setDate("07/05/2020");
        request.setAccount("test");
        request.setAmount(80000L);
        request.setType("Urgent");
        request.setDescription("New Description");

        mockMvc.perform(
                put("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/update/NotExistExp")
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

    // test Delete

    @Test
    void testDeleteExp() throws Exception {
        String userId = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Accounts accounts1 = accountRepo.findById("test").orElseThrow();


        Expenses newExp = new Expenses();
        newExp.setId("testExp-1");
        newExp.setUserId(userId);
        newExp.setDate(LocalDate.of(2023, 5, 6));
        newExp.setAmount(20000L);
        newExp.setExpensesType("Utilities");
        newExp.setDescription("Description");

        newExp.setAccounts(accounts1);


        expensesRepo.save(newExp);
        mockMvc.perform(
                delete("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/delete/testExp-1")
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

                    assertFalse(expensesRepo.existsById("testExp-1"));
                }
        );
    }

    @Test
    void testDeleteExpNotExist() throws Exception {
        String userId = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Accounts accounts1 = accountRepo.findById("test").orElseThrow();


        Expenses newExp = new Expenses();
        newExp.setId("testExp-1");
        newExp.setUserId(userId);
        newExp.setDate(LocalDate.of(2023, 5, 6));
        newExp.setAmount(20000L);
        newExp.setExpensesType("Utilities");
        newExp.setDescription("Description");

        newExp.setAccounts(accounts1);


        expensesRepo.save(newExp);
        mockMvc.perform(
                delete("/api/expenses/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/delete/randomExpenses")
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
