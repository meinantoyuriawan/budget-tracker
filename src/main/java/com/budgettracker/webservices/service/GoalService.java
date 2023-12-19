package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.Goal;
import com.budgettracker.webservices.model.GoalResponse;
import com.budgettracker.webservices.repository.GoalRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    GoalRepo goalRepo;

    private GoalResponse toGoalResponse(Goal goal){
        GoalResponse newGoalResponse = new GoalResponse();
        newGoalResponse.setId(goal.getId());
        newGoalResponse.setTime(goal.getByTime());
        newGoalResponse.setAccount(goal.getByAccount());
        newGoalResponse.setAmount(goal.getAmount());

        return newGoalResponse;
    }

    
}
