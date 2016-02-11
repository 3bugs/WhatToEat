package com.example.helloandroid.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.helloandroid.model.Dish;
import com.example.helloandroid.model.ResponseStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Promlert on 2/11/2016.
 */
public class WebServices {

    private static final String TAG = "WebServices";

    private static final String BASE_URL = "http://10.0.3.2/what_to_eat/";
    public static final String IMAGES_BASE_URL = BASE_URL + "images/";
    private static final String GET_DISHES_URL = BASE_URL + "get_dishes.php";
    private static final String ADD_DISH_URL = BASE_URL + "add_dish.php";

    private static final OkHttpClient mClient = new OkHttpClient();
    private static ResponseStatus mResponseStatus;
    private static ArrayList<Dish> mDishList;

    public interface GetDishesFromWebServiceCallback {
        void onFailure(IOException e);
        void onResponse(ResponseStatus responseStatus, ArrayList<Dish> dishList);
    }

    public static void getDishes(final GetDishesFromWebServiceCallback callback) {
        Request request = new Request.Builder()
                .url(GET_DISHES_URL)
                .build();

        mClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e);
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                delay(3);

                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, null);
                        mDishList = new ArrayList<>();

                        parseJsonDishData(jsonObject.getJSONArray("dish_data"));
                    } else if (success == 0) {
                        mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                        mDishList = null;
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON.");
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(mResponseStatus, mDishList);
                            }
                        }
                );
            }
        });
    }

    private static void parseJsonDishData(JSONArray jsonArrayDishData) throws JSONException {
        for (int i = 0; i < jsonArrayDishData.length(); i++) {
            JSONObject jsonDish = jsonArrayDishData.getJSONObject(i);

            Dish dish = new Dish(
                    jsonDish.getString("name"),
                    jsonDish.getString("file_name")
            );
            mDishList.add(dish);
        }
    }

    private static void delay(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public interface AddDishToWebServiceCallback {
        void onFailure(IOException e);
        void onResponse(ResponseStatus responseStatus);
    }

    public static void addDish(String name, String fileName, final AddDishToWebServiceCallback callback) {
        FormBody.Builder builder = new FormBody.Builder()
                .add("name", name)
                .add("file_name", fileName);

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(ADD_DISH_URL)
                .post(formBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e);
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                    } else if (success == 0) {
                        mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON.");
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(mResponseStatus);
                            }
                        }
                );
            }
        });
    }

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");

    public static void addDish(String name, File file, final AddDishToWebServiceCallback callback) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_JPG, file))
                .build();

        Request request = new Request.Builder()
                .url(ADD_DISH_URL)
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(e);
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonResult = response.body().string();
                Log.d(TAG, jsonResult);

                try {
                    JSONObject jsonObject = new JSONObject(jsonResult);
                    int success = jsonObject.getInt("success");

                    if (success == 1) {
                        mResponseStatus = new ResponseStatus(true, jsonObject.getString("message"));
                    } else if (success == 0) {
                        mResponseStatus = new ResponseStatus(false, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON.");
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(mResponseStatus);
                            }
                        }
                );
            }
        });
    }
}
