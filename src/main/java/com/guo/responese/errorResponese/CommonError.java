package com.guo.responese.errorResponese;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/21 11:27
 */
//错误异常接口
public interface CommonError {
    public int getErrCode();
    public  String getErrMsg();
    public  CommonError setErrMsg(String errMsg);

}
