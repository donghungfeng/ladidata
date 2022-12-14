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
import com.example.ladi.model.UtmMedium;
import com.example.ladi.model.Work;
import com.example.ladi.repository.*;
import com.example.ladi.service.DataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Service
public class DataServiceImpl extends BaseServiceImpl<Data> implements DataService {
    @Autowired
    DataRepository dataRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UtmMediumRepository utmMediumRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomDataRepository customDataRepository;

    @Autowired
    CustomWorkRepository customWorkRepository;
    @Override
    protected BaseRepository<Data> getRepository() {
        return dataRepository;
    }

    @Override
    public BaseResponse getAllData(String jwt, String status, String startDate, String endDate, String shopCode) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        List<DataDto> dataDtoList = new ArrayList<>();
        if (account.getRole().equals("admin")) {
            List<Data> dataList = customDataRepository.finDataByConditions(status, startDate, endDate, null, shopCode);
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getAccount() == null){
                    DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
                    dataDtoList.add(dataDto);
                }
                else {
                    AccountDto accountDto = new AccountDto(dataList.get(i).getAccount().getId(), dataList.get(i).getAccount().getUserName(), dataList.get(i).getAccount().getFullName(), dataList.get(i).getAccount().getShop(), dataList.get(i).getAccount().getRole());
                    DataDto dataDto = modelMapper.map(dataList.get(i), DataDto.class);
                    dataDto.setAccount(accountDto);
                    dataDtoList.add(dataDto);
                }
            }
            return new BaseResponse(200, "OK", dataDtoList);
        }else {
            List<Data> dataList = customDataRepository.finDataByConditions(status, startDate, endDate, account, shopCode);
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
    public BaseResponse  createData(Data data, String shopCode) {
        Date nowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat dateOnlyFormatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        dateOnlyFormatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        Long date = Long.parseLong(formatter.format(nowDate));
        Long dateOnly = Long.parseLong(dateOnlyFormatter.format(nowDate));
        data.setDate(date);
        data.setDateOnly(dateOnly);
        data.setShopCode(shopCode);
        List<Work> workList = customWorkRepository.finWorkByConditions(null, null, null, shopCode, 1);
        DataDto dataDto = new DataDto();
        if (!workList.isEmpty()){
            Random rand = new Random();
            int ranNum = rand.nextInt(workList.size());
            data.setAccount(workList.get(ranNum).getAccount());
            data.setStatus(1);
            data.setDateChanged(date);
            AccountDto accountDto = modelMapper.map(workList.get(ranNum).getAccount(), AccountDto.class);
            dataDto = modelMapper.map(data, DataDto.class);
            dataDto.setAccount(accountDto);
        }
        dataRepository.save(data);
        return new BaseResponse(200, "OK", dataDto);
    }
    
    @Override
    public BaseResponse assignWork(AssignJobRequest assignJobRequest) {
        for (DataRequest data : assignJobRequest.getDataList()){
            Account account = accountRepository.findAllById(data.getNhanVienId());
            Data dataResult = modelMapper.map(data, Data.class);
            dataResult.setAccount(account);
            dataRepository.save(dataResult);
        }
        return new BaseResponse(200, "Success!", null);
    }

    @Override
    public BaseResponse getAllDataAccountNull(String status, String shopCode) {
        List<Data> dataList = customDataRepository.findDataByAccountNull(status, shopCode);
        return new BaseResponse(200, "OK", dataList);
    }

    private String getJwtFromRequest(String bearerToken) {
        // Ki???m tra xem header Authorization c?? ch???a th??ng tin jwt kh??ng
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public BaseResponse statisticByUtmMedium() {
        List <UtmMedium> utmMediumList = utmMediumRepository.findAll();
        List <String> codeList = new ArrayList<>();
        codeList.add("ALL");
        List <List<Object>> dataList = new ArrayList<>();
        dataList.add(dataRepository.statisticUtmMedium());
        Map<String,List> map = new HashMap<String,List>();
        for (int i = 0;i<utmMediumList.size();i++){
            codeList.add(utmMediumList.get(i).getCode());
            dataList.add(dataRepository.statisticUtmMediumByMedium(utmMediumList.get(i).getCode()));
        }
        map.put("code",codeList);
        map.put("data",dataList);
        return new BaseResponse(200, "OK", map);
    }
}
