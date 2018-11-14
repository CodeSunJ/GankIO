package com.sunj.gankio.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.sunj.gankio.R;
import com.sunj.gankio.ui.base.BaseActivity;
import com.sunj.gankio.ui.presenter.WebViewPresenter;
import com.sunj.gankio.ui.view.IWebView;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/11/6 6:59 PM
 */

public class WebViewActivity extends BaseActivity<WebViewPresenter> implements IWebView {

    public static final String BUNDLE_CONTENT_URL = "BUNDLE_CONTENT_URL";
    public static final String BUNDLE_URL_TITLE = "BUNDLE_URL_TITLE";

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected WebViewPresenter getPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected void init() {
        initToolbar(TOOLBAR_MODE_BACK, "");
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mPresenter.loadHtml(mWebView, getIntent().getStringExtra(BUNDLE_CONTENT_URL));
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_webview;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void showWebViewProgress(int progress) {
        if (progress == 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setProgress(progress);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setWebViewTitle(String title) {
        setToolbarTitle(title);
    }
}
