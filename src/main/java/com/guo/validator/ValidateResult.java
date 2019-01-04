package com.guo.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/24 17:11
 */
public class ValidateResult {
    private boolean hashErr = false;
    private Map<String, Object> map = new HashMap<>();

    public boolean isHashErr() {
        return hashErr;
    }

    public void setHashErr(boolean hashErr) {
        this.hashErr = hashErr;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getErrMsg() {
        return StringUtils.join(map.values().toArray(), ",");

    }
}
