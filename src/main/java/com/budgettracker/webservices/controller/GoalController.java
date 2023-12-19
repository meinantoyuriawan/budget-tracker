package com.budgettracker.webservices.controller;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping(
            path = "/api/goals/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<GoalResponse>> get(
            @PathVariable("userId") String userId
    ) {
        List<GoalResponse> goalResponses = goalService.getAll(userId);
        WebResponse<List<GoalResponse>> result = new WebResponse<>();
        result.setData(goalResponses);

        return result;
    }

    @GetMapping(
            path = "/api/goals/{userId}/account/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<GoalResponse>> getAllByAcc(
            @PathVariable("userId") String userId,
            @PathVariable("accountId") String accountId
    ) {
        List<GoalResponse> goalResponses = goalService.getAllByAccId(userId, accountId);
        WebResponse<List<GoalResponse>> result = new WebResponse<>();
        result.setData(goalResponses);

        return result;
    }

    @GetMapping(
            path = "/api/goal/{goaId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<GoalResponse> getOne(
            @PathVariable("goalId") String goalId
    ) {
        GoalResponse goalResponses = goalService.getGoal(goalId);
        WebResponse<GoalResponse> result = new WebResponse<>();
        result.setData(goalResponses);

        return result;
    }

    @PostMapping(
            path = "/api/goal/{userId}/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<GoalResponse> add(
            @RequestBody GoalRequest request,
            @PathVariable("userId") String userId
    ) {
        request.setUserId(userId);
        GoalResponse goalResponse = goalService.add(request);
        WebResponse<GoalResponse> result = new WebResponse<>();
        result.setData(goalResponse);
        return result;
    }

    @PutMapping(
            path = "api/goal/{userId}/update/{goalId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<GoalResponse> update(
            @RequestBody GoalRequest request,
            @PathVariable("userId") String userId,
            @PathVariable("goalId") String goalId
    ) {
        request.setUserId(userId);
        request.setGoalId(goalId);
        GoalResponse goalResponse = goalService.update(request);

        WebResponse<GoalResponse> result = new WebResponse<>();
        result.setData(goalResponse);
        return result;
    }

    @DeleteMapping(
            path = "api/goal/{userId}/delete/{goalId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(
            @PathVariable("userId") String userId,
            @PathVariable("goalId") String goalId
    ) {
        goalService.remove(userId, goalId);
        WebResponse<String> result = new WebResponse<>();
        result.setData("Ok");
        return result;

    }
}
