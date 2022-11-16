package com.example.ladi.controller.reponse;

import org.springframework.stereotype.Component;
@Component
public class BaseResponse {
    public int CODE;
    public String MESSAGE;
    public Object RESULT;

    public BaseResponse() {}

    public BaseResponse(int code, String message, Object result) {
        this.CODE = code;
        this.MESSAGE = message;
        this.RESULT = result;
    }
}
