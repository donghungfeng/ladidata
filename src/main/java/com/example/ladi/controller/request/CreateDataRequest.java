package com.example.ladi.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateDataRequest {
    private String name;
    private String phone;
    private String street;
    private String country;
    private String state;
    private String district;
    private String ward;
    private String formcolor;
    private int status;
    private String date;
    private String source;
    private String ipAddress;
    private String dateChanged;
    private int nhanVienId;
}
