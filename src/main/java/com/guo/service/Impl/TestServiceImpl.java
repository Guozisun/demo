package com.guo.service.Impl;

import com.guo.dao.UserDOMapper;
import com.guo.dao.UserPassWordDOMapper;
import com.guo.pojo.UserDO;
import com.guo.pojo.UserPassWordDO;
import com.guo.responese.errorResponese.BusinessException;
import com.guo.responese.errorResponese.EmBusinessError;
import com.guo.service.TestService;
import com.guo.service.model.UserModel;
import com.guo.validator.ValidateResult;
import com.guo.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/20 17:33
 */
@Service
public class TestServiceImpl implements TestService {


    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    UserPassWordDOMapper userPassWordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        //判断是否为空
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息错误");
        }
            ValidateResult validateResult =validator.validateResult(userModel);
        if (validateResult.isHashErr()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validateResult.getErrMsg());

        }
        /*if (StringUtils.isEmpty(userModel.getName())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelphone())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/
        UserDO userDO = convertFormUserModel(userModel);
        userDOMapper.insertSelective(userDO);
        userModel.setId(userDO.getId());
        UserPassWordDO userPassWordDO = convertPasswordFormUserModel(userModel);
        userPassWordDOMapper.insertSelective(userPassWordDO);
        return;
    }

    private UserDO convertFormUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    @Override
    public UserModel login(String telphone, String encrptPassword) throws BusinessException {
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户名或密码错误");
        }
        UserPassWordDO userPassWordDO = userPassWordDOMapper.selectByUserId(userDO.getId());
        if (userPassWordDO == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户名或密码不正确");
        }
        UserModel userModel = convertFromDataObject(userDO, userPassWordDO);
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;

    }

    private UserPassWordDO convertPasswordFormUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPassWordDO userPassWordDO = new UserPassWordDO();
        userPassWordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPassWordDO.setUserId(userModel.getId());
        return userPassWordDO;
    }


    @Override
    public UserModel getUserId(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return null;
        }
        UserPassWordDO userPassWordDO = userPassWordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPassWordDO);
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPassWordDO userPassWordDO) {
        UserModel userModel = new UserModel();
        if (userDO == null) {
            return null;
        }
        BeanUtils.copyProperties(userDO, userModel);
        if (userPassWordDO != null) {
            userModel.setEncrptPassword(userPassWordDO.getEncrptPassword());
        }
        return userModel;
    }

    private UserModel convertFromUserDo(UserDO userDO) {
        UserModel userModel = new UserModel();
        if (userDO == null) {
            return null;
        }
        BeanUtils.copyProperties(userDO, userModel);
        return userModel;
    }
}
