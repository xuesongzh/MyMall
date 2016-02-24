package com.zxsong.mymall.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zxsong.mymall.R;
import com.zxsong.mymall.bean.Wares;

import java.util.List;

/**
 * Created by zxsong on 2016/2/23.
 */
public class HWAdapter extends BaseAdapter<Wares, BaseViewHolder> {

    public HWAdapter(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);

    }

    @Override
    protected void bindData(BaseViewHolder holder, Wares wares) {

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        holder.getTextView(R.id.text_title).setText(wares.getName());
        holder.getTextView(R.id.text_price).setText("￥" + wares.getPrice());
    }
}
