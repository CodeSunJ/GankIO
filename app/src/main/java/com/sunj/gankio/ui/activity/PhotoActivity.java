package com.sunj.gankio.ui.activity;

import com.sunj.gankio.R;
import com.sunj.gankio.net.ImageLoader;
import com.sunj.gankio.ui.base.BaseActivity;
import com.sunj.gankio.ui.base.BasePresenter;

import uk.co.senab.photoview.PhotoView;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/11/6 6:59 PM
 */

public class PhotoActivity extends BaseActivity {

    public static final String BUNDLE_PHOTO_URL = "BUNDLE_PHOTO_URL";

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void init() {
        PhotoView imagePv = (PhotoView) findViewById(R.id.pv_photo);
        initToolbar(TOOLBAR_MODE_BACK, getString(R.string.image));
        String url = getIntent().getStringExtra(BUNDLE_PHOTO_URL);
        if (url != null) {
            ImageLoader.loadImage(this, url, imagePv);
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_photo;
    }
}
