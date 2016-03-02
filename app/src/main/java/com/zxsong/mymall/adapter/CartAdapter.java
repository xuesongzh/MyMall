package com.zxsong.mymall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zxsong.mymall.R;
import com.zxsong.mymall.bean.ShoppingCart;
import com.zxsong.mymall.utils.CartProvider;
import com.zxsong.mymall.widget.NumberAddSubView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zxsong on 2016/2/29.
 */
public class CartAdapter extends BaseAdapter<ShoppingCart, BaseViewHolder> implements BaseAdapter.OnItemClickListener {

    private CheckBox checkBox;
    private TextView textView;

    private CartProvider provider;

    public CartAdapter(Context context, List<ShoppingCart> datas, final CheckBox cb, TextView tv) {

        super(context, R.layout.template_cart, datas);
        setCheckBox(cb);
        setTextView(tv);
        provider = new CartProvider(context);
        setOnItemClickListener(this);
        showTotalPrice();
    }

    private void setTextView(TextView tv) {
        this.textView = tv;
    }

    private void setCheckBox(CheckBox cb) {
        this.checkBox = cb;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAllNone(checkBox.isChecked());
                showTotalPrice();
            }
        });
    }

    public void checkAllNone(boolean isChecked) {
        if (mDatas != null && mDatas.size() > 0) {

            for (int i = 0; i < mDatas.size(); i++) {

                getItem(i).setIsChecked(isChecked);
                notifyItemChanged(i);
            }
        }
    }


    @Override
    protected void bindData(BaseViewHolder holder, final ShoppingCart cart) {

        CheckBox checkBox = (CheckBox) holder.getView(R.id.check_box);
        checkBox.setChecked(cart.isChecked());

        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(cart.getImgUrl()));

        holder.getTextView(R.id.text_title).setText(cart.getName());
        holder.getTextView(R.id.text_price).setText("￥" + cart.getPrice());

        NumberAddSubView numberAddSubView = (NumberAddSubView) holder.getView(R.id.num_control);
        numberAddSubView.setValue(cart.getCount());
        numberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {

                cart.setCount(value);
                provider.updata(cart);
                showTotalPrice();
            }

            @Override
            public void onButtonSubClick(View view, int value) {

                cart.setCount(value);
                provider.updata(cart);
                showTotalPrice();
            }
        });
    }


    public void showTotalPrice() {

        float total = getTotalPrice();
        textView.setText("合计：" + total);
    }


    private float getTotalPrice() {

        float sum = 0;
        if (mDatas == null || mDatas.size() <= 0) {
            return sum;
        }
        for (ShoppingCart cart : mDatas
                ) {
            //只有选择的才加起来
            if (cart.isChecked()) {

                sum += cart.getPrice() * cart.getCount();
            }

        }

        return sum;
    }

    @Override
    public void onItemClick(View view, int position) {

        ShoppingCart cart = getItem(position);
        cart.setIsChecked(!cart.isChecked());
        notifyItemChanged(position);
        checkListen();
        showTotalPrice();
    }

    public void checkListen() {

        int count = 0;
        if (mDatas != null && mDatas.size() > 0) {
            for (ShoppingCart cart : mDatas
                    ) {
                if (!cart.isChecked()) {

                    checkBox.setChecked(false);
                    break;
                } else {

                    count += 1;
                }
            }

            if (count == mDatas.size()) {

                checkBox.setChecked(true);
            }

        }
    }

    public void deleteCart() {

        if (mDatas != null && mDatas.size() > 0) {

//            mDatas.remove(cart)之后，mDatas.size()发生变化，不能再用这种方法遍历
//            for (ShoppingCart cart : mDatas) {
//
//                if (cart.isChecked()) {
//                    int position = mDatas.indexOf(cart);
//                    provider.delete(cart);
//                    mDatas.remove(cart);
//                    notifyItemRemoved(mDatas.indexOf(cart));
//                }
//            }
//          使用迭代器的方法遍历
            Iterator iterator = mDatas.iterator();
            while (iterator.hasNext()) {

                ShoppingCart cart = (ShoppingCart) iterator.next();
                if (cart.isChecked()) {

                    int position = mDatas.indexOf(cart);
                    provider.delete(cart);
                    iterator.remove();
                    notifyItemRemoved(position);
                }
            }

        }

        if (mDatas.size() <= 0) {

            checkBox.setChecked(false);
        }
    }
}
