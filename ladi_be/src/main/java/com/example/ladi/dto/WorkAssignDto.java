package com.example.ladi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkAssignDto {
    private int id;
    private String country;
    private String date;
    private String district;
    private String formColor;
    private String name;
    private String phone;
    private String source;
    private String state;
    private  int status;
    private String street;
    private String ward;
    // private String ipAddress;
    // private String dateChanged;
    private int staffId;
    private String staffName;
}
