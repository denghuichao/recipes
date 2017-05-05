package com.deng.recipes.api.entity.subscriber;

/**
 * Created by hcdeng on 2017/5/5.
 */

public class NumberSubscriberResultInfo {
    private String msg;
    private String retCode;
    private Integer result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
