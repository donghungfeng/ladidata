package com.example.ladi.controller;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.LoginRequest;
import com.example.ladi.model.Account;
import com.example.ladi.service.AcountService;
import com.example.ladi.service.BaseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AcountController extends BaseController<Account> {
    private AcountService acountService;
    public AcountController(AcountService acountService){
        this.acountService = acountService;
    }
    @Override
    protected BaseService<Account> getService() {
        return acountService;
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest loginRequest){
        return new BaseResponse(200, "Token", acountService.login(loginRequest.getUserName(), loginRequest.getPassWord()));
    }
}
