package com.example.ladi.service.impl;

import com.example.ladi.configurations.JwtTokenProvider;
import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.CheckOutRequest;
import com.example.ladi.controller.request.CheckWorkActiveRequest;
import com.example.ladi.controller.request.CreateWorkRequest;
import com.example.ladi.dto.AccountDto;
import com.example.ladi.dto.WorkDto;
import com.example.ladi.model.Account;
import com.example.ladi.model.Work;
import com.example.ladi.repository.AccountRepository;
import com.example.ladi.repository.BaseRepository;
import com.example.ladi.repository.WorkRepository;
import com.example.ladi.service.WorkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkServiceImpl extends BaseServiceImpl<Work> implements WorkService {
    @Autowired
    WorkRepository workRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    protected BaseRepository<Work> getRepository() {
        return workRepository;
    }

    @Override
    public BaseResponse createWork(CreateWorkRequest createWorkRequest) {
        Account account = accountRepository.findAllById(createWorkRequest.getNhanVienId());
        if (account == null){
            return new BaseResponse(500, "Account not found", "Create Fail");
        }
        Work work = new Work(createWorkRequest.getTimeIn(), createWorkRequest.getTimeOut(), createWorkRequest.getDonGiao(), createWorkRequest.getDonHoanThanh(), createWorkRequest.getGhiChu(), 1, account);
        workRepository.save(work);
        return new BaseResponse(200, "OK", work.getId());
    }

    @Override
    public BaseResponse getAllWork(String jwt) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        List<WorkDto> workDtoList = new ArrayList<>();
        if (account.getRole().equals("admin")){
            List<Work> workList = workRepository.findAllByOrderByIdDesc();
            for (int i = 0; i<workList.size(); i++){
                AccountDto accountDto = modelMapper.map(workList.get(i).getAccount(), AccountDto.class);
                WorkDto workDto = modelMapper.map(workList.get(i),WorkDto.class);
                workDto.setAcount(accountDto);
                workDtoList.add(workDto);
            }
        }
        else{
            List<Work> workList = workRepository.findAllByAccount(account);
            for (int i = 0 ; i<workList.size(); i++){
                AccountDto accountDto = modelMapper.map(workList.get(i).getAccount(),AccountDto.class);
                WorkDto workDto = modelMapper.map(workList.get(i),WorkDto.class);
                workDto.setAcount(accountDto);
                workDtoList.add(workDto);
            }
        }
        return new BaseResponse(200, "OK", workDtoList);

    }

    @Override
    public BaseResponse checkOut(CheckOutRequest checkOutRequest) {
        Work work = workRepository.findAllById(checkOutRequest.getId());
        if (work == null){
            return new BaseResponse(500, "Work not found", "Checkout Fail");
        }
        if (checkOutRequest.getTimeOut() != 0){
            work.setTimeOut(checkOutRequest.getTimeOut());
        }
        work.setDonGiao(checkOutRequest.getDonGiao());
        work.setDonHoanThanh(checkOutRequest.getDonHoanThanh());
        work.setIsActive(-1);
        workRepository.save(work);
        return new BaseResponse(200, "OK", "Checkout Success");
    }

    @Override
    public BaseResponse getAllActive() {
        List<Work> workList = workRepository.findAllByIsActive(1);
        List<WorkDto> workDtoList = new ArrayList<>();
        for (int i = 0 ; i<workList.size(); i++){
            AccountDto accountDto = new AccountDto(workList.get(i).getAccount().getId(), workList.get(i).getAccount().getUserName(), workList.get(i).getAccount().getFullName());
            WorkDto workDto = new WorkDto(workList.get(i).getId(), workList.get(i).getTimeIn(), workList.get(i).getTimeOut(), workList.get(i).getDonGiao(), workList.get(i).getDonHoanThanh(), workList.get(i).getGhiChu(), accountDto);
            workDtoList.add(workDto);
        }
        return new BaseResponse(200, "OK", workDtoList);
    }

    @Override
    public BaseResponse checkWorkActive(CheckWorkActiveRequest checkWorkActive) {
        Account account = accountRepository.findAllById(checkWorkActive.getNhanVienId());
        if (account == null){
            return new BaseResponse(500, "Account Not Found!", null);
        }
        Work work = workRepository.findAllByIsActiveAndAccount(1, account);
        if (work == null){
            return new BaseResponse(500, "NOT ACCOUNT CHECKING!", null);
        }
        AccountDto accountDto = new AccountDto(account.getId(), account.getUserName(), account.getFullName());
        WorkDto workDto = new WorkDto(work.getId(), work.getTimeIn(), work.getTimeOut(), work.getDonGiao(), work.getDonHoanThanh(), work.getGhiChu(), accountDto);
        return new BaseResponse(200, "OK", workDto);
    }

    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
