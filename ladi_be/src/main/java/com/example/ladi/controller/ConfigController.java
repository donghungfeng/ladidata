package com.example.ladi.controller;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.FindConfigByCODERequest;
import com.example.ladi.model.Config;
import com.example.ladi.service.BaseService;
import com.example.ladi.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class ConfigController extends BaseController<Config> {
    @Autowired
    ConfigService configService;
    @Override
    protected BaseService<Config> getService() {
        return configService;
    }

    @PostMapping("/getByCODE")
    public BaseResponse getByCODE(@RequestBody FindConfigByCODERequest findConfigByCODERequest){
        return configService.getByCODE(findConfigByCODERequest);
    }
}
