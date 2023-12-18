package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepo extends JpaRepository<Goal, String> {
}
