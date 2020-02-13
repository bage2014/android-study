package com.bage.tutorials;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bage.tutorials.ui.login.LoginActivity;

public class BootstrapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);

        Intent i = new Intent(BootstrapActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
