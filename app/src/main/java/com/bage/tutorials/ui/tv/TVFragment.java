package com.bage.tutorials.ui.tv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bage.tutorials.MainActivity;
import com.bage.tutorials.R;
import com.bage.tutorials.api.android.AppFavorite;
import com.bage.tutorials.api.android.TVItem;
import com.bage.tutorials.component.recycleview.LoadMoreOnScrollListener;
import com.bage.tutorials.utils.AndroidUtils;
import com.bage.tutorials.utils.HttpResultUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TVFragment extends Fragment {

    private TVViewModel TVViewModel;
    private FavoriteViewModel favoriteViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout tabLayout;
    private MyRecyclerViewAdapter adapter;
    private List<TVItem> list = new ArrayList<>();
    private List<TVItem> originList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tv, container, false);
        TVViewModel = ViewModelProviders.of(this, new TVViewModelFactory())
                .get(TVViewModel.class);
        favoriteViewModel = ViewModelProviders.of(this, new FavoriteViewModelFactory())
                .get(FavoriteViewModel.class);
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
        FragmentActivity activity = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new LoadMoreOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
//                TVViewModel.query(currentPage);
            }
        });

        adapter = new MyRecyclerViewAdapter(activity, list,favoriteViewModel);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(activity, R.dimen.divider_height,
                R.color.divider));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(true);
        TVViewModel.queryAll();
        TVViewModel.getResult().observe(this, httpResult -> {
            swipeRefreshLayout.setRefreshing(false);
            if (HttpResultUtils.isOk(httpResult)) {
                list.clear();
                List<TVItem> datas = JsonUtils.fromJson(httpResult.getData(), new TypeToken<List<TVItem>>() {
                }.getType());
                for (TVItem data : datas) {
                    list.add(data);
                }
                originList = new ArrayList<>(list);
                adapter.notifyDataSetChanged();
            } else {
                HttpResultUtils.errorCallback(activity, httpResult);
            }
        });

        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setSearchViewVisibility(true);
        }

        tabLayout = root.findViewById(R.id.tabs_tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("tab onTabSelected " + tab.getPosition());
//                onQueryFavorite(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        favoriteViewModel.queryByUserId(AndroidUtils.getUserId(), 1);
        favoriteViewModel.getResult().observe(this, httpResult -> {
            if (HttpResultUtils.isOk(httpResult)) {
                List<AppFavorite> favoriteList = JsonUtils.fromJson(httpResult.getData(), new TypeToken<List<AppFavorite>>() {
                }.getType());
                for (AppFavorite appFavorite : favoriteList) {
                    for (TVItem data : list) {
                        if (Objects.equals(appFavorite.getFavoriteId(), data.getId())) {
                            data.setIsFavorite(true);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                HttpResultUtils.errorCallback(activity, httpResult);
            }
        });
        favoriteViewModel.getUpdateResult().observe(this, httpResult -> {
            if (HttpResultUtils.isOk(httpResult)) {
                favoriteViewModel.queryByUserId(AndroidUtils.getUserId(), 1);
            } else {
                HttpResultUtils.errorCallback(activity, httpResult);
            }
        });;
        return root;
    }

    private boolean onQueryFavorite(int position) {
        swipeRefreshLayout.setRefreshing(true);
        list.clear();
        for (int i = 0; i < originList.size(); i++) {
            int max = position == 1 ? 5 : originList.size();
            if (i <= max) {
                list.add(originList.get(i));
            }
        }
        ;
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        return true;
    }

    public boolean onQueryTextChange(String newText) {
        swipeRefreshLayout.setRefreshing(true);
        list.clear();
        if (Objects.isNull(newText) || newText.isEmpty()) {
            list.addAll(originList);
        } else {
            originList.forEach(item -> {
                if (item.getName().contains(newText)) {
                    list.add(item);
                }
            });
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        return true;
    }
}