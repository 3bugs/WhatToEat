package com.example.helloandroid.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.helloandroid.DishDetailActivity;
import com.example.helloandroid.DishFragment;
import com.example.helloandroid.model.FoodMenu;

/**
 * Created by COM_00 on 11/2/2559.
 */
public class DishPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public DishPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        FoodMenu foodMenu = FoodMenu.getInstance(mContext);
        return foodMenu.getDishList().size();
    }

    @Override
    public Fragment getItem(int position) {
        DishFragment fragment = new DishFragment();
        fragment.setPosition(position);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        FoodMenu foodMenu = FoodMenu.getInstance(mContext);
        return foodMenu.getDishList().get(position).name;
    }
}
