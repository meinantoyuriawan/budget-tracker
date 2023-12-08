package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, String> {

    List<Schedule> findByuserId(String userId);

    Schedule findByexpensesId(String expensesId);

}
