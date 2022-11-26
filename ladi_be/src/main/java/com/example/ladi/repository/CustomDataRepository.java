package com.example.ladi.repository;

import com.example.ladi.model.Account;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

public interface CustomDataRepository<Data, Integer extends Serializable> extends Repository {
    public List<Data> finDataByConditions(String status, String startDate, String endDate, Account account);
}
