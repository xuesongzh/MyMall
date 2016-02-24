package com.zxsong.mymall.adapter;

import android.content.Context;

import com.zxsong.mymall.R;
import com.zxsong.mymall.bean.Category;

import java.util.List;

/**
 * Created by zxsong on 2016/2/23.
 */
public class CategoryAdapter extends BaseAdapter<Category,BaseViewHolder> {

    public CategoryAdapter(Context context , List<Category> datas) {
        super(context, R.layout.template_single_text, datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, Category category) {

        holder.getTextView(R.id.textView).setText(category.getName());
    }
}
