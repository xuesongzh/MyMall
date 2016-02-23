package com.zxsong.mymall.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zxsong.mymall.R;
import com.zxsong.mymall.bean.Wares;

import java.util.List;

/**
 * Created by zxsong on 2016/2/23.
 */
public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {

    private List<Wares> mDatas;

    private LayoutInflater mInflater;

    public HotWaresAdapter(List<Wares> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.template_hot_wares, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.draweeView.setImageURI(Uri.parse(mDatas.get(position).getImgUrl()));
        holder.textTitle.setText(mDatas.get(position).getName());
        holder.textPrice.setText("￥" + mDatas.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<Wares> getDatas() {

        return mDatas;
    }

    public void addData(List<Wares> datas) {

        addData(0, datas);
    }

    public void addData(int position, List<Wares> datas) {

        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyItemRangeChanged(position, datas.size());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView draweeView;
        TextView textTitle;
        TextView textPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textPrice = (TextView) itemView.findViewById(R.id.text_price);

        }
    }
}
