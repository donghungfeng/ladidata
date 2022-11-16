package com.example.ladi.service.impl;

import com.example.ladi.model.Account;
import com.example.ladi.model.Data;
import com.example.ladi.repository.BaseRepository;
import com.example.ladi.repository.DataRepository;
import com.example.ladi.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {
    @Autowired
    DataRepository dataRepository;
    @Override
    protected BaseRepository<Data> getRepository() {
        return dataRepository;
    }
}
