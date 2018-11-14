package com.sunj.gankio.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunj.gankio.R;
import com.sunj.gankio.entity.GankBaseResponse;
import com.sunj.gankio.entity.ReadMainCategoryData;
import com.sunj.gankio.entity.ReadSubCategoryData;
import com.sunj.gankio.ui.adapter.BaseListAdapter;
import com.sunj.gankio.ui.base.BaseActivity;
import com.sunj.gankio.ui.fragment.CategoryPagesFragment;
import com.sunj.gankio.ui.fragment.HomeFragment;
import com.sunj.gankio.ui.presenter.ReadCategoryPresenter;
import com.sunj.gankio.ui.view.IReadCategoryView;
import com.sunj.gankio.util.ToastUtils;
import com.sunj.gankio.widget.navigation.NavigationBar;
import com.sunj.gankio.widget.navigation.NavigationItem;
import com.sunj.gankio.widget.option.OptionView;
import com.sunj.gankio.widget.popupwindow.PopupView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/13 9:33 PM
 */

public class HomeActivity extends BaseActivity<ReadCategoryPresenter> implements View.OnClickListener,
        NavigationBar.OnNavigationItemClickListener, IReadCategoryView {

    private List<NavigationItem> navigationItemList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mCategoryTextList = new ArrayList<>();
    private List<ReadMainCategoryData> mCategoryList;
    private PopupWindow mCategoryPopWindow;
    private NavigationBar navigationBar;
    private ListView mItemsLv;

    @Override
    protected ReadCategoryPresenter getPresenter() {
        return new ReadCategoryPresenter();
    }

    @Override
    protected void init() {
        initToolbar(TOOLBAR_MODE_DRAWLAYOUT, getString(R.string.app_name));
        navigationBar = (NavigationBar) findViewById(R.id.navigation_bar);
        navigationItemList.add(NavigationItem.create(R.drawable.ic_homepage_selected,
                R.drawable.ic_homepage_unselected, getString(R.string.home_page)));
        navigationItemList.add(NavigationItem.create(R.drawable.ic_categorydata_selected,
                R.drawable.ic_categorydata_unselected, getString(R.string.category)));

        fragmentList.add(new HomeFragment());
        fragmentList.add(CategoryPagesFragment.newInstance());
        CircleImageView userIv = (CircleImageView) findViewById(R.id.iv_user);
        userIv.setImageResource(R.drawable.ic_user);
        userIv.setOnClickListener(this);
        navigationBar.addAllItems(navigationItemList);
        navigationBar.setOnNavigationItemClickListener(this);
        showFragment(-1, 0);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                initDrawerLayout();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_home;
    }

    @Override
    public void onNavigationItemClick(int preIndex, int index) {
        showFragment(preIndex, index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user:
                break;
        }
    }

    @Override
    protected int getMenuResID() {
        return R.menu.menu_home;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                goToActivity(SearchActivity.class, null);
                break;
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void getReadMainCategorySuccess(GankBaseResponse<ReadMainCategoryData> response) {
        mCategoryList = response.getResults();
        for (int i=0; i<mCategoryList.size(); i++) {
            mCategoryTextList.add(mCategoryList.get(i).getName());
        }
        showReadMainCategory();
    }

    @Override
    public void getReadMainCategoryFailure() {
        ToastUtils.show(this, getString(R.string.load_read_main_category_failure));
    }

    @Override
    public void getReadSubCategorySuccess(GankBaseResponse<ReadSubCategoryData> response) {

    }

    @Override
    public void getReadSubCategoryFailure() {

    }

    private void showFragment(int preIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (preIndex >= 0) {
            transaction.hide(fragmentList.get(preIndex));
        }
        if (!fragmentList.get(index).isAdded()) {
            transaction.add(R.id.container, fragmentList.get(index));
        } else {
            transaction.show(fragmentList.get(index));
        }
        transaction.commit();
    }

    private void initDrawerLayout() {
        if (mItemsLv == null) {
            final List<Item> itemList = new ArrayList<>();
            itemList.add(Item.createItem(R.drawable.ic_read, getString(R.string.read)));
            itemList.add(Item.createItem(R.drawable.ic_setting, getString(R.string.setting)));
            mItemsLv = (ListView) findViewById(R.id.lv_items);
            mItemsLv.setAdapter(new BaseListAdapter(getApplicationContext()) {
                @Override
                protected void bindData(ViewHolder vh, int position) {
                    ImageView iv = vh.findViewById(R.id.iv_icon);
                    iv.setImageResource(itemList.get(position).imageResId);
                    TextView tv = vh.findViewById(R.id.tv_title);
                    tv.setText(itemList.get(position).text);
                }

                @Override
                protected int getItemViewLayoutResID() {
                    return R.layout.item_option_list;
                }

                @Override
                public int getCount() {
                    return itemList.size();
                }

                @Override
                public Object getItem(int position) {
                    return itemList.get(position);
                }
            });
            mItemsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            showReadMainCategory();
                            closeDrawerLayout();
                            break;
                    }
                }
            });
        }
    }

    private void showReadMainCategory() {
        if (mCategoryTextList == null || mCategoryTextList.size() == 0) {
            mPresenter.getReadMainCategoryData();
            return;
        }

        OptionView optionView = null;
        if (mCategoryPopWindow == null) {
            mCategoryPopWindow = PopupView.getPopupWindow(this, R.layout.ppw_category);
            mCategoryPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    HomeActivity.this.setActivityWindowAlpha(1f);
                }
            });
            optionView = mCategoryPopWindow.getContentView().findViewById(R.id.option_view_category);
            optionView.setOptionTexts(mCategoryTextList);
            optionView.setOnOptionViewSelectedListener(new OptionView.OnOptionViewSelectedListener() {
                @Override
                public void onSelectedListener(final int index) {
                    mCategoryTextList.get(index);
                    navigationBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCategoryPopWindow.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putString(ReadCategoryActivity.BUNDLE_READ_SUB_CATEGORY_TITLE,
                                    mCategoryList.get(index).getName());
                            bundle.putString(ReadCategoryActivity.BUNDLE_READ_SUB_CATEGORY,
                                    mCategoryList.get(index).getEn_name());
                            goToActivity(ReadCategoryActivity.class, bundle);
                        }
                    }, 500);
                }
            });
        }
        if (!mCategoryPopWindow.isShowing()) {
            if (optionView == null) {
                optionView = mCategoryPopWindow.getContentView().findViewById(R.id.option_view_category);
            }
            if (optionView != null) {
                optionView.clear();
            }
            mCategoryPopWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER_HORIZONTAL, 0, 0);
            setActivityWindowAlpha(0.3f);
        } else {
            mCategoryPopWindow.dismiss();
        }
    }

    private void closeDrawerLayout() {
        mDrawerLayout.closeDrawers();
    }

    private static class Item {
        int imageResId;
        String text;
        public static Item createItem(int imageResId, String text) {
            Item item = new Item();
            item.imageResId = imageResId;
            item.text = text;
            return item;
        }
    }
}
