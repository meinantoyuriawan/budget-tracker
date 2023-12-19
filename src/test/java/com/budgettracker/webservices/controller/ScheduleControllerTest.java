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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerTest {

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

        Expenses expenses = new Expenses();
        expenses.setId("testExpenses");
        expenses.setUserId("be3ca79c-44f5-4b90-ae62-aa38800ac4c5");
        expenses.setDate(LocalDate.of(2023, 10, 2));
        expenses.setAccounts(accounts);
        expenses.setAmount(20000L);
        expenses.setExpensesType("Food");
        expenses.setDescription("some description");

        expensesRepo.save(expenses);

        Expenses expenses2 = new Expenses();
        expenses2.setId("testExpenses2");
        expenses2.setUserId("be3ca79c-44f5-4b90-ae62-aa38800ac4c5");
        expenses2.setDate(LocalDate.of(2023, 10, 2));
        expenses2.setAccounts(accounts);
        expenses2.setAmount(20000L);
        expenses2.setExpensesType("Food");
        expenses2.setDescription("some description");

        expensesRepo.save(expenses2);
    }

    @Test
    void testCreateSchedule() throws Exception{
        ScheduleRequest request = new ScheduleRequest();
        request.setExpensesId("testSchedule");
        request.setStart("11/12/2023");
        request.setEnd("25/12/2023");
        request.setByTime("Weekly");

        mockMvc.perform(
                post("/api/schedule/testExpenses/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ScheduleResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());

                    assertEquals(request.getStart(),response.getData().getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    assertEquals(request.getEnd(),response.getData().getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    assertEquals(request.getByTime(), response.getData().getByTime());

                }
        );
    }

    @Test
    void testCreateSchExpNotExist() throws Exception{
        ScheduleRequest request = new ScheduleRequest();
        request.setExpensesId("testSchedule");
        request.setStart("11/12/2023");
        request.setEnd("25/12/2023");
        request.setByTime("Weekly");

        mockMvc.perform(
                post("/api/schedule/notAvailable/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<ScheduleResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());

                }
        );
    }

    void pushData() throws Exception {
        String userId = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Expenses exp = expensesRepo.findById("testExpenses").orElseThrow();
        Expenses exp2 = expensesRepo.findById("testExpenses2").orElseThrow();

        Schedule newSch = new Schedule();
        newSch.setId("testSch-1");
        newSch.setUserId(userId);
        newSch.setExpenses(exp);
        newSch.setStartDate(LocalDate.of(2023, 5, 3));
        newSch.setEndDate(LocalDate.of(2023, 6, 3));
        newSch.setByTime("Weekly");

        Schedule newSch2 = new Schedule();
        newSch2.setId("testSch-2");
        newSch2.setUserId(userId);
        newSch2.setExpenses(exp2);
        newSch2.setStartDate(LocalDate.of(2023, 5, 4));
        newSch2.setEndDate(LocalDate.of(2023, 6, 4));
        newSch2.setByTime("Weekly");

        scheduleRepo.save(newSch);
        scheduleRepo.save(newSch2);
    }

    @Test
    void testGetAll() throws Exception {
        pushData();

        mockMvc.perform(
                get("/api/schedules/be3ca79c-44f5-4b90-ae62-aa38800ac4c5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<ScheduleResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                    assertEquals(2, response.getData().size());
                }
        );
    }

    @Test
    void testGetExp() throws Exception {
        pushData();

        mockMvc.perform(
                get("/api/schedule/testSch-2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ScheduleResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                }
        );
    }

    @Test
    void testGetAllNoExp() throws Exception {
        pushData();

        mockMvc.perform(
                get("/api/schedules/noAcc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<List<ScheduleResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

    @Test
    void testGetExpNoExist() throws Exception {
        pushData();

        mockMvc.perform(
                get("/api/schedule/noSch")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<ScheduleResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

    @Test
    void testEditSch() throws Exception {
        pushData();

        ScheduleRequest request = new ScheduleRequest();
        request.setStart("01/01/2023");
        request.setEnd("01/08/2023");
        request.setByTime("Monthly");

        mockMvc.perform(
                put("/api/schedule/testExpenses/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ScheduleResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());

                    assertEquals(request.getStart(),response.getData().getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    assertEquals(request.getEnd(),response.getData().getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    assertEquals(request.getByTime(), response.getData().getByTime());


                }
        );
    }

    @Test
    void testEditSchError() throws Exception {
        pushData();

        ScheduleRequest request = new ScheduleRequest();
        request.setStart("01/01/2023");
        request.setEnd("01/08/2023");
        request.setByTime("Monthly");

        mockMvc.perform(
                put("/api/schedule/notexist/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<List<ScheduleResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());

                }
        );
    }

    @Test
    void testDeleteSch() throws Exception {
        pushData();

        mockMvc.perform(
                delete("/api/schedule/testExpenses/delete")
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
    void testDeleteSchNotExist() throws Exception {

        mockMvc.perform(
                delete("/api/schedule/random/delete")
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
