package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.repository.GoalRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

    // Get All Goal
    public List<GoalResponse> getAll(String userId){
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        List<Goal> goalList = goalRepo.findByusers(users);
        if (goalList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Goal yet");
        }

        return goalList.stream().map(this::toGoalResponse).toList();
    }

    // Get All Goal by AccId
    public List<GoalResponse> getAllByAccId(String userId, String accId){
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        List<Goal> goalList = goalRepo.findAllGoalByUserIdAndAccId(userId, accId);
        if (goalList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Goal yet");
        }

        return goalList.stream().map(this::toGoalResponse).toList();
    }

    // Get Goal
    public GoalResponse getGoal(String goalId){
        Goal goal = goalRepo.findById(goalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal doesn't exist"));

        return toGoalResponse(goal);
    }

    public GoalResponse add(GoalRequest goalRequest){
        Users users = userRepo.findById(goalRequest.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Goal newGoal = new Goal();

        newGoal.setId(UUID.randomUUID().toString());
        newGoal.setAmount(goalRequest.getAmount());
        newGoal.setByAccount(goalRequest.getAccount());
        newGoal.setByTime(goalRequest.getTime());
        newGoal.setUsers(users);

        goalRepo.save(newGoal);
        return toGoalResponse(newGoal);
    }

    public GoalResponse update(GoalRequest goalRequest){
        Users users = userRepo.findById(goalRequest.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Goal goal = goalRepo.findById(goalRequest.getGoalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal doesn't exist"));

        goal.setByTime(goalRequest.getTime());
        goal.setByAccount(goalRequest.getAccount());
        goal.setAmount(goalRequest.getAmount());

        goalRepo.save(goal);

        return toGoalResponse(goal);
    }

    public void remove(String userId, String goalId){
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Goal goal = goalRepo.findById(goalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal doesn't exist"));

        goalRepo.deleteById(goalId);
    }
}
