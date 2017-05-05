package com.deng.recipes.api.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/20.
 */

public class SearchRecipeResultInfo {

    private int curPage;
    private int total;
    private ArrayList<RecipeEntity> list;

    public SearchRecipeResultInfo(){

    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<RecipeEntity> getList() {
        return list;
    }

    public void setList(ArrayList<RecipeEntity> list) {
        this.list = list;
    }
}
