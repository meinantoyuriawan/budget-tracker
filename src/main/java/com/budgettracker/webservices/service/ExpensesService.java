package com.budgettracker.webservices.service;

import com.budgettracker.webservices.repository.AccountRepo;
import com.budgettracker.webservices.repository.ExpensesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpensesService {

    @Autowired
    ExpensesRepo expensesRepo;

    @Autowired
    AccountRepo accountRepo;

        
}
