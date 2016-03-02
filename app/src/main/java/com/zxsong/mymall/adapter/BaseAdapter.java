package com.zxsong.mymall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zxsong on 2016/2/23.
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected final Context context;

    protected final int layoutResId;

    protected List<T> mDatas;

    private LayoutInflater mInflater;

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }

    public BaseAdapter(Context context, int layoutResId, List<T> datas) {

        this.context = context;
        this.layoutResId = layoutResId;
        this.mDatas = datas;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(layoutResId, parent, false);
        return new BaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = mDatas.get(position);
        bindData(holder, t);
    }

    @Override
    public int getItemCount() {

        if (mDatas == null || mDatas.size() <= 0) {

            return 0;
        }

        return mDatas.size();
    }

    public T getItem(int position) {

        if (position >= mDatas.size())
            return null;

        return mDatas.get(position);
    }

    public List<T> getDatas() {

        return mDatas;
    }

    public void clearData() {
        int itemCount = mDatas.size();
        mDatas.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    public void addData(List<T> datas) {

        addData(0, datas);
    }

    public void addData(int position, List<T> datas) {

        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyItemRangeChanged(position, datas.size());
        }
    }

    protected abstract void bindData(BaseViewHolder holder, T t);

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

}
