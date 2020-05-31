package com.bage.tutorials.ui.tv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bage.tutorials.R;
import com.bage.tutorials.component.recycleview.LoadMoreOnScrollListener;
import com.bage.tutorials.domain.TVItem;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TVFragment extends Fragment {

    private TVViewModel TVViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyRecyclerViewAdapter adapter;
    private List<TVItem> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tv, container, false);
        TVViewModel = ViewModelProviders.of(this, new TVViewModelFactory())
                .get(TVViewModel.class);
        swipeRefreshLayout = root.findViewById(R.id.layout_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TVViewModel.queryAll();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView = root.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new LoadMoreOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
//                TVViewModel.query(currentPage);
            }
        });

        adapter = new MyRecyclerViewAdapter(getActivity(), list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(getActivity(), R.dimen.divider_height,
                R.color.divider));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(true);
        TVViewModel.queryAll();
        TVViewModel.getResult().observe(this, httpResult -> {
            swipeRefreshLayout.setRefreshing(false);
            LoggerUtils.info(TVFragment.class, "data = " + httpResult.getData());
            if (httpResult == null) {
                return;
            }
            if (httpResult.isOk()) {
                list.clear();
                List<TVItem> datas = JsonUtils.fromJson(httpResult.getData(), new TypeToken<List<TVItem>>() {
                }.getType());
                for (TVItem data : datas) {
                    list.add(data);
                }
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }
}