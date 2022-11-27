package com.example.ladi.service.impl;

import com.example.ladi.configurations.JwtTokenProvider;
import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.AssignJobRequest;
import com.example.ladi.controller.request.AssignWorkRequest;
import com.example.ladi.controller.request.DataRequest;
import com.example.ladi.dto.AccountDto;
import com.example.ladi.dto.DataDto;
import com.example.ladi.dto.WorkDto;
import com.example.ladi.model.Account;
import com.example.ladi.model.Data;
import com.example.ladi.model.Work;
import com.example.ladi.repository.AccountRepository;
import com.example.ladi.repository.BaseRepository;
import com.example.ladi.repository.CustomDataRepository;
import com.example.ladi.repository.DataRepository;
import com.example.ladi.service.DataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {
    @Autowired
    DataRepository dataRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomDataRepository customDataRepository;
    @Override
    protected BaseRepository<Data> getRepository() {
        return dataRepository;
    }

    @Override
    public BaseResponse getAllData(String jwt, String status, String startDate, String endDate) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        List<DataDto> dataDtoList = new ArrayList<>();
        if (account.getRole().equals("admin")) {
            List<Data> dataList = customDataRepository.finDataByConditions(status, startDate, endDate, null);
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getAccount() == null){
                    DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
                    dataDtoList.add(dataDto);
                }
                else {
                    AccountDto accountDto = new AccountDto(dataList.get(i).getAccount().getId(), dataList.get(i).getAccount().getUserName(), dataList.get(i).getAccount().getFullName());
                    DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
                    dataDto.setAccount(accountDto);
                    dataDtoList.add(dataDto);
                }
            }
            return new BaseResponse(200, "OK", dataList);
        }else {
            List<Data> dataList = customDataRepository.finDataByConditions(status, startDate, endDate, account);
            for (int i = 0; i<dataList.size(); i++){
                AccountDto accountDto = modelMapper.map(dataList.get(i).getAccount(), AccountDto.class);
                DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
                dataDto.setAccount(accountDto);
                dataDtoList.add(dataDto);
            }
        }
        return new BaseResponse(200, "OK", dataDtoList);
    }

    @Override
    public BaseResponse createData(Data data) {
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Long date = Long.parseLong(formatter.format(nowDate));
        data.setDate(date);
        dataRepository.save(data);
        return new BaseResponse(200, "OK", data);
    }
    
    @Override
    public BaseResponse assignWork(AssignJobRequest assignJobRequest) {
        for (DataRequest data : assignJobRequest.getDataList()){
            Account account = accountRepository.findAllById(data.getNhanVienId());
            Data dataResult = modelMapper.map(data, Data.class);
            dataResult.setAccount(account);
            System.out.println(dataResult);
            dataRepository.save(dataResult);
        }
        return new BaseResponse(200, "Success!", null);
    }

    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
