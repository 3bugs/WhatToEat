package com.example.helloandroid;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.helloandroid.adapter.DishPagerAdapter;
import com.example.helloandroid.model.Dish;
import com.example.helloandroid.model.FoodMenu;

import java.util.ArrayList;

public class DishDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        Intent i = getIntent();
        int itemPosition = i.getIntExtra(DishListActivity.KEY_POSITION, 0);

        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);

        DishPagerAdapter adapter = new DishPagerAdapter(
                this,
                getSupportFragmentManager()
        );

        pager.setAdapter(adapter);
        pager.setCurrentItem(itemPosition);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

/*
        FoodMenu foodMenu = FoodMenu.getInstance(this);
        ArrayList<Dish> dishList = foodMenu.getDishList();
        Dish dish = dishList.get(itemPosition);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(dish.name);

        // สร้างแฟรกเมนต์ (ออบเจ็ค DishFragment)
        DishFragment fragment = new DishFragment();
        // บอกให้แฟรกเมนต์รู้ว่าต้องแสดงข้อมูลรายการอาหารอะไร
        fragment.setPosition(itemPosition);

        // ใส่แฟรกเมนต์ลงใน FrameLayout ภายใน layout ของแอคทิวิตี
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
*/
    }
}
