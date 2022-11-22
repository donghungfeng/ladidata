package com.example.ladi.service;

import com.example.ladi.controller.reponse.BaseResponse;
import com.example.ladi.controller.request.FindConfigByCODERequest;
import com.example.ladi.model.Config;

public interface ConfigService extends BaseService<Config>{
    public BaseResponse getByCODE(FindConfigByCODERequest findConfigByCODERequest);
}
