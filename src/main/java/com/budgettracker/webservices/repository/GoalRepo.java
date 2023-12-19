package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Expenses;
import com.budgettracker.webservices.model.Goal;
import com.budgettracker.webservices.model.Schedule;
import com.budgettracker.webservices.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoalRepo extends JpaRepository<Goal, String> {

    List<Goal> findByusers(Users users);

    @Query(
            value = "SELECT * FROM goal WHERE user_id = ?1 AND by_acc_id = ?2",
            nativeQuery = true
    )
    List<Goal> findAllGoalByUserIdAndAccId(String userId, String accountId);

}
