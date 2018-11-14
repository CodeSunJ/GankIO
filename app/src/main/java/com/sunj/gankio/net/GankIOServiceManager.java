package com.sunj.gankio.net;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.GankCategoryData;
import com.sunj.gankio.entity.GankSearchResponse;
import com.sunj.gankio.entity.GankHomeResponse;
import com.sunj.gankio.entity.ReadArticleData;
import com.sunj.gankio.entity.ReadMainCategoryData;
import com.sunj.gankio.entity.ReadSubCategoryData;
import com.sunj.gankio.entity.SearchData;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/14 10:22 AM
 */

public class GankIOServiceManager {

    private GankIOService mGankIOService;

    public static GankIOServiceManager getInstance() {
        return GankIOServiceManagerHolder.pInstance;
    }

    private static class GankIOServiceManagerHolder {
        public static final GankIOServiceManager pInstance = new GankIOServiceManager();
    }

    private GankIOServiceManager() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ResponseInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankIOService.GANKIO_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mGankIOService = retrofit.create(GankIOService.class);
    }

    public void getHomeGankData(final GankCallback<GankHomeResponse> gankCallback) {
        mGankIOService.getHomeGankData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankHomeResponse>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(GankHomeResponse value) {
                gankCallback.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                gankCallback.onError(e);
            }

            @Override
            public void onComplete() { }
        });
    }

    public void getReadMainCategoryData(final GankCallback<GankBaseResponse<ReadMainCategoryData>> gankCallback) {
        mGankIOService.getReadMainCategoryData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBaseResponse<ReadMainCategoryData>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(GankBaseResponse<ReadMainCategoryData> value) {
                gankCallback.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                gankCallback.onError(e);
            }

            @Override
            public void onComplete() { }
        });
    }

    public void getReadSubCategoryData(String category,
                                       final GankCallback<GankBaseResponse<ReadSubCategoryData>> gankCallback) {
        mGankIOService.getReadSubCategoryData(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBaseResponse<ReadSubCategoryData>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(GankBaseResponse<ReadSubCategoryData> value) {
                gankCallback.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                gankCallback.onError(e);
            }

            @Override
            public void onComplete() { }
        });
    }

    public void getReadArticleData(String id, int count, final int page,
                                   final GankCallback<GankBaseResponse<ReadArticleData>> gankCallback) {
        mGankIOService.getReadArticleData(id, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBaseResponse<ReadArticleData>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(GankBaseResponse<ReadArticleData> value) {
                gankCallback.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                gankCallback.onError(e);
            }

            @Override
            public void onComplete() { }
        });
    }

    public void getSearchData(String query, String category, int count, int page, final GankCallback<GankSearchResponse<SearchData>> gankCallback) {
        mGankIOService.getSearchData(query, category, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankSearchResponse<SearchData>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(GankSearchResponse<SearchData> value) {
                gankCallback.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                gankCallback.onError(e);
            }

            @Override
            public void onComplete() { }
        });
    }

    public void getGankCategoryData(String category, int count, int page,
                                    final GankCallback<GankBaseResponse<GankCategoryData>> gankCallback) {
        mGankIOService.getGankCategoryData(category, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBaseResponse<GankCategoryData>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(GankBaseResponse<GankCategoryData> value) {
                gankCallback.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                gankCallback.onError(e);
            }

            @Override
            public void onComplete() { }
        });
    }

}
