package com.sunj.gankio.entity;

import java.util.List;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/18 2:55 PM
 */

public class GankCategoryData {

    String _id;
    String createdAt;
    String desc;
    List<String> images;
    String publishedAt;
    String source;
    String type;
    String url;
    boolean used;
    String who;

    public String get_id() {
        return _id;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getImages() {
        return images;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getWho() {
        return who;
    }
}
