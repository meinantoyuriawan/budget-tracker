package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.AccountResponse;
import com.budgettracker.webservices.model.Accounts;
import com.budgettracker.webservices.repository.AccountRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    UserRepo userRepo;

    // Account Response
    private AccountResponse toAccResponse(Accounts accounts) {
        AccountResponse newAccResp = new AccountResponse();
        newAccResp.setId(accounts.getId());
        newAccResp.setName(accounts.getAccountName());
        newAccResp.setType(accounts.getAccountType());
        return newAccResp;
    }
    // next create endpoint(?) for each service

    boolean isUser(String userId){
//        boolean exists = userRepo.existsById(userId);
//
//        return exists;
        return userRepo.existsById(userId);
    }

    //Get All Account
    public List<AccountResponse> getAll(String userId) {
        boolean isExist = isUser(userId);
        if (!isExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"user doesn't exist");
        }

        List<Accounts> accountsList = accountRepo.findByUserId(userId);
        if (accountsList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no Account yet");
        }
        return accountsList.stream().map(this::toAccResponse).toList();
    }

    //Add Account
    public AccountResponse add(Accounts account){
        boolean isExist = isUser(account.getUsers().getId());
        if (!isExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"user doesn't exist");
        }
        Accounts newAcc = new Accounts();
//                UUID.randomUUID().toString(),
//                account.getAccountName(),
//                account.getAccountType(),
//                account.getUsers(),
//                account.getExpenses()
//        );
        newAcc.setId(UUID.randomUUID().toString());
        newAcc.setAccountName(account.getAccountName());
        newAcc.setAccountType(account.getAccountType());
        newAcc.setUsers(account.getUsers());
        newAcc.setExpenses(account.getExpenses());

        accountRepo.save(newAcc);

        return toAccResponse(account);
    }

    //Update Account

        //get item by id

    boolean isAcc(String accId){
        return accountRepo.existsById(accId);
    }
    Accounts getAccById(String accId){
        return accountRepo.findById(accId).get();
    }

    public AccountResponse update(Accounts account){
        boolean isExist = isAcc(account.getId());
        if (!isExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"account doesn't exist");
        }
        Accounts newAcc = getAccById(account.getId());
        newAcc.setAccountName(account.getAccountName());
        newAcc.setAccountType(account.getAccountType());
        accountRepo.save(newAcc);

        return toAccResponse(newAcc);
    }

    //Delete Account
    public void remove(String accId){
        boolean isExist = isAcc(accId);
        if (!isExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"account doesn't exist");
        }
        accountRepo.deleteById(accId);
    }
}
