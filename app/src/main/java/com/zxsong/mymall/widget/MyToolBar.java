package com.zxsong.mymall.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zxsong.mymall.R;

/**
 * Created by zxsong on 2016/1/6.
 */
public class MyToolBar extends Toolbar {

    private LayoutInflater mInflater;
    private View mView;
    private TextView mTextTitle;
    private EditText mSearchView;
    private ImageButton mRightImageButton;

    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        //setContentInsetsRelative(10,10);
        if (attrs != null) {
            //添加右侧图标
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyleAttr, 0);

            final Drawable rightIcon = a.getDrawable(R.styleable.MyToolBar_rightButtonIcon);
            if (rightIcon != null) {
                //   setNavigationIcon(navIcon);
                setRightButtonIcon(rightIcon);
            }
            //添加搜索，与title互斥
            boolean isShowSearchView = a.getBoolean(R.styleable.MyToolBar_isShowSearchView, false);
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }

            a.recycle();
        }


    }

    public void setRightButtonIcon(Drawable icon) {
        if (mRightImageButton != null) {
            mRightImageButton.setImageDrawable(icon);
            mRightImageButton.setVisibility(VISIBLE);

        }
    }

    public void setRightButtonOnClickListener(OnClickListener li) {
        mRightImageButton.setOnClickListener(li);
    }

    private void initView() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);

            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchView);
            mRightImageButton = (ImageButton) mView.findViewById(R.id.toolbar_rightButton);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_HORIZONTAL);

            addView(mView, lp);

        }

    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        //先要初始化控件
        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    public void showSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(GONE);

        }
    }

    public void showTitleView() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(VISIBLE);
        }
    }

    public void hideTitleView() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(GONE);

        }
    }
}