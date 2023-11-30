package com.budgettracker.webservices.service;

import com.budgettracker.webservices.model.Accounts;
import com.budgettracker.webservices.repository.AccountRepo;
import com.budgettracker.webservices.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    UserRepo userRepo;

    //isUser

    boolean isUser(String userId){
//        boolean exists = userRepo.existsById(userId);
//
//        return exists;
        return userRepo.existsById(userId);
    }

    //Get All Account
    public List<Accounts> getAllAccount(String userId){
        boolean isExist = isUser(userId);

        ArrayList<Accounts> accountsList = new ArrayList<>();
        if (isExist){
            accountRepo.findByUserId(userId).forEach(account -> accountsList.add(account));
        }
        // else {error handling}
        // accountRepo.findAll().forEach(account -> accountsList.add(account));

        return accountsList;
    }

    //Add Account
    public Accounts addAccount(Accounts account){
        boolean isExist = isUser(account.getUsers().getId());
        if (isExist){
            accountRepo.save(account);
        }
        return account;

    }
    //Update Account

        //get item by id

    boolean isAcc(String accId){
        return accountRepo.existsById(accId);
    }
    Accounts getAccById(String accId){
        return accountRepo.findById(accId).get();
    }

    public Accounts updateAcoount(Accounts account){
//        boolean isExist = isUser(account.getUsers().getId());
        boolean isExist = isAcc(account.getId());
        if (isExist) {
            Accounts newAcc = getAccById(account.getId());
            newAcc.setAccountName(account.getAccountName());
            newAcc.setAccountType(account.getAccountType());
        }
        return account;
    }
    //Delete Account

    public void deleteAccount(String accId){
        boolean isExist = isAcc(accId);
        if (isExist) {
            accountRepo.deleteById(accId);
        }
    }
}
