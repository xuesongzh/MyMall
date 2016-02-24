package com.zxsong.mymall.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zxsong on 2016/2/23.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private SparseArray<View> views;

    private BaseAdapter.OnItemClickListener listener;

    public BaseViewHolder(View itemView,BaseAdapter.OnItemClickListener listener) {
        super(itemView);

        //设置监听，不要忘了
        itemView.setOnClickListener(this);
        this.views = new SparseArray<View>();
        this.listener = listener;
    }

    public TextView getTextView(int viewId) {

        return retrieveView(viewId);
    }

    public Button getButton(int viewId) {

        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {

        return retrieveView(viewId);
    }

    public View getView(int viewId) {

        return retrieveView(viewId);
    }

    private <T extends View> T retrieveView(int viewId) {

        View view = views.get(viewId);

        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }

        return (T) view;
    }


    @Override
    public void onClick(View v) {

        if (listener != null) {
            listener.onItemClick(v, getLayoutPosition());
        }
    }
}
