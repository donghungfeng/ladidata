package com.example.ladi.service.impl;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.FindConfigByCODERequest;
import com.example.ladi.model.Config;
import com.example.ladi.repository.BaseRepository;
import com.example.ladi.repository.ConfigRepository;
import com.example.ladi.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CongifServiceImpl extends BaseServiceImpl<Config> implements ConfigService {
    @Autowired
    ConfigRepository configRepository;
    @Override
    protected BaseRepository<Config> getRepository() {
        return configRepository;
    }

    @Override
    public BaseResponse getByCODE(FindConfigByCODERequest findConfigByCODERequest) {
        Config config = configRepository.findAllByCode(findConfigByCODERequest.getCode());
        if (config == null){
            return new BaseResponse(500, "Congif Not Found", null);
        }
        return new BaseResponse(200, "OK", config);
    }
}
