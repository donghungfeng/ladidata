package com.example.ladi.service.impl;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.AssignJobRequest;
import com.example.ladi.dto.DataDto;
import com.example.ladi.dto.DataAssginDto;
import com.example.ladi.model.Account;
import com.example.ladi.model.Data;
import com.example.ladi.repository.BaseRepository;
import com.example.ladi.repository.DataRepository;
import com.example.ladi.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {
    @Autowired
    DataRepository dataRepository;
    @Override
    protected BaseRepository<Data> getRepository() {
        return dataRepository;
    }

    public List<DataAssginDto> getByStatus(Integer status, String startDate, String endDate) {
        var datas = dataRepository.findAllByStatus(status, startDate, endDate);
        return datas;
    }

    public List<Data> getByDate(String startDate, String endDate) {
        List<Data> datas = dataRepository.findAllByDate(startDate, endDate);
        return datas;
    }

    @Override
    public BaseResponse assignWork(AssignJobRequest request) {
        
        return null;
    }
}
