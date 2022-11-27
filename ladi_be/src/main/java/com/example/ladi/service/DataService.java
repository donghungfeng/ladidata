package com.example.ladi.service;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.AssignJobRequest;
import com.example.ladi.controller.request.AssignWorkRequest;
import com.example.ladi.model.Data;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public interface DataService extends BaseService<Data> {

    public BaseResponse getAllData(String jwt, String status, String startDate, String endDate);
    public BaseResponse createData(Data data);
    public BaseResponse assignWork(AssignJobRequest assignJobRequest);
}
