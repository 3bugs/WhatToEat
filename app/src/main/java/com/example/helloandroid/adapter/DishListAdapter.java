package com.example.helloandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloandroid.MainActivity;
import com.example.helloandroid.R;
import com.example.helloandroid.model.Dish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by COM_00 on 11/2/2559.
 */
public class DishListAdapter extends ArrayAdapter<Dish> {

    private Context mContext;
    private int mLayoutResId;
    private ArrayList<Dish> mDishList;

    // constructor
    public DishListAdapter(Context context, int resource, ArrayList<Dish> dishList) {
        super(context, resource, dishList);
        mContext = context;
        mLayoutResId = resource;
        mDishList = dishList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemLayout = View.inflate(mContext, mLayoutResId, null);
        ImageView dishImageView = (ImageView) itemLayout.findViewById(R.id.dish_image_view);
        TextView dishNameTextView = (TextView) itemLayout.findViewById(R.id.dish_name_text_view);

        Dish dish = mDishList.get(position);

        // ใส่ชื่ออาหารลงใน TextView ภายใน layout
        dishNameTextView.setText(dish.name);

        // ใส่รูปภาพอาหารลงใน ImageView ภายใน layout
        Drawable dishImage = MainActivity.getDrawableFromAssets(mContext, dish.fileName);
        dishImageView.setImageDrawable(dishImage);

        return itemLayout;
    }
}
