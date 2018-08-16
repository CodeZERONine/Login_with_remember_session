package com.ebwebtech.vidhya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
  private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      mImageView = findViewById(R.id.imageView);
      Glide.with(this).load(R.mipmap.monster).into(mImageView);
    }
}
