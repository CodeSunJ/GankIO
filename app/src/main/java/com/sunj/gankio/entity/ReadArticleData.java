package com.sunj.gankio.entity;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/19 9:13 PM
 */

public class ReadArticleData {

    String _id;
    String content;
    String cover;
    long crawled;
    String created_at;
    boolean deleted;
    String published_at;
    String raw;
    Site site;
    String title;
    String uid;
    String url;

    public String get_id() {
        return _id;
    }

    public String getContent() {
        return content;
    }

    public String getCover() {
        return cover;
    }

    public long getCrawled() {
        return crawled;
    }

    public String getCreated_at() {
        return created_at;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getPublished_at() {
        return published_at;
    }

    public String getRaw() {
        return raw;
    }

    public Site getSite() {
        return site;
    }

    public String getTitle() {
        return title;
    }

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public static class Site {

        String cat_cn;
        String cat_en;
        String desc;
        String feed_id;
        String icon;
        String id;
        String name;
        int subscribers;
        String type;
        String url;

        public String getCat_cn() {
            return cat_cn;
        }

        public String getCat_en() {
            return cat_en;
        }

        public String getDesc() {
            return desc;
        }

        public String getFeed_id() {
            return feed_id;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getSubscribers() {
            return subscribers;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }



    }

}
