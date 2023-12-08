package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.repository.ExpensesRepo;
import com.budgettracker.webservices.repository.ScheduleRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepo scheduleRepo;
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


    //Get All Schedule
    public List<ScheduleResponse> getAll(String userId){
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        List<Schedule> scheduleList = scheduleRepo.findByuserId(userId);
        if (scheduleList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Schedule yet");
        }
        return scheduleList.stream().map(this::toSchResponse).toList();
    }

    //Get Schedule
    public ScheduleResponse getSchedule(String scheduleId){
        Schedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule doesn't exist"));
        return toSchResponse(schedule);
    }

    //Add Schedule
    public ScheduleResponse add(ScheduleRequest request) {

        Expenses expenses = expensesRepo.findById(request.getExpensesId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenses doesn't exist"));

        Schedule newSch = new Schedule();

        int yearStart = Integer.parseInt(request.getYYStart());
        int monthStart = Integer.parseInt(request.getMMStart());
        int dayStart = Integer.parseInt(request.getDDStart());

        int yearEnd = Integer.parseInt(request.getYYEnd());
        int monthEnd = Integer.parseInt(request.getMMEnd());
        int dayEnd = Integer.parseInt(request.getDDEnd());

        newSch.setId(UUID.randomUUID().toString());
        newSch.setUserId(expenses.getUserId());
        newSch.setStartDate(LocalDate.of(yearStart, monthStart, dayStart));
        newSch.setEndDate(LocalDate.of(yearEnd, monthEnd, dayEnd));
        newSch.setByTime(request.getByTime());
        newSch.setExpenses(expenses);

        scheduleRepo.save(newSch);

        return toSchResponse(newSch);
    }


    //Update Schedule
    public ScheduleResponse update(ScheduleRequest request) {
        Expenses expenses = expensesRepo.findById(request.getExpensesId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenses doesn't exist"));

        Schedule schedule = scheduleRepo.findByexpensesId(expenses.getId());

        int yearStart = Integer.parseInt(request.getYYStart());
        int monthStart = Integer.parseInt(request.getMMStart());
        int dayStart = Integer.parseInt(request.getDDStart());

        int yearEnd = Integer.parseInt(request.getYYEnd());
        int monthEnd = Integer.parseInt(request.getMMEnd());
        int dayEnd = Integer.parseInt(request.getDDEnd());

        schedule.setStartDate(LocalDate.of(yearStart, monthStart, dayStart));
        schedule.setEndDate(LocalDate.of(yearEnd, monthEnd, dayEnd));
        schedule.setByTime(request.getByTime());

        scheduleRepo.save(schedule);
        return toSchResponse(schedule);

    }

    //Delete Schedule
    public void remove(String expId){
        Expenses expenses = expensesRepo.findById(expId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenses doesn't exist"));

        Schedule schedule = scheduleRepo.findByexpensesId(expenses.getId());

        scheduleRepo.deleteById(schedule.getId());
    }
}
