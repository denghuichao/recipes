package com.deng.recipes.entity;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class CookStep {

    private Integer stepOrder;

    private String description = "";

    private String image = "";


    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
