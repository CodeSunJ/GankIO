package com.sunj.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sunj.gankio.ui.base.BaseMultiPagesFragment;
import com.sunj.gankio.util.ConstantUtils;

import java.util.ArrayList;

/**
 * @Description: 
 * @Author: sunjing
 * @Time: 2018/10/13 10:12 PM
 */

public class CategoryPagesFragment extends BaseMultiPagesFragment {

    public static Fragment newInstance() {
        CategoryPagesFragment fragment = new CategoryPagesFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add(ConstantUtils.IMAGES);
        titleList.add(ConstantUtils.ANDROID);
        titleList.add(ConstantUtils.IOS);
        titleList.add(ConstantUtils.VIDEO);
        titleList.add(ConstantUtils.EXPANDING_RES);
        titleList.add(ConstantUtils.WEB_FRONT_END);
        titleList.add(ConstantUtils.ALL);
        bundle.putStringArrayList(BaseMultiPagesFragment.ARGUMENT_CHILD_FRAGMENT_TITLES, titleList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Fragment getChildFragment(int position) {
        switch (position) {
            case 0:
                return CategoryImagesFragment.newInstance(mTitleList.get(position));
            case 1:
            case 2:
            case 5:
                return CategoryWebFragment.newInstance(mTitleList.get(position));
            case 3:
            case 4:
                return CategoryTextFragment.newInstance(mTitleList.get(position));
            case 6:
                return CategoryTextFragment.newInstance(mTitleList.get(position));
        }
        return CategoryTextFragment.newInstance(mTitleList.get(position));
    }
}
