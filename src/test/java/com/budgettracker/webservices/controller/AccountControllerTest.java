package com.budgettracker.webservices.controller;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        AddAccountRequest request = new AddAccountRequest();
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
        AddAccountRequest request = new AddAccountRequest();
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
        AddAccountRequest request = new AddAccountRequest();
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
        AddAccountRequest request = new AddAccountRequest();
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
        AddAccountRequest request = new AddAccountRequest();
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
        AddAccountRequest request = new AddAccountRequest();
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
        AddAccountRequest request = new AddAccountRequest();
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
    @Test
    void testEditAcc() throws  Exception {
        // push new data
        String id = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Users user = userRepo.findById(id).orElseThrow();
        Accounts newAcc = new Accounts();
        newAcc.setId("test");
        newAcc.setAccountName("nama");
        newAcc.setAccountType("tipe");
        newAcc.setUsers(user);
        accountRepo.save(newAcc);

        // edit
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setName("updated name");
        request.setType("updated type");
        mockMvc.perform(
                put("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/update/test")
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
                    assertEquals("test", response.getData().getId());
                    assertEquals(request.getName(), response.getData().getName());
                    assertEquals(request.getType(), response.getData().getType());

                    assertTrue(accountRepo.existsById(response.getData().getId()));
                }
        );
    }

    @Test
    void testEditAccNotExist() throws  Exception {
        // push new data
        String id = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Users user = userRepo.findById(id).orElseThrow();
        Accounts newAcc = new Accounts();
        newAcc.setId("test");
        newAcc.setAccountName("nama");
        newAcc.setAccountType("tipe");
        newAcc.setUsers(user);
        accountRepo.save(newAcc);

        // edit
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setName("updated name");
        request.setType("updated type");
        mockMvc.perform(
                put("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/update/differentAcc")
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

    // test Delete
    @Test
    void testDeleteAcc() throws Exception {
        // push new data
        String id = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Users user = userRepo.findById(id).orElseThrow();
        Accounts newAcc = new Accounts();
        newAcc.setId("test");
        newAcc.setAccountName("nama");
        newAcc.setAccountType("tipe");
        newAcc.setUsers(user);
        accountRepo.save(newAcc);
        mockMvc.perform(
                delete("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/delete/test")
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

                    assertFalse(accountRepo.existsById("test"));
                }
        );
    }

    @Test
    void testDeleteAccNotExist() throws Exception {
        // push new data
        String id = "be3ca79c-44f5-4b90-ae62-aa38800ac4c5";
        Users user = userRepo.findById(id).orElseThrow();
        Accounts newAcc = new Accounts();
        newAcc.setId("test");
        newAcc.setAccountName("nama");
        newAcc.setAccountType("tipe");
        newAcc.setUsers(user);
        accountRepo.save(newAcc);
        mockMvc.perform(
                delete("/api/accounts/be3ca79c-44f5-4b90-ae62-aa38800ac4c5/delete/differentAcc")
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
