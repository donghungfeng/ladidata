package com.example.ladi.service.impl;

import com.example.ladi.configurations.JwtTokenProvider;
import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.AssignJobRequest;
import com.example.ladi.dto.AccountDto;
import com.example.ladi.dto.DataDto;
import com.example.ladi.dto.WorkDto;
import com.example.ladi.model.Account;
import com.example.ladi.model.Data;
import com.example.ladi.model.Work;
import com.example.ladi.repository.AccountRepository;
import com.example.ladi.repository.BaseRepository;
import com.example.ladi.repository.DataRepository;
import com.example.ladi.service.DataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {
    @Autowired
    DataRepository dataRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    protected BaseRepository<Data> getRepository() {
        return dataRepository;
    }

    public List<Data> getByStatus(Integer status, String startDate, String endDate) {
        List<Data> datas = dataRepository.findAllByStatus(status, startDate, endDate);
        return datas;
    }

    public List<Data> getByDate(String startDate, String endDate) {
        List<Data> datas = dataRepository.findAllByDate(startDate, endDate);
        return datas;
    }

    @Override
    public BaseResponse assignWork(AssignJobRequest request) {
        
        return null;
    }

    @Override
    public BaseResponse getAllData(String jwt) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        if (account.getRole().equals("admin")){
            List<Data> dataList = dataRepository.findAllByOrderByIdDesc();
            List<DataDto> dataDtoList = new ArrayList<>();
            for (int i = 0; i<dataList.size(); i++){
                AccountDto accountDto = modelMapper.map(dataList.get(i).getAccount(), AccountDto.class);
                DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
                dataDto.setAccount(accountDto);
                dataDtoList.add(dataDto);
            }
            return new BaseResponse(200, "OK", dataDtoList);
        }
        List<Data> dataList = dataRepository.findAllByAccount(account);
        List<DataDto> dataDtoList = new ArrayList<>();
        for (int i = 0; i<dataList.size(); i++){
            AccountDto accountDto = modelMapper.map(dataList.get(i).getAccount(), AccountDto.class);
            DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
            dataDto.setAccount(accountDto);
            dataDtoList.add(dataDto);
        }
        return new BaseResponse(200, "OK", dataDtoList);
    }

    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
