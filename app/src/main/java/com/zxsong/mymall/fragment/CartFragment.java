package com.zxsong.mymall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zxsong.mymall.R;
import com.zxsong.mymall.adapter.CartAdapter;
import com.zxsong.mymall.adapter.decoration.DividerItemDecoration;
import com.zxsong.mymall.bean.ShoppingCart;
import com.zxsong.mymall.utils.CartProvider;
import com.zxsong.mymall.widget.MyToolBar;

import java.util.List;

/**
 * Created by zxsong on 2016/1/5.
 */
public class CartFragment extends Fragment implements View.OnClickListener{

    @ViewInject(R.id.toolbar)
    private MyToolBar toolBar;

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.checkbox_all)
    private CheckBox mCheckBox;

    @ViewInject(R.id.txt_total)
    private TextView mTextView;

    @ViewInject(R.id.btn_order)
    private Button mBtnOrder;

    @ViewInject(R.id.btn_del)
    private Button mBtnDel;

    private static final int ACTION_EDIT = 1;
    private static final int ACTION_COMPLETE = 2;

    private CartAdapter mAdapter;
    private CartProvider cartProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        ViewUtils.inject(this, view);

        mBtnDel.setOnClickListener(this);
        initToolBar();
        cartProvider = new CartProvider(getContext());
        showData();
        return view;
    }

    private void initToolBar() {

        toolBar.hideSearchView();
        toolBar.showTitleView();
        toolBar.setTitle(R.string.cart);
        toolBar.getRightButton().setVisibility(View.VISIBLE);
        toolBar.setRightButtonText("编辑");

        toolBar.getRightButton().setOnClickListener(this);
        toolBar.getRightButton().setTag(ACTION_EDIT);
    }

    private void showData() {

        List<ShoppingCart> carts = cartProvider.getAll();

        mAdapter = new CartAdapter(getContext(), carts,mCheckBox,mTextView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter.checkListen();
    }

    public void refreshData() {

        mAdapter.clearData();
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter.addData(carts);
        mAdapter.showTotalPrice();
        mAdapter.checkListen();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.toolbar_rightButton:
                int action = (int) v.getTag();

                if (action == ACTION_EDIT) {

                    showDeleteBtn();

                } else if (action == ACTION_COMPLETE) {

                    hideDeleteBtn();
                }
                break;
            case R.id.btn_del:
                mAdapter.deleteCart();
                break;
        }

    }

    private void showDeleteBtn() {
        toolBar.setRightButtonText("完成");
        mTextView.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mAdapter.checkAllNone(false);
        mCheckBox.setChecked(false);
        toolBar.getRightButton().setTag(ACTION_COMPLETE);
    }

    private void hideDeleteBtn() {
        toolBar.setRightButtonText("编辑");
        mTextView.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mAdapter.checkAllNone(true);
        mCheckBox.setChecked(true);
        toolBar.getRightButton().setTag(ACTION_EDIT);
        mAdapter.showTotalPrice();
    }
}
