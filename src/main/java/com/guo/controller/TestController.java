package com.guo.controller;

import com.alibaba.druid.util.StringUtils;
import com.guo.controller.viewObject.UserVO;
import com.guo.responese.ResponeseResult;
import com.guo.responese.errorResponese.BusinessException;
import com.guo.responese.errorResponese.EmBusinessError;
import com.guo.service.TestService;
import com.guo.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static com.guo.controller.BaseController.CONTENT_VALUE;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/20 17:12
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_VALUE})
    public ResponeseResult validatorLogin(@RequestParam(name = "telphone") String telphone,
                                          @RequestParam(name = "encrptPassword") String encrptPassword) throws BusinessException, NoSuchAlgorithmException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone)
                || org.apache.commons.lang3.StringUtils.isEmpty(encrptPassword)) {
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号或密码不正确");
        }
       UserModel userModel= testService.login(telphone,this.endcodeByMD5(encrptPassword));
        this.request.getSession().setAttribute("IS_LOGIN",true);
        this.request.getSession().setAttribute("LOGIN_USER",userModel);
        return ResponeseResult.create(null);

    }

    @RequestMapping(value = "/opt", method = {RequestMethod.POST}, consumes = {CONTENT_VALUE})
    public ResponeseResult otp(@RequestParam(name = "telphone") String telphone) {
//        生成随机验证码
        Random random = new Random();
        int randomnum = random.nextInt(90000);
        randomnum += 10000;
        String value = String.valueOf(randomnum);
//将验证码和手机号关联 redis 缓存管理 暂时不是用 使用session
        request.getSession().setAttribute(telphone, value);
//        将验证码发给用户 省略 otp协议
        System.out.println("telphone=" + telphone + ";value=" + value);
        return ResponeseResult.create(null);
    }

    @RequestMapping("/test")
    public ResponeseResult value(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = testService.getUserId(id);
        if (userModel == null) {
            userModel.setEncrptPassword("1111");
        }

        UserVO userVO = converFromModel(userModel);
        return ResponeseResult.create(userVO);
    }

    private UserVO converFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_VALUE})
    public ResponeseResult register(@RequestParam(name = "telphone") String telphone,
                                    @RequestParam(name = "otpCode") String otpCode,
                                    @RequestParam(name = "name") String name,
                                    @RequestParam(name = "gender") Integer gender,
                                    @RequestParam(name = "age") Integer age,
                                    @RequestParam(name = "encrptPassword") String encrptPassword
    ) throws BusinessException, NoSuchAlgorithmException {
        //验证手机号和验证码是否一致
        String inSessionOtpCode = (String) request.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setRegisterMode("ByPhone");
        userModel.setEncrptPassword(endcodeByMD5(encrptPassword));

        testService.register(userModel);
        return ResponeseResult.create(null);
    }

    public String endcodeByMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encoding = base64Encoder.encode(md5.digest(str.getBytes()));
        return encoding;
    }

    @RequestMapping(value = "/zhuce", method = {RequestMethod.POST}, consumes = {CONTENT_VALUE})
    public ResponeseResult register1(@RequestParam(name = "telphone") String telphone, @RequestParam(name = "otpCode") String otpCode) throws BusinessException {
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        testService.register(userModel);
        System.out.println("1111");
        return ResponeseResult.create(null);
    }

    @RequestMapping(value = "/cahxun")
    public ResponeseResult caxun(){
        return ResponeseResult.create(null);
    }
}
