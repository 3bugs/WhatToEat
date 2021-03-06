package com.example.helloandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.helloandroid.model.Dish;
import com.example.helloandroid.model.FoodMenu;
import com.example.helloandroid.net.WebServices;

public class DishFragment extends Fragment {

    private int mPosition;

    public DishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // อ้างอิงไปยัง ImageView และ TextView ใน layout ของ fragment
        final ImageView dishImageView = (ImageView) view.findViewById(R.id.dish_image_view);
        final TextView dishNameTextView = (TextView) view.findViewById(R.id.dish_name_text_view);

        // เข้าถึงออบเจ็ค Dish หนึ่งๆ (ตามค่า mPosition)
        FoodMenu foodMenu = FoodMenu.getInstance(getActivity());
        Dish dish = foodMenu.getDishList().get(mPosition);

        // ใส่ชื่ออาหารลงใน TextView
        dishNameTextView.setText(dish.name);

        // ใส่รูปภาพลงใน ImageView
        //Drawable dishImage = MainActivity.getDrawableFromAssets(getActivity(), dish.fileName);
        //dishImageView.setImageDrawable(dishImage);

        Glide.with(this).load(WebServices.IMAGES_BASE_URL + dish.fileName).into(dishImageView);
    }

    public void setPosition(int position) {
        mPosition = position;
    }

}
