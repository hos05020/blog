package com.blog.exception;


import lombok.Getter;

/**
 * 정책상 status ->400
 */
@Getter
public class InvalidRequest extends HodologException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName,String message){
        super(MESSAGE);
        addValidation(fieldName,message);

    }

    @Override
    public int getstatusCode(){
        return 400;
    }
}
