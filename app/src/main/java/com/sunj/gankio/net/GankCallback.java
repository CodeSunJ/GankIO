package com.sunj.gankio.net;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/19 9:22 PM
 */

public interface GankCallback<T> {

    void onSuccess(T t);

    void onError(Throwable error);

}
