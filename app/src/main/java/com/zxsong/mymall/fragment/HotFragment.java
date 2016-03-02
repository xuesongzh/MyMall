package com.zxsong.mymall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zxsong.mymall.Contants;
import com.zxsong.mymall.R;
import com.zxsong.mymall.adapter.HWAdapter;
import com.zxsong.mymall.adapter.decoration.DividerItemDecoration;
import com.zxsong.mymall.bean.Page;
import com.zxsong.mymall.bean.Wares;
import com.zxsong.mymall.http.BaseCallback;
import com.zxsong.mymall.http.OkHttpHelper;
import com.zxsong.mymall.widget.MyToolBar;

import java.util.List;

/**
 * Created by zxsong on 2016/1/5.
 */
public class HotFragment extends Fragment {

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int pageSize = 10;
    private int totalPage = 1;

    private List<Wares> datas;
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private MyToolBar toolBar;
//    private HotWaresAdapter mAdapter;
    private HWAdapter mAdapter;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        toolBar = (MyToolBar) view.findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_view);

        initToolBar();
        initRefreshLayout();
        getData();

        return view;
    }

    private void initToolBar() {

        toolBar.hideSearchView();
        toolBar.showTitleView();
        toolBar.setTitle(R.string.hot);
    }

    private void initRefreshLayout() {

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (currPage < totalPage) {
                    loadMoreData();
                } else {
                    Toast.makeText(getActivity(), "已经没有数据了", Toast.LENGTH_LONG).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void loadMoreData() {

        currPage = ++currPage;
        state = STATE_MORE;
        getData();
    }

    private void refreshData() {

        currPage = 1;
        state = STATE_REFREH;
        getData();
    }


    private void getData() {

        String url = Contants.API.WARES_HOT + "?curPage=" + currPage + "&pageSize=" + pageSize;

        httpHelper.get(url, new BaseCallback<Page<Wares>>() {
            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                datas = waresPage.getList();
                currPage = waresPage.getCurrentPage();
                pageSize = waresPage.getPageSize();
                totalPage = waresPage.getTotalPage();
                showData();
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

//        httpHelper.get(url, new SpotsCallBack<Page<Wares>>(getActivity()) {
//
//            @Override
//            public void onSuccess(Response response, Page<Wares> waresPage) {
//
//                datas = waresPage.getList();
//                currPage = waresPage.getCurrentPage();
//                pageSize = waresPage.getPageSize();
//                totalPage = waresPage.getTotalPage();
//                showData();
//            }
//
//            @Override
//            public void onError(Response response, int code, Exception e) {
//
//            }
//        });
    }

    private void showData() {

        switch (state) {
            case STATE_NORMAL:
                mAdapter = new HWAdapter(getActivity(),datas);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration
                        .VERTICAL_LIST));
                break;
            case STATE_REFREH:
                mAdapter.addData(datas);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mAdapter.addData(mAdapter.getDatas().size(), datas);
                mRecyclerView.scrollToPosition(mAdapter.getDatas().size()-datas.size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }

    }
}
