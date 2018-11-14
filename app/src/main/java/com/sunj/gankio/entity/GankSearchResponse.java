package com.sunj.gankio.entity;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/19 9:46 PM
 */

public class GankSearchResponse<T> extends GankBaseResponse<T> {
    
    int count;

    public int getCount() {
        return count;
    }
}
