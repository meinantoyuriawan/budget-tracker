package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Accounts;
import com.budgettracker.webservices.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Accounts, String> {
    List<Accounts> findAll(Users users);
}
