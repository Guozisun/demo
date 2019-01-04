package com.guo.controller;

import com.guo.responese.ResponeseResult;
import com.guo.responese.errorResponese.BusinessException;
import com.guo.responese.errorResponese.EmBusinessError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/21 14:15
 */
@ControllerAdvice
public class BaseController {
    public static final String CONTENT_VALUE="application/x-www-form-urlencoded";
    //定义ExceptionHadndler处理未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    if (ex instanceof BusinessException){
        BusinessException businessException = (BusinessException)ex;
        resultMap.put("errCode", businessException.getErrCode());
        resultMap.put("errMsg", businessException.getErrMsg());
    }
    else {
        resultMap.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
        resultMap.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
    }
    return ResponeseResult.create(resultMap,"fail");



    }
}
