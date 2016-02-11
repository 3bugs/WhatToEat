package com.example.helloandroid.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.helloandroid.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by COM_00 on 9/2/2559.
 */
public class FoodMenu {

    private static final String TAG = "FoodMenu";

    private ArrayList<Dish> mDishList = new ArrayList<>();
    private Random mRandom = new Random();

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDatabase;

    private Context mContext;

    private static FoodMenu instance;

    public static FoodMenu getInstance(Context context) {
        if (instance == null) {
            instance = new FoodMenu(context);
        }

        return instance;
    }

    private FoodMenu(Context context) {
/*
        mDishList.add(new Dish("ข้าวผัด", "kao_pad.jpg"));
        mDishList.add(new Dish("ข้าวไข่เจียว", "kao_kai_jeaw.jpg"));
        mDishList.add(new Dish("ข้าวหน้าเป็ด", "kao_na_ped.jpg"));
        mDishList.add(new Dish("ข้าวมันไก่", "kao_mun_kai.jpg"));
        mDishList.add(new Dish("ข้าวหมูแดง", "kao_moo_dang.jpg"));
        mDishList.add(new Dish("ราดหน้า", "rad_na.jpg"));
        mDishList.add(new Dish("ผัดซีอิ๊ว", "pad_sie_eew.jpg"));
        mDishList.add(new Dish("ผัดไทย", "pad_thai.jpg"));
        mDishList.add(new Dish("ส้มตำ ไก่ย่าง", "som_tum_kai_yang.jpg"));
*/
        mContext = context;

        mHelper = new DatabaseHelper(context);
        mDatabase = mHelper.getWritableDatabase();

        loadFromDatabase();
    }

    private void loadFromDatabase() {
        mDishList.clear();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
            String fileName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FILENAME));

            Dish dish = new Dish(name, fileName);
            mDishList.add(dish);
        }

        cursor.close();
    }

    public Dish getRandomDish() {
        int randomIndex = mRandom.nextInt(mDishList.size());
        Dish randomDish = mDishList.get(randomIndex);

        Log.i(TAG, randomDish.name + ", " + randomDish.fileName);
        return randomDish;
    }

    public ArrayList<Dish> getDishList() {
        return mDishList;
    }
}
