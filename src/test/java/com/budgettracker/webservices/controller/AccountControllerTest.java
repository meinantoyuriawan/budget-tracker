package com.budgettracker.webservices.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.budgettracker.webservices.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.budgettracker.webservices.repository.AccountRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.*;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
//        be3ca79c-44f5-4b90-ae62-aa38800ac4c5
        accountRepo.deleteAll();
    }

    @Test
    // push to acc db
    void testPushAccSuccess() throws Exception {
        GetAddAccountRequest request = new GetAddAccountRequest();
        request.setName("New account");
        request.setType("Debit");
        // userId = be3ca79c-44f5-4b90-ae62-aa38800ac4c5
        mockMvc.perform(
                post("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<AccountResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                    assertEquals(request.getName(), response.getData().getName());
                    assertEquals(request.getType(), response.getData().getType());

                    assertTrue(accountRepo.existsById(response.getData().getId()));
                }
        );
    }
    @Test
    void testPushAccUserNotExist() throws Exception {
        // User doesn't exist
        GetAddAccountRequest request = new GetAddAccountRequest();
        request.setName("New account");
        request.setType("Debit");
        // userId = be3ca79c-44f5-4b90-ae62-aa38800ac4c5
        mockMvc.perform(
                post("/api/accounts/user-test/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<AccountResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }
    @Test
    void testPushAccExist() throws Exception {
        // Account Name already exist
        testPushAccSuccess();
        GetAddAccountRequest request = new GetAddAccountRequest();
        request.setName("New account");
        request.setType("Debit");
        // userId = be3ca79c-44f5-4b90-ae62-aa38800ac4c5
        mockMvc.perform(
                post("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<AccountResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                }
        );
    }

//    @Test
    void testPushAccBadReq() throws  Exception {
        GetAddAccountRequest request = new GetAddAccountRequest();
        request.setName("");
        request.setType("");
        // userId = be3ca79c-44f5-4b90-ae62-aa38800ac4c5
        mockMvc.perform(
                post("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<AccountResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                }
        );
    }

    // test GetAll
    @Test
    void testGetAll() throws Exception {
        String id = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";

        // push 3 acc
        Users user = userRepo.findById(id).orElseThrow();
        for (int i=0; i<3; i++){
            Accounts newAcc = new Accounts();
            newAcc.setId("test-" + i);
            newAcc.setAccountName("nama");
            newAcc.setAccountType("tipe");
            newAcc.setUsers(user);
            accountRepo.save(newAcc);
        }
        // get 3 acc
        GetAddAccountRequest request = new GetAddAccountRequest();
        mockMvc.perform(
                get("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<AccountResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                    assertEquals(3, response.getData().size());
                }
        );
    }

    @Test
    void testGetAllUserNotExist() throws Exception {
        GetAddAccountRequest request = new GetAddAccountRequest();
        mockMvc.perform(
                get("/api/accounts/random-userid")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<List<AccountResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }

    @Test
    void testGetAllNoAccount() throws Exception {
        GetAddAccountRequest request = new GetAddAccountRequest();
        mockMvc.perform(
                get("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<List<AccountResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                }
        );
    }
    // test Put
    // test Delete
}
