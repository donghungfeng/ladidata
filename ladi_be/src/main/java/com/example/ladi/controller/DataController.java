package com.example.ladi.controller;

import com.example.ladi.configurations.jdbc.JDBCConnection;
import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.AssignJobRequest;
import com.example.ladi.dto.DataDto;
import com.example.ladi.extentions.datetimeExtention;
import com.example.ladi.model.Data;
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
        obj.setDate(datetimeExtention.getCurrentUnixDate());
        return new BaseResponse(200, "Tạo thành công!", this.getService().create(obj));
    }


    @CrossOrigin
    @GetMapping("/getByStatus")
    public BaseResponse getAllByStatus(@RequestParam Integer status, @RequestParam String startDate, @RequestParam String endDate){
        var datas = dataService.getByStatus(status, startDate, endDate);
        if(datas != null) return new BaseResponse(200, "Lấy dữ liệu thành công!", datas);
        else return new BaseResponse(404, "Lấy dữ liệu thất bại!", datas);
    }

    @CrossOrigin
    @GetMapping("/getByDate")
    public BaseResponse getAllByDate(@RequestParam String startDate, @RequestParam String endDate){
        var datas = dataService.getByDate(startDate, endDate);
        if(datas != null) return new BaseResponse(200, "Lấy dữ liệu thành công!", datas);
        else return new BaseResponse(404, "Lấy dữ liệu thất bại!", datas);
    } 

    @CrossOrigin
    @GetMapping("/getJoin")
    public BaseResponse getAllByDate(@RequestParam Integer status, @RequestParam String startDate, @RequestParam String endDate) throws ClassNotFoundException, SQLException{
        
        Connection conn = JDBCConnection.getJDBCConnection();
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String sql = "";
        if(status == -1) 
            sql = "SELECT d.*, a.username, a.fullname"
                        +" FROM data as d"
                    +" LEFT JOIN account as a"
                    +" ON d.staff_id = a.id"
                    +" WHERE (d.date BETWEEN "+startDate+" AND "+endDate+")"
                    +" ORDER BY d.id DESC";
        else
            sql = "SELECT d.*, a.username, a.fullname"
                        +" FROM data as d"
                    +" LEFT JOIN account as a"
                    +" ON d.staff_id = a.id"
                    +" WHERE (d.status = "+status+") AND (d.date BETWEEN "+startDate+" AND "+endDate+")"
                    +" ORDER BY d.id DESC";
        
        ResultSet result = statement.executeQuery(sql);
       
        var data = new DataDto();
        data.setId(result.getInt(1));     
        data.setCountry(result.getString(2));     
        data.setDate(result.getString(3));     
        data.setDistrict(result.getString(4));     
        data.setFormColor(result.getString(5));     
        data.setName(result.getString(6));     
        data.setPhone(result.getString(7));     
        data.setSource(result.getString(8));     
        data.setState(result.getString(9));     
        data.setStatus(result.getInt(10));     
        data.setStreet(result.getString(11));     
        data.setWard(result.getString(12));     
        data.setNhanVienId(result.getInt(13));     
        data.setStaffName(result.getString(14));     
        
        if(result != null) return new BaseResponse(200, "Lấy dữ liệu thành công!", data);
        else return new BaseResponse(404, "Lấy dữ liệu thất bại!", null);
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

    @GetMapping("")
    public BaseResponse getAllData(@RequestHeader(name = "Authorization") String jwt){

        return dataService.getAllData(jwt);
    }


}
