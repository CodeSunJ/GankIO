package com.sunj.gankio.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadArticleData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/11/7 3:22 PM
 */

public class ResponseInterceptor implements Interceptor {

    private Gson mGson = new Gson();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String url = response.request().url().toString();
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("xiandu/data")) {
                if (response.body() != null && response.body().contentType() != null) {
                    String body = response.body().string();
                    GankBaseResponse<ReadArticleData> data = mGson.fromJson(body,
                            new TypeToken<GankBaseResponse<ReadArticleData>>(){}.getType());
                    for (ReadArticleData article : data.getResults()) {
                        Document document = Jsoup.parse(article.getContent());
                        Elements elements = document.select("p");
                        String content = "";
                        for (Element element : elements) {
                            content = element.text();
                            if (!TextUtils.isEmpty(content)) {
                                break;
                            }
                        }
                        article.setContent(content);
                        article.setRaw("");
                    }
                    String newBody = mGson.toJson(data);
                    MediaType contentType = response.body().contentType();
                    ResponseBody responseBody = ResponseBody.create(contentType, newBody);
                    return response.newBuilder().body(responseBody).build();
                }
            }
        }
        return response;
    }
}
