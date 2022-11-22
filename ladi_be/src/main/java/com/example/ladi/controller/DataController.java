package com.example.ladi.controller;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.AssignJobRequest;
import com.example.ladi.dto.DataDto;
import com.example.ladi.model.Data;
import com.example.ladi.service.BaseService;
import com.example.ladi.service.DataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/data")
@CrossOrigin
public class DataController extends BaseController<Data>{

    @Autowired
    DataService dataService;

//    @Autowired
//    ModelMapper mapper;
    
    @Override
    protected BaseService<Data> getService() {
        return dataService;
    }

    @PostMapping("create")
    public BaseResponse create(@RequestBody Data obj) throws NoSuchAlgorithmException {
//        Data data = mapper.map(entity, Data.class);

        long unixTime = Instant.now().getEpochSecond();
        obj.setDate(Long.toString(unixTime));
        return new BaseResponse(200, "Tạo thành công!", this.getService().create(obj));
    }


    @CrossOrigin
    @GetMapping("/getByStatus")
    public BaseResponse getAllByStatus(@RequestParam Integer status){
        var datas = dataService.getByStatus(status);
        if(datas != null) return new BaseResponse(200, "Lấy dữ liệu thành công!", datas);
        else return new BaseResponse(404, "Lấy dữ liệu thất bại!", datas);
    }

    @CrossOrigin
    @PostMapping("/assignWork")
    public BaseResponse assignWork(@RequestBody AssignJobRequest request) throws NoSuchAlgorithmException {
        if(request != null) {
            var staffId = request.getStaffId();
            for (var data : request.getData()) {
                data.setStaffId(staffId);
                dataService.update(data);
            }
            return new BaseResponse(200, "Giao việc thành công", null);
        }
        else return new BaseResponse(404, "Giá trị rỗng!", null);
    }
}
