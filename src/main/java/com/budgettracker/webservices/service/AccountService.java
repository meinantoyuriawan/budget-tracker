package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.*;
import com.budgettracker.webservices.repository.AccountRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    //Get All Account
    public List<AccountResponse> getAll(String userId) {
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        List<Accounts> accountsList = accountRepo.findAll(users);
        if (accountsList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Account yet");
        }
        return accountsList.stream().map(this::toAccResponse).toList();
    }

    //Add Account
    public AccountResponse add(GetAddAccountRequest request){

        Users users = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));
        // add logic isDuplicate
        Accounts newAcc = new Accounts();

        newAcc.setId(UUID.randomUUID().toString());
        newAcc.setAccountName(request.getName());
        newAcc.setAccountType(request.getType());
        newAcc.setUsers(users);

        accountRepo.save(newAcc);

        return toAccResponse(newAcc);
    }

    //Update Account
    public AccountResponse update(UpdateAccountRequest request){

        Users users = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Accounts account = accountRepo.findById(request.getAccId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist"));

        account.setAccountName(request.getName());
        account.setAccountType(request.getType());
        accountRepo.save(account);

        return toAccResponse(account);
    }

    //Delete Account
    public void remove(String accId, String userId){
        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist"));

        Accounts account = accountRepo.findById(accId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist"));
        accountRepo.deleteById(accId);
    }
}
