package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepo extends JpaRepository<Expenses, String> {

    @Query(
            value = "SELECT * FROM expenses WHERE user_id = ?1 ORDER BY expenses_date DESC LIMIT ?2 OFFSET ?3",
            nativeQuery = true
    )
    List<Expenses> findAllExpensesByUserId(String userId, Integer limit, Integer offset);
    @Query(
            value = "SELECT * FROM expenses WHERE user_id = ?1 AND account_id = ?2 ORDER BY expenses_date DESC LIMIT ?3 OFFSET ?4",
            nativeQuery = true
    )
    List<Expenses> findAllExpensesByUserIdAndAccId(String userId, String accountId, Integer limit, Integer offset);
}
