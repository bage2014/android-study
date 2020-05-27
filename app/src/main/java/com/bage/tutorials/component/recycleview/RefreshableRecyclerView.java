package com.bage.tutorials.component.recycleview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RefreshableRecyclerView extends RecyclerView {
    private GridLayoutManager mManager;
    private int mSpanCount;
    private int mScrollAmount;
    private boolean scrolled;
    private String mSizeName;

    public RefreshableRecyclerView(Context context) {
        super(context);
        init();
    }

    public RefreshableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        mSpanCount = 1;

        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollAmount += dy;
                setScrolled(mScrollAmount != 0);
            }
        });

        mManager = new GridLayoutManager(getContext(), mSpanCount);
        setLayoutManager(mManager);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    public GridLayoutManager getManager() {
        return mManager;
    }

    public boolean hasScrolled() {
        return scrolled;
    }

    public void setScrolled(boolean isScrolled) {
        scrolled = isScrolled;
    }

    public int getSpanCount() {
        return mManager.getSpanCount();
    }

    protected void setSpanCount(int count) {
        mManager.setSpanCount(count);
    }

}
