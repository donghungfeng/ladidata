package com.example.ladi.controller;

import com.example.ladi.controller.request.CreateDataRequest;
import com.example.ladi.controller.request.UpdateDataRequest;
import com.example.ladi.model.Data;
import com.example.ladi.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    DataService dataService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Data>> getAll(){
        return new ResponseEntity<List<Data>>(dataService.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Data> createData(@RequestBody CreateDataRequest createDataRequest){
        return new ResponseEntity<Data>(dataService.createData(createDataRequest), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<String> updateData(@RequestParam(name = "id", required = false) Integer id, @RequestBody UpdateDataRequest updateDataRequest){
        return new ResponseEntity<String>(dataService.updateDataById(id, updateDataRequest), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<String> deleteData(@RequestParam(name = "id", required = false) Integer id){
        return new ResponseEntity<String>(dataService.deleteDataById(id), HttpStatus.OK);
    }

}
