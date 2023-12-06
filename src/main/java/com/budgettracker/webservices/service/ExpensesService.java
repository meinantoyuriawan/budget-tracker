package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.repository.AccountRepo;
import com.budgettracker.webservices.repository.ExpensesRepo;
import com.budgettracker.webservices.repository.UserRepo;
import jakarta.persistence.EntityManager;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpensesService {

    @Autowired
    ExpensesRepo expensesRepo;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    UserRepo userRepo;

    // Expenses Response
    private ExpensesResponse toExpResponse(Expenses expenses) {
        ExpensesResponse newExpResp = new ExpensesResponse();
        newExpResp.setId(expenses.getId());
        newExpResp.setDate(expenses.getDate());
        newExpResp.setAccount(expenses.getAccounts().getId());
        newExpResp.setAmount(expenses.getAmount());
        newExpResp.setType(expenses.getExpensesType());
        newExpResp.setDescription(expenses.getDescription());
        return newExpResp;
    }

    //Get All expenses
        //All date
        //Filter by time variable
            //params Year/Month/Week -> tbc
        //Filter by accountId
    private EntityManager em;
    //Get All Account
    public List<ExpensesResponse> getAll(String userId, Integer limit, Integer offset) {
        String jplQ = "select p from Expenses p where p.user_id = :id order by p.date desc";

        var query =
                em.createNamedQuery(jplQ, ExpensesResponse.class);
        query.setParameter("id", userId);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<ExpensesResponse> result = query.getResultList();
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses yet");
        }
        return query.getResultList();
    }

    public List<ExpensesResponse> getAllByAcc(String userId, String accId, Integer limit, Integer offset) {
        String jplQ = "select p from Expenses p where p.user_id = :id and p.account_id = :accId order by p.date desc";

        var query =
                em.createNamedQuery(jplQ, ExpensesResponse.class);
        query.setParameter("id", userId);
        query.setParameter("accId", accId);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<ExpensesResponse> result = query.getResultList();
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses yet");
        }
        return query.getResultList();
    }

//    upcoming feature, by date
//    public List<ExpensesResponse> getAllByDate(String userId, Integer limit, Integer offset) {
//      String jplQ = "select p from Expenses p where p.user_id = :id and p.account_id = :accId order by p.date desc";
//      String jplQ = "select p from Expenses p where p.user_id = :id  order by p.date desc";
//        var query =
//                em.createNamedQuery(jplQ, ExpensesResponse.class);
//        query.setParameter("id", userId);
//        query.setParameter("accId", accId);
//        query.setFirstResult(offset);
//        query.setMaxResults(limit);
//
//        List<ExpensesResponse> result = query.getResultList();
//        if (result.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses yet");
//        }
//        return query.getResultList();
//    }

    //Add Expenses

    public ExpensesResponse add(AddExpsensesRequest request) {

        Users users = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Accounts accounts = accountRepo.findById(request.getAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "account doesn't exist"));

        Expenses newExp = new Expenses();

        int year = Integer.parseInt(request.getYY());
        int month = Integer.parseInt(request.getMM());
        int day = Integer.parseInt(request.getDD());


        newExp.setUserId(request.getUserId());
        newExp.setDate(LocalDate.of(year, month, day));
        newExp.setAmount(request.getAmount());
        newExp.setExpensesType(request.getType());
        newExp.setDescription(request.getDescription());
        newExp.setAccounts(accounts);

        expensesRepo.save(newExp);

        return toExpResponse(newExp);
    }

    //Update Expenses
    public ExpensesResponse update(UpdateExpensesRequest request) {

        Accounts newAcc = accountRepo.findById(request.getAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "New account doesn't exist"));

        Expenses expenses = expensesRepo.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenses doesn't exist"));

        int year = Integer.parseInt(request.getYY());
        int month = Integer.parseInt(request.getMM());
        int day = Integer.parseInt(request.getDD());

        expenses.setDate(LocalDate.of(year, month, day));
        expenses.setAmount(request.getAmount());
        expenses.setExpensesType(request.getType());
        expenses.setDescription(request.getDescription());
        expenses.setAccounts(newAcc);

        expensesRepo.save(expenses);

        return toExpResponse(expenses);
    }

    //Delete Expenses
    public void remove(String expId, String userId){
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Expenses expenses = expensesRepo.findById(expId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expenses doesn't exist"));

        expensesRepo.deleteById(expId);
    }

}
