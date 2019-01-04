package com.guo.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/24 17:16
 */
//验证，能够把验证信息提取到页面显示出来
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;
    public ValidateResult validateResult(Object bean){
       final ValidateResult validateResult= new ValidateResult();
        Set<ConstraintViolation<Object>> constraintViolationSet =validator.validate(bean);
        if (constraintViolationSet.size()>0){
            validateResult.setHashErr(true);
            constraintViolationSet.forEach(constraintViolation->{
                        String errMsg=constraintViolation.getMessage();
                        String propertyName=constraintViolation.getPropertyPath().toString();
                        validateResult.getMap().put(propertyName,errMsg);
                    }
                   );
        }
        return  validateResult;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator= Validation.buildDefaultValidatorFactory().getValidator();

    }
}
