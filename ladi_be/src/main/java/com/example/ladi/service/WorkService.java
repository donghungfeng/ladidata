package com.example.ladi.service;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.CheckOutRequest;
import com.example.ladi.controller.request.CheckWorkActiveRequest;
import com.example.ladi.controller.request.CreateWorkRequest;
import com.example.ladi.model.Work;

public interface WorkService extends BaseService<Work> {
    public BaseResponse createWork(CreateWorkRequest createWorkRequest);
    public BaseResponse getAllWork(String jwt);
    public BaseResponse checkOut(CheckOutRequest checkOutRequest);
    public BaseResponse getAllActive();
    public BaseResponse checkWorkActive(CheckWorkActiveRequest checkWorkActive);
}
