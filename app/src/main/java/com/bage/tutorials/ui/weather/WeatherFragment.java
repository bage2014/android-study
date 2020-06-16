package com.bage.tutorials.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bage.tutorials.R;
import com.bage.tutorials.component.recycleview.LoadMoreOnScrollListener;
import com.bage.tutorials.domain.weather.DayWeather;
import com.bage.tutorials.domain.weather.WeekWeather;
import com.bage.tutorials.http.HttpResult;
import com.bage.tutorials.ui.tv.MyItemDecoration;
import com.bage.tutorials.ui.tv.MyRecyclerViewAdapter;
import com.bage.tutorials.utils.AndroidUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private WeatherRecyclerViewAdapter adapter;
    private List<DayWeather> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather, container, false);

        swipeRefreshLayout = root.findViewById(R.id.layout_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherViewModel.query(AndroidUtils.getIp());
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

        adapter = new WeatherRecyclerViewAdapter(activity, list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyItemDecoration(activity, R.dimen.divider_height,
                R.color.divider));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(true);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.query(AndroidUtils.getIp());
        weatherViewModel.getResult().observe(this, new Observer<HttpResult>() {
            @Override
            public void onChanged(@Nullable HttpResult httpResult) {
                swipeRefreshLayout.setRefreshing(false);
                LoggerUtils.info(WeatherFragment.class,JsonUtils.toJson(httpResult));
                if (httpResult.isOk()) {
                    String data = httpResult.getData();
                    WeekWeather weekWeather = JsonUtils.fromJson(data, WeekWeather.class);
                    list.clear();
                    list.addAll(weekWeather.getDays());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return root;
    }
}