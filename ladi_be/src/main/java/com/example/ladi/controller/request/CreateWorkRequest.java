package com.example.ladi.controller.request;

import com.example.ladi.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWorkRequest {
    private int timeIn;
    private int timeOut;
    private int donGiao;
    private int donHoanThanh;
    private String ghiChu;
    private int nhanVienId;
}