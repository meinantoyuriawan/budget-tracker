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
    }

    @Test
    void testCreateExpSuccess() throws Exception{
        AddExpsensesRequest request = new AddExpsensesRequest();
        request.setDate("07/05/2023");
        request.setAccount("test");
        request.setAmount(20000L);
        request.setType("Urgent");
        request.setDescription("I got injured");

        MvcResult res =
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

    // test GetAll

    // test Put

    // test Delete

}
