package com.example.helloandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helloandroid.model.FoodMenu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddDishActivity extends AppCompatActivity {

    ImageView mDishImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        mDishImageView = (ImageView) findViewById(R.id.dish_image_view);
        mDishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        final EditText dishNameEditText = (EditText) findViewById(R.id.dish_name_edit_text);
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodMenu foodMenu = FoodMenu.getInstance(AddDishActivity.this);
                String dishName = dishNameEditText.getText().toString();

                if (dishName.trim().equals("") || mPictureFile == null || mPictureFile.length() == 0) {
                    Toast.makeText(
                            AddDishActivity.this,
                            "กรุณาป้อนข้อมูลให้ครบ",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                final ProgressDialog progress = new ProgressDialog(AddDishActivity.this);
                progress.setMessage("Saving...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();

                foodMenu.addDish(dishName, mPictureFile, new FoodMenu.AddDishCallback() {
                    @Override
                    public void onFinish() {
                        progress.dismiss();
                        finish();
                    }
                });
            }
        });
    }

    private static final int TAKE_PICTURE = 1;
    private File mPictureFile;

    private void takePicture() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        try {
            mPictureFile = File.createTempFile("img", ".jpg", dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri outputFileUri = Uri.fromFile(mPictureFile);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                Bitmap bitmap = resizePicture(
                        BitmapFactory.decodeFile(mPictureFile.getAbsolutePath(), options),
                        600,
                        true
                );

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mPictureFile);
                    fos.write(bytes.toByteArray());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mDishImageView.setImageBitmap(bitmap);
            }
        }
    }

    private static Bitmap resizePicture(Bitmap sourceBitmap, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / sourceBitmap.getWidth(),
                (float) maxImageSize / sourceBitmap.getHeight()
        );
        int width = Math.round((float) ratio * sourceBitmap.getWidth());
        int height = Math.round((float) ratio * sourceBitmap.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(sourceBitmap, width, height, filter);
        return newBitmap;
    }
}