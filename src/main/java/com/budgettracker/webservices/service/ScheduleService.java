package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.Schedule;
import com.budgettracker.webservices.model.ScheduleResponse;
import com.budgettracker.webservices.repository.ExpensesRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    ExpensesRepo expensesRepo;

    @Autowired
    UserRepo userRepo;

    private ScheduleResponse toSchResponse(Schedule schedule) {
        ScheduleResponse newSchResp = new ScheduleResponse();
        newSchResp.setId(schedule.getId());
        newSchResp.setUser(schedule.getUserId());
        newSchResp.setExpenses(schedule.getExpenses().getId());
        newSchResp.setStart(schedule.getStartDate());
        newSchResp.setEnd(schedule.getEndDate());
        newSchResp.setByTime(schedule.getByTime());

        return newSchResp;
    }
    
}
