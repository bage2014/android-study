package com.bage.tutorials.ui.tv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bage.tutorials.R;
import com.bage.tutorials.domain.TVItem;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TVActivity extends AppCompatActivity {

    private TVViewModel TVViewModel;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<TVItem> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        TVViewModel = ViewModelProviders.of(this, new TVViewModelFactory())
                .get(TVViewModel.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MyRecyclerViewAdapter(TVActivity.this, list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        TVViewModel.init("some param");
        TVViewModel.getResult().observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(@Nullable HttpResult httpResult) {
                LoggerUtils.info(TVActivity.class, "data = " + httpResult.getData());
                if (httpResult == null) {
                    return;
                }
                // do something
                list.clear();
                List<TVItem> datas = JsonUtils.fromJson(httpResult.getData(), new TypeToken<List<TVItem>>() {
                }.getType());
                for (TVItem data : datas) {
                    list.add(data);
                }

                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void back(View v) {
        finish();
    }

}
