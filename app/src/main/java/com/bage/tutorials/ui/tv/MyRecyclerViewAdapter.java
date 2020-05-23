package com.bage.tutorials.ui.tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bage.tutorials.R;
import com.bage.tutorials.domain.TVItem;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<TVItem> list;
    private Context context;

    public MyRecyclerViewAdapter(Context context, List<TVItem> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_item, parent, false);
        MyViewHolder viewHolder = new MyRecyclerViewAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.mText.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mText;

        MyViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.item_tx);

            //添加点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TVItem tvItem = list.get(getLayoutPosition());
                    Intent intent = new Intent(context, TVPlayingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tvItem", tvItem);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }


}