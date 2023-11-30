package com.budgettracker.webservices.repository;

import com.budgettracker.webservices.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<Users, String> {

}
