package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, String> {

}
