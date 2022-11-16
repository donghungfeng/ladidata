package com.example.ladi.controller;

import com.example.ladi.model.Data;
import com.example.ladi.service.BaseService;
import com.example.ladi.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataController extends BaseController<Data>{

    @Autowired
    DataService dataService;
    @Override
    protected BaseService<Data> getService() {
        return dataService;
    }
}
