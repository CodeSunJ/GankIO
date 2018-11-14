package com.sunj.gankio.net;

import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.GankCategoryData;
import com.sunj.gankio.entity.GankSearchResponse;
import com.sunj.gankio.entity.GankHomeResponse;
import com.sunj.gankio.entity.ReadArticleData;
import com.sunj.gankio.entity.ReadMainCategoryData;
import com.sunj.gankio.entity.ReadSubCategoryData;
import com.sunj.gankio.entity.SearchData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/14 10:12 AM
 */

public interface GankIOService {

    String GANKIO_BASE_URL = "https://gank.io/api/";

    @GET("today")
    Observable<GankHomeResponse> getHomeGankData();

    @GET("xiandu/categories")
    Observable<GankBaseResponse<ReadMainCategoryData>> getReadMainCategoryData();

    @GET("xiandu/category/{category}")
    Observable<GankBaseResponse<ReadSubCategoryData>> getReadSubCategoryData(@Path("category") String category);

    @GET("xiandu/data/id/{id}/count/{count}/page/{page}")
    Observable<GankBaseResponse<ReadArticleData>> getReadArticleData(@Path("id") String id,
                                                                     @Path("count") int count,
                                                                     @Path("page") int page);

    @GET("search/query/{query}/category/{category}/count/{count}/page/{page}")
    Observable<GankSearchResponse<SearchData>> getSearchData(@Path("query") String query,
                                                             @Path("category") String category,
                                                             @Path("count") int count,
                                                             @Path("page") int page);

    @GET("data/{category}/{count}/{page}")
    Observable<GankBaseResponse<GankCategoryData>> getGankCategoryData(@Path("category") String category,
                                                                       @Path("count") int count,
                                                                       @Path("page") int page);


}
