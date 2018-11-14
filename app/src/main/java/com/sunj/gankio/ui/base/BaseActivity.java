package com.sunj.gankio.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sunj.gankio.R;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: SunJ
 * @Time: 2018/10/13 8:03 PM
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements BaseView, Toolbar.OnMenuItemClickListener{

    protected static final int TOOLBAR_MODE_NONE = 0;
    protected static final int TOOLBAR_MODE_BACK = 1;
    protected static final int TOOLBAR_MODE_DRAWLAYOUT = 2;

    protected P mPresenter;
    protected Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    protected void initToolbar(int toolbarMode, String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        if (!TextUtils.isEmpty(title)) {
            setToolbarTitle(title);
        }
        switch (toolbarMode) {
            case TOOLBAR_MODE_BACK:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                break;
            case TOOLBAR_MODE_DRAWLAYOUT:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ActionBarDrawerToggle actionBarDrawerToggle =
                        new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
                actionBarDrawerToggle.syncState();
                break;
            case TOOLBAR_MODE_NONE:
                break;
        }
        mToolbar.setOnMenuItemClickListener(this);
    }

    protected void setToolbarTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            TextView titleTv = mToolbar.findViewById(R.id.tv_title);
            if (titleTv != null) {
                titleTv.setText(title);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuResID = getMenuResID();
        if (menuResID != 0) {
            getMenuInflater().inflate(menuResID, menu);
        }
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                Method m;
                try {
                    m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    protected int getMenuResID(){
        return 0;
    }

    protected void setActivityWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    protected void goToActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected abstract P getPresenter();

    protected abstract void init();

    protected abstract int getLayoutResID();
}
