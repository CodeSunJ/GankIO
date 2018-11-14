package com.sunj.gankio.entity;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/18 3:39 PM
 */

public class GankBaseResponse<T> {
    boolean error;
    List<T> results;

    public boolean isError() {
        return error;
    }

    public List<T> getResults() {
        return results;
    }
}
