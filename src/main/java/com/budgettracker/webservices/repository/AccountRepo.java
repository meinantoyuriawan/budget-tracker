package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Accounts, String> {
    List<Accounts> findByUserId(String users);
}
