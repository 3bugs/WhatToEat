package com.example.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.helloandroid.adapter.DishListAdapter;
import com.example.helloandroid.model.Dish;
import com.example.helloandroid.model.FoodMenu;

import java.util.ArrayList;

public class DishListActivity extends AppCompatActivity {

    public static final String KEY_POSITION = "position";

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DishListActivity.this, AddDishActivity.class);
                startActivity(i);
            }
        });

        setupListView();
    }

    private void setupListView() {
        mListView = (ListView) findViewById(R.id.dish_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DishListActivity.this, DishDetailActivity.class);
                i.putExtra(KEY_POSITION, position);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        FoodMenu foodMenu = FoodMenu.getInstance(this);
        ArrayList<Dish> dishList = foodMenu.getDishList();

        DishListAdapter adapter = new DishListAdapter(
                DishListActivity.this,
                R.layout.list_item,
                dishList
        );
        mListView.setAdapter(adapter);
    }
}
