package com.example.ladi.controller.request;

import com.example.ladi.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.NoRepositoryBean;

public class DataRequest {
    private int id;
    private String phone;
    private String name;
    private String street;
    private String country;

    public DataRequest(int id, String phone, String name, String street, String country, String state, String district, String ward, String formColor, int status, String date, String source, String ipAddress, String dateChanged, String staffName, int nhanVienId) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.street = street;
        this.country = country;
        this.state = state;
        this.district = district;
        this.ward = ward;
        this.formColor = formColor;
        this.status = status;
        this.date = date;
        this.source = source;
        this.ipAddress = ipAddress;
        this.dateChanged = dateChanged;
        this.staffName = staffName;
        this.nhanVienId = nhanVienId;
    }

    private String state;
    private String district;
    private String ward;
    private String formColor;
    private int status;
    private String date;
    private String source;
    private String ipAddress;
    private String dateChanged;
    private String staffName;
    private int nhanVienId;

    public DataRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getFormColor() {
        return formColor;
    }

    public void setFormColor(String formColor) {
        this.formColor = formColor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(int nhanVienId) {
        this.nhanVienId = nhanVienId;
    }
}
