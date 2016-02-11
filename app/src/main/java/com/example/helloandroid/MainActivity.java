package com.example.helloandroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloandroid.model.Dish;
import com.example.helloandroid.model.FoodMenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        Button randomButton = (Button) findViewById(R.id.random_button); // ปุ่ม Random
        Button exitButton = (Button) findViewById(R.id.exit_button);     // ปุ่ม Exit
        //MyListener listener = new MyListener();     // Listener ของปุ่ม

        // กำหนด Listener ให้กับปุ่ม Random
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // สร้างออบเจ็ค FoodMenu
                FoodMenu foodMenu = FoodMenu.getInstance(MainActivity.this);
                Dish dish = foodMenu.getRandomDish();

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                // เข้าถึงตัว Inflater ของระบบ Android
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // ทำการ inflate layout ให้เป็น object
                View layout = inflater.inflate(R.layout.dialog_dish, null);

                // อ้างอิงไปยัง ImageView (แสดงภาพอาหาร) ใน layout
                ImageView dishImageView = (ImageView) layout.findViewById(R.id.dish_image_view);
                // อ้างอิงไปยัง TextView (แสดงชื่ออาหาร) ใน layout
                TextView dishNameTextView = (TextView) layout.findViewById(R.id.dish_name_text_view);

                // อ่านข้อมูลจากไฟล์ภาพมาเก็บเป็น Drawable
                Drawable image = getDrawableFromAssets(MainActivity.this, dish.fileName);

                // กำหนดรูปภาพ (ภาพอาหาร) ให้กับ ImageView
                dishImageView.setImageDrawable(image);
                // กำหนดข้อความ (ชื่ออาหาร) ให้กับ TextView
                dishNameTextView.setText(dish.name);

                dialog.setView(layout);
                dialog.show();
            }
        });

        // กำหนด Listener ให้กับปุ่ม Exit
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Loading...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        FoodMenu foodMenu = FoodMenu.getInstance(this);
        foodMenu.loadFromWebService(new FoodMenu.GetDishListCallback() {
            @Override
            public void onFinish(ArrayList<Dish> dishList) {
                progress.dismiss();
            }
        });
    }

    public static Drawable getDrawableFromAssets(Context context, String fileName) {
        AssetManager am = context.getAssets();
        InputStream stream = null;

        try {
            stream = am.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error opening image file: " + fileName);
        }

        Drawable image = Drawable.createFromStream(stream, null);
        return image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_show_list) {
            Intent i = new Intent(this, DishListActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    // Listener ของปุ่ม
    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            int buttonId = v.getId();

            switch (buttonId) {
                case R.id.random_button:
                    Toast.makeText(
                            MainActivity.this,
                            "Hello Android",
                            Toast.LENGTH_SHORT
                    ).show();
                    break;

                case R.id.exit_button:
                    finish();
                    break;
            }
        }
    }
*/

}
