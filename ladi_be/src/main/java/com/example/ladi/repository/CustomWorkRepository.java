package com.example.ladi.repository;

import com.example.ladi.model.Account;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

public interface CustomWorkRepository<Work, Integer extends Serializable> extends Repository {
    public List<Work> finWorkByConditions(String startDate, String endDate, Account account);
}
