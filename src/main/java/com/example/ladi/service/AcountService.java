package com.example.ladi.service;

import com.example.ladi.model.Account;

public interface AcountService extends BaseService<Account> {
    public String login(String userName, String password);
}
