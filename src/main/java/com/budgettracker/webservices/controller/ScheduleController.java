package com.budgettracker.webservices.controller;

import com.budgettracker.webservices.model.ExpensesResponse;
import com.budgettracker.webservices.model.ScheduleRequest;
import com.budgettracker.webservices.model.ScheduleResponse;
import com.budgettracker.webservices.model.WebResponse;
import com.budgettracker.webservices.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(
            path = "/api/schedules/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ScheduleResponse>> get(
            @PathVariable("userId") String userId
    ) {
        List<ScheduleResponse> scheduleResponses = scheduleService.getAll(userId);
        WebResponse<List<ScheduleResponse>> result = new WebResponse<>();
        result.setData(scheduleResponses);
        return result;
    }

    @GetMapping(
            path = "/api/schedule/{scheduleId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ScheduleResponse> getSchedule(
            @PathVariable("scheduleId") String scheduleId
    ) {
        ScheduleResponse scheduleResponse = scheduleService.getSchedule(scheduleId);
        WebResponse<ScheduleResponse> result = new WebResponse<>();
        result.setData(scheduleResponse);
        return result;
    }

    @PostMapping(
            path = "/api/schedule/{expensesId}/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ScheduleResponse> add(
            @RequestBody ScheduleRequest request,
            @PathVariable("expensesId") String expensesId
    ) {
        request.setExpensesId(expensesId);
        ScheduleResponse scheduleResponse = scheduleService.add(request);
        WebResponse<ScheduleResponse> result = new WebResponse<>();
        result.setData(scheduleResponse);
        return result;
    }

    @PutMapping(
            path = "/api/schedule/{expensesId}/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ScheduleResponse> update(
            @RequestBody ScheduleRequest request,
            @PathVariable("expensesId") String expensesId
    ) {
        request.setExpensesId(expensesId);
        ScheduleResponse scheduleResponse = scheduleService.update(request);
        WebResponse<ScheduleResponse> result = new WebResponse<>();
        result.setData(scheduleResponse);
        return result;
    }

    @DeleteMapping(
            path = "/api/schedule/{expensesId}/delete",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(
            @PathVariable("expensesId") String expensesId
    ) {
        scheduleService.remove(expensesId);
        WebResponse<String> result = new WebResponse<>();
        result.setData("Ok");
        return result;
    }
}
