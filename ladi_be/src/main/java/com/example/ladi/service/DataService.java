package com.example.ladi.service;

import com.example.ladi.controller.request.CreateDataRequest;
import com.example.ladi.controller.request.UpdateDataRequest;
import com.example.ladi.model.Data;
import com.example.ladi.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;

    public List<Data> getAll(){
        return dataRepository.findAll();
    }

    public Data createData(CreateDataRequest createDataRequest){
        Data data = Data.builder()
                .name(createDataRequest.getName())
                .phone(createDataRequest.getPhone())
                .street(createDataRequest.getStreet())
                .country(createDataRequest.getCountry())
                .state(createDataRequest.getState())
                .district(createDataRequest.getDistrict())
                .ward(createDataRequest.getWard())
                .formcolor(createDataRequest.getFormcolor())
                .status(createDataRequest.getStatus())
                .date(createDataRequest.getDate())
                .source(createDataRequest.getSource())
                .ipAddress(createDataRequest.getIpAddress())
                .dateChanged(createDataRequest.getDateChanged())
//                .nhanVienId(createDataRequest.getNhanVienId())
                .build();
        dataRepository.save(data);
        return data;
    }

    public String updateDataById(int id, UpdateDataRequest updateDataRequest){
        Data data = dataRepository.findAllById(id);
        if (updateDataRequest.getName() != null){
            data.setName(updateDataRequest.getName());
        }
        if (updateDataRequest.getPhone() != null){
            data.setPhone(updateDataRequest.getPhone());
        }
        if (updateDataRequest.getStreet() != null){
            data.setStreet(updateDataRequest.getStreet());
        }
        if (updateDataRequest.getCountry() != null){
            data.setCountry(updateDataRequest.getCountry());
        }
        if (updateDataRequest.getState() != null){
            data.setState(updateDataRequest.getState());
        }
        if (updateDataRequest.getDistrict() != null){
            data.setDistrict(updateDataRequest.getDistrict());
        }
        if (updateDataRequest.getWard() != null){
            data.setWard(updateDataRequest.getWard());
        }
        if (updateDataRequest.getFormcolor() != null){
            data.setFormcolor(updateDataRequest.getFormcolor());
        }
        if (updateDataRequest.getStatus() != 0){
            data.setStatus(updateDataRequest.getStatus());
        }
        if (updateDataRequest.getDate() != null){
            data.setDate(updateDataRequest.getDate());
        }
        if (updateDataRequest.getSource() != null){
            data.setSource(updateDataRequest.getSource());
        }
        if (updateDataRequest.getIpAddress() != null){
            data.setIpAddress(updateDataRequest.getIpAddress());
        }
        if (updateDataRequest.getDateChanged() != null){
            data.setDateChanged(updateDataRequest.getDateChanged());
        }
//        if (updateDataRequest.getNhanVienId() != 0){
//            data.setNhanVienId(updateDataRequest.getNhanVienId());
//        }
        dataRepository.save(data);
        return "updated";
    }

    public String deleteDataById(int id){
        Data data = dataRepository.findAllById(id);
        dataRepository.delete(data);
        return "deleted";
    }

}
