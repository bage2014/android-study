package com.bage.tutorials.ui.tv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bage.tutorials.R;
import com.bage.tutorials.api.android.AppFavorite;
import com.bage.tutorials.api.android.TVItem;
import com.bage.tutorials.component.dialog.AlertDialogHelper;
import com.bage.tutorials.utils.AndroidUtils;
import com.bage.tutorials.utils.JsonUtils;
import com.bage.tutorials.utils.LoggerUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<TVItem> list;
    private SetFavoriteClickListener setFavoriteClickListener;
    private Context context;
    private AlertDialogHelper alertDialogHelper;

    public MyRecyclerViewAdapter(Context context, List<TVItem> list, SetFavoriteClickListener setFavoriteClickListener) {
        this.list = list;
        this.context = context;
        this.setFavoriteClickListener = setFavoriteClickListener;
        alertDialogHelper = new AlertDialogHelper(context);
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
        int iconId = R.drawable.ic_favorite_black_24dp;
        if (Objects.nonNull(tvItem.getAppFavorite())) {
            iconId = R.drawable.ic_favorite_red_24dp;
        }
        Drawable drawable = context.getResources().getDrawable(iconId);
        holder.mImage.setImageDrawable(drawable);
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

//                alertDialogHelper.showCustomDialog(R.layout.dialog_tv_listview, "", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        TVItem tvItem = list.get(getLayoutPosition());
//                        LoggerUtils.info(MyRecyclerViewAdapter.class, "onClick tvItem = " + JsonUtils.toJson(tvItem));                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });

                TVItem tvItem = list.get(getLayoutPosition());
                tvItem.setUrls(new ArrayList<String>(Arrays.asList("A", "B", "C")));
                new MaterialAlertDialogBuilder(context)
                        .setSingleChoiceItems(tvItem.getUrls().toArray(new String[0]), 1, (dialog, which) -> {
                            dialog.dismiss();
                            LoggerUtils.info(MyRecyclerViewAdapter.class, "onClick tvItem = " + JsonUtils.toJson(tvItem));
                            TVItem tvItem1 = list.get(getLayoutPosition());
                            Intent intent = new Intent(context, TVPlayingActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("tvItem", tvItem1);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        })
                        .show();


            });

            //添加点击事件
            mImage = itemView.findViewById(R.id.item_iv_favorite);
            mImage.setOnClickListener(v -> {
                TVItem tvItem = list.get(getLayoutPosition());
                LoggerUtils.info(MyRecyclerViewAdapter.class, "tvItem = " + JsonUtils.toJson(tvItem));
                AppFavorite appFavorite = tvItem.getAppFavorite();
                if (Objects.isNull(appFavorite)) {
                    appFavorite = new AppFavorite();
                    appFavorite.setUserId(AndroidUtils.getUserId());
                    appFavorite.setFavoriteId(tvItem.getId());
                }
                setFavoriteClickListener.setAppFavorite(appFavorite);
            });
        }
    }


}