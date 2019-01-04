package com.guo.responese;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/21 10:34
 */
public class ResponeseResult {
    private String status;
    private Object data;

    public String getStatus() {
        return status;
    }

    public static ResponeseResult create(Object data) {
        return ResponeseResult.create(data, "success");

    }

    public static ResponeseResult create(Object data, String status) {
        ResponeseResult result = new ResponeseResult();
        result.setData(data);
        result.setStatus(status);
        return result;

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

