package com.bage.tutorials.ui.about;

import android.os.Bundle;

import com.bage.tutorials.utils.ImageViewUtils;
import com.bage.tutorials.view.CircleImageView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.bage.tutorials.R;

public class AboutAuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_author);
        setSupportActionBar(findViewById(R.id.toolbar));
        findViewById(R.id.fab).setOnClickListener(view -> Snackbar.make(view, "to be continue", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        String url = "https://avatars1.githubusercontent.com/u/18094768?s=460&u=1a2cacb3972a01fc3592f3c314b6e6b8e41d59b4&v=4";
        ImageViewUtils.loadImage(this,url,findViewById(R.id.about_im_author),R.drawable.user_null,R.drawable.user_null);


    }

}
