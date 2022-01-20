package com.multitips.entity;

import java.util.List;

public class BaseResult {
    private boolean HasError;
    private String ErrorMessage;
    private List<Category> CategoryList;

    public boolean isHasError() {
        return HasError;
    }

    public void setHasError(boolean hasError) {
        HasError = hasError;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public List<Category> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        CategoryList = categoryList;
    }


    public Category getByName(String name){
        List<Category> data= this.getCategoryList();
        for (Category cat: data) {
            if(cat.getName().equals(name)){
                return cat;
            }
        }
        return null;
    }
}
