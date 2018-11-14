package com.sunj.gankio.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/20 9:19 AM
 */

public class GankHomeResponse {

    List<String> category;

    boolean error;

    GankToadyResult results;

    public List<String> getCategory() {
        return category;
    }

    public boolean isError() {
        return error;
    }

    public GankToadyResult getResults() {
        return results;
    }

    public static class GankToadyResult {
        List<GankCategoryData> Android;
        List<GankCategoryData> App;
        List<GankCategoryData> iOS;

        @SerializedName("休息视频")
        List<GankCategoryData> Video;

        @SerializedName("拓展资源")
        List<GankCategoryData> ExpandingRes;

        @SerializedName("瞎推荐")
        List<GankCategoryData> Recommend;

        @SerializedName("福利")
        List<GankCategoryData> Image;

        public List<GankCategoryData> getAndroid() {
            return Android;
        }

        public List<GankCategoryData> getApp() {
            return App;
        }

        public List<GankCategoryData> getiOS() {
            return iOS;
        }

        public List<GankCategoryData> getVideo() {
            return Video;
        }

        public List<GankCategoryData> getExpandingRes() {
            return ExpandingRes;
        }

        public List<GankCategoryData> getRecommend() {
            return Recommend;
        }

        public List<GankCategoryData> getImage() {
            return Image;
        }
    }
}
