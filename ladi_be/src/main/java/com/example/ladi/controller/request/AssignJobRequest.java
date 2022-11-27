package com.example.ladi.controller.request;

import java.util.List;

import com.example.ladi.model.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AssignJobRequest {
    public List<DataRequest> dataList;

    public AssignJobRequest() {
    }

    public AssignJobRequest(List<DataRequest> dataList) {
        this.dataList = dataList;
    }

    public List<DataRequest> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataRequest> dataList) {
        this.dataList = dataList;
    }
}
