package com.budgettracker.webservices.controller;

import com.budgettracker.webservices.model.AccountResponse;
import com.budgettracker.webservices.model.AddAccountRequest;
import com.budgettracker.webservices.model.UpdateAccountRequest;
import com.budgettracker.webservices.model.WebResponse;
import com.budgettracker.webservices.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(
            path = "/api/accounts/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AccountResponse>> get(@PathVariable("userId") String userId) {
        List<AccountResponse> accountResponse = accountService.getAll(userId);
        WebResponse<List<AccountResponse>> result = new WebResponse<>();
        result.setData(accountResponse);
        return result;
    }

    @PostMapping(
            path = "/api/accounts/{userId}/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AccountResponse> add(
            @RequestBody AddAccountRequest request,
            @PathVariable("userId") String userId
    ) {
        request.setUserId(userId);
        AccountResponse accountResponse = accountService.add(request);
        WebResponse<AccountResponse> result = new WebResponse<>();
        result.setData(accountResponse);
        return result;
    }

    @PutMapping(
            path = "api/accounts/{userId}/update/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AccountResponse> update(
            @RequestBody UpdateAccountRequest request,
            @PathVariable("userId") String userId,
            @PathVariable("accountId") String accountId
    ) {
        request.setAccId(accountId);
        request.setUserId(userId);
        AccountResponse accountResponse = accountService.update(request);

        WebResponse<AccountResponse> result = new WebResponse<>();
        result.setData(accountResponse);
        return result;
    }

    @DeleteMapping(
            path = "api/accounts/{userId}/delete/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(
            @PathVariable("userId") String userId,
            @PathVariable("accountId") String accountId
    ) {
        accountService.remove(accountId, userId);
        WebResponse<String> result = new WebResponse<>();
        result.setData("Ok");
        return result;

    }

}
