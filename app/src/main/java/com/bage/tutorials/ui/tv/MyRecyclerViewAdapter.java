package com.bage.tutorials.ui.tv;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;

import com.bage.tutorials.R;
import com.bage.tutorials.api.android.AppFavorite;
import com.bage.tutorials.api.android.TVItem;
import com.bage.tutorials.utils.AndroidUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;

import java.util.List;
import java.util.Objects;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<TVItem> list;
    private FavoriteViewModel favoriteViewModel;
    private Context context;

    public MyRecyclerViewAdapter(Context context, List<TVItem> list, FavoriteViewModel favoriteViewModel) {
        this.list = list;
        this.context = context;
        this.favoriteViewModel = favoriteViewModel;
    }

    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_item, parent, false);
        MyViewHolder viewHolder = new MyRecyclerViewAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder holder, int position) {
        TVItem tvItem = list.get(position);
        holder.mText.setText(tvItem.getName());
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
        if(Objects.equals(tvItem.getIsFavorite(), true)){
            holder.mImage.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mText;
        ImageView mImage;

        MyViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.item_tx);
            //添加点击事件
            mText.setOnClickListener(v -> {
                TVItem tvItem = list.get(getLayoutPosition());
                Intent intent = new Intent(context, TVPlayingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tvItem", tvItem);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });

            //添加点击事件
            mImage = itemView.findViewById(R.id.item_iv_favorite);
            mImage.setOnClickListener(v -> {
                TVItem tvItem = list.get(getLayoutPosition());
                LoggerUtils.info(MyRecyclerViewAdapter.class, "tvItem = " + JsonUtils.toJson(tvItem));
                AppFavorite item = new AppFavorite();
                item.setUserId(AndroidUtils.getUserId());
                item.setFavoriteId(tvItem.getId());
                favoriteViewModel.setFavorite(item);
            });
        }
    }


}