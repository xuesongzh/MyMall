package com.zxsong.mymall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zxsong.mymall.R;
import com.zxsong.mymall.bean.ShoppingCart;
import com.zxsong.mymall.bean.Wares;
import com.zxsong.mymall.utils.CartProvider;
import com.zxsong.mymall.utils.ToastUtils;

import java.util.List;

/**
 * Created by zxsong on 2016/2/23.
 */
public class HWAdapter extends BaseAdapter<Wares, BaseViewHolder> {

    CartProvider provider;

    public HWAdapter(Context context, List<Wares> datas) {

        super(context, R.layout.template_hot_wares, datas);
        provider = new CartProvider(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, final Wares wares) {

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        holder.getTextView(R.id.text_title).setText(wares.getName());
        holder.getTextView(R.id.text_price).setText("￥" + wares.getPrice());

        Button button = holder.getButton(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                provider.put(convertData(wares));
                ToastUtils.show(context,"已添加到购物车");
            }
        });
    }

    public ShoppingCart convertData(Wares wares) {

        ShoppingCart cart = new ShoppingCart();

        cart.setId(wares.getId());
        cart.setDescripition(wares.getDescripition());
        cart.setImgUrl(wares.getImgUrl());
        cart.setName(wares.getName());
        cart.setPrice(wares.getPrice());

        return cart;

    }
}
