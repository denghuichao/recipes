package com.deng.recipes.api.entity.subscriber;

import com.deng.recipes.api.entity.SearchRecipeResultInfo;

/**
 * Created by Administrator on 2017/2/20.
 */

public class RecipeSubscriberResultInfo {

    private String msg;
    private Integer retCode;
    private SearchRecipeResultInfo result;

    public RecipeSubscriberResultInfo(){

    }

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

    public SearchRecipeResultInfo getResult() {
        return result;
    }

    public void setResult(SearchRecipeResultInfo result) {
        this.result = result;
    }
}
