package com.example.ladi.controller;

import com.example.ladi.configurations.jdbc.JDBCConnection;
import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.AssignJobRequest;
import com.example.ladi.controller.request.AssignWorkRequest;
import com.example.ladi.dto.DataDto;
import com.example.ladi.extentions.datetimeExtention;
import com.example.ladi.model.Data;
import com.example.ladi.repository.CustomDataRepository;
import com.example.ladi.service.BaseService;
import com.example.ladi.service.DataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/data")
@CrossOrigin
public class DataController extends BaseController<Data>{

    @Autowired
    DataService dataService;

    @Autowired
    CustomDataRepository customDataRepository;
    
    @Override
    protected BaseService<Data> getService() {
        return dataService;
    }


    @GetMapping("")
    public BaseResponse getAllData(@RequestHeader(name = "Authorization") String jwt, @RequestParam String status, @RequestParam String startDate, @RequestParam String endDate){

        return dataService.getAllData(jwt, status, startDate, endDate);
    }

    @PostMapping("createData")
    public BaseResponse createData(@RequestBody Data data){
        return dataService.createData(data);
    }

    @PostMapping("assignWork")
    public BaseResponse assignWork(@RequestBody AssignJobRequest assignJobRequest){
        return dataService.assignWork(assignJobRequest);
    }

}
