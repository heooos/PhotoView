package com.test.photoview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class View extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        imageView = (ImageView) findViewById(R.id.imageView);

        Uri uri = getIntent().getData();
        imageView.setImageURI(uri);
      //  System.out.println(uri);
    }
}
