package com.sunj.gankio.net;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sunj.gankio.R;

/**
 * @Description:
 * @Author: sunjing
 * @Time: 2018/10/28 10:10 AM
 */

public class ImageLoader {

    public static void loadImage(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.bg_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


}
