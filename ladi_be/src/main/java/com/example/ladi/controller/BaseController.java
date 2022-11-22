package com.example.ladi.controller;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.service.BaseService;

import io.swagger.models.Path;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

public abstract class BaseController<T> {
    protected abstract BaseService<T> getService();

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("getAll")
    public BaseResponse getAll(){
        return new BaseResponse(200, "Lấy dữ liệu thành công!", this.getService().getAll());
    }

    @PostMapping("create")
    public BaseResponse create(@RequestBody T t) throws NoSuchAlgorithmException {
        System.out.println(t);
        return new BaseResponse(200, "Tạo thành công!", this.getService().create(t));
    }

    @PutMapping("update")
    public BaseResponse update(@RequestBody T t) throws NoSuchAlgorithmException {
        return new BaseResponse(200, "Cập nhật thành công!", this.getService().update(t));
    }

    @GetMapping("getById")
    public BaseResponse getById(@RequestParam(name = "id") int id){
        return new BaseResponse(200, "Lấy dữ liệu thành công!", this.getService().getById(id));
    }

    @DeleteMapping("deleteById")
    public BaseResponse deleteById(@RequestParam(name = "id") int id){
        return new BaseResponse(200, "OK", this.getService().deleteById(id));
    }
}
