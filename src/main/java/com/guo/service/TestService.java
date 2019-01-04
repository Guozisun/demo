package com.guo.service;

import com.guo.responese.errorResponese.BusinessException;
import com.guo.service.model.UserModel;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/20 17:32
 */
public interface TestService {
    UserModel getUserId(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel login(String telphone,String encrptPassword) throws BusinessException;
}
