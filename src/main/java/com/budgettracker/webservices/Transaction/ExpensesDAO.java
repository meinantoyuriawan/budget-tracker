package com.budgettracker.webservices.Transaction;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExpensesDAO {

    private static List<Expenses> expenses = new ArrayList<>();

    static {
        expenses.add(new Expenses(1, 1, LocalDate.now(), 1, 10000, "Food", "Padang Rice"));
        expenses.add(new Expenses(2, 1, LocalDate.now(), 1, 50000, "Housing", "Internet"));
        expenses.add(new Expenses(3, 1, LocalDate.now(), 1, 10000, "Personal Spending", "Gym"));
    }
}
