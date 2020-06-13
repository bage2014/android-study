package com.bage.tutorials;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BootstrapActivity extends AppCompatActivity {

    public static final int appVersion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);

//        Intent i = new Intent(BootstrapActivity.this, LoginActivity.class);
//        startActivity(i);
//        finish();

        //Complete and destroy login activity once successful
        Intent i = new Intent(BootstrapActivity.this, MainActivity.class);
        startActivity(i);
        finish(); // 关闭当前

    }
}
