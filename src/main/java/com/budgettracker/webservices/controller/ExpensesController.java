package com.budgettracker.webservices.controller;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;

    @GetMapping(
            path = "/api/expenses/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ExpensesResponse>> get(
            @PathVariable("userId") String userId,
            @RequestParam Integer limit,
            @RequestParam Integer offset
    ) {
        List<ExpensesResponse> expensesResponses = expensesService.getAll(userId, limit, offset);
        WebResponse<List<ExpensesResponse>> result = new WebResponse<>();
        result.setData(expensesResponses);
        return result;
    }

    @GetMapping(
            path = "/api/expenses/{userId}/account/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ExpensesResponse>> getByAcc(
            @PathVariable("userId") String userId,
            @PathVariable("accountId") String accId,
            @RequestParam Integer limit,
            @RequestParam Integer offset
    ) {
        List<ExpensesResponse> expensesResponses = expensesService.getAllByAcc(userId, accId, limit, offset);
        WebResponse<List<ExpensesResponse>> result = new WebResponse<>();
        result.setData(expensesResponses);
        return result;
    }

    @PostMapping(
            path = "/api/expenses/{userId}/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ExpensesResponse> add(
            @RequestBody AddExpsensesRequest request,
            @PathVariable("userId") String userId
    ) {
        request.setUserId(userId);
        ExpensesResponse expensesResponse = expensesService.add(request);
        WebResponse<ExpensesResponse> result = new WebResponse<>();
        result.setData(expensesResponse);
        return result;
    }

    @PutMapping(
            path = "api/expenses/{userId}/update/{expensesId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ExpensesResponse> update(
            @RequestBody UpdateExpensesRequest request,
            @PathVariable("userId") String userId,
            @PathVariable("expensesId") String expId
    ) {
        request.setId(expId);
        request.setUserId(userId);
        ExpensesResponse expensesResponse = expensesService.update(request);

        WebResponse<ExpensesResponse> result = new WebResponse<>();
        result.setData(expensesResponse);
        return result;
    }

    @DeleteMapping(
            path = "api/expenses/{userId}/delete/{expensesId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(
            @PathVariable("userId") String userId,
            @PathVariable("expensesId") String expensesId
    ) {
        expensesService.remove(expensesId, userId);
        WebResponse<String> result = new WebResponse<>();
        result.setData("Ok");
        return result;

    }
}
