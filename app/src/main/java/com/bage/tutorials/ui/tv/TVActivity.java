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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bage.tutorials.R;
import com.bage.tutorials.component.recycleview.LoadMoreOnScrollListener;
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
    private SwipeRefreshLayout swipeRefreshLayout;
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
        swipeRefreshLayout = findViewById(R.id.layout_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                TVItem item = new TVItem();
                item.setId(list.size() + 1);
                item.setName("CCTVRefresh");
                item.setLogo("XXX" + (list.size() + 1));
                item.setUrl("http://117.169.120.140:8080/live/cctv-10/.m3u8");
                list.add(0,item);

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new LoadMoreOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                TVItem item = new TVItem();
                item.setId(list.size() + 1);
                item.setName("CCTVLoadMore");
                item.setLogo("XXX" + (list.size() + 1));
                item.setUrl("http://117.169.120.140:8080/live/cctv-10/.m3u8");
                list.add(item);

                adapter.notifyDataSetChanged();
            }
        });

        adapter = new MyRecyclerViewAdapter(TVActivity.this, list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(this, R.dimen.divider_height,
                R.color.divider));
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
