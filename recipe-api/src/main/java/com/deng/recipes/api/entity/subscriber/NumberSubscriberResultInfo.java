package com.deng.recipes.api.entity.subscriber;

/**
 * Created by hcdeng on 2017/5/5.
 */

public class NumberSubscriberResultInfo {
    private String msg;
    private Integer retCode;
    private Integer result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public static NumberSubscriberResultInfo fail(){

        NumberSubscriberResultInfo resultInfo = new NumberSubscriberResultInfo();
        resultInfo.setResult(0);
        resultInfo.setMsg("fail");
        resultInfo.setRetCode(0);

        return resultInfo;
    }
}
