package com.zxsong.mymall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zxsong.mymall.Contants;
import com.zxsong.mymall.R;
import com.zxsong.mymall.adapter.BaseAdapter;
import com.zxsong.mymall.adapter.CategoryAdapter;
import com.zxsong.mymall.adapter.CategoryWaresAdapter;
import com.zxsong.mymall.adapter.decoration.DividerGridItemDecoration;
import com.zxsong.mymall.adapter.decoration.DividerItemDecoration;
import com.zxsong.mymall.bean.Banner;
import com.zxsong.mymall.bean.Category;
import com.zxsong.mymall.bean.Page;
import com.zxsong.mymall.bean.Wares;
import com.zxsong.mymall.http.BaseCallback;
import com.zxsong.mymall.http.OkHttpHelper;
import com.zxsong.mymall.http.SpotsCallBack;

import java.util.List;

/**
 * Created by zxsong on 2016/1/5.
 */
public class CategoryFragment extends Fragment {

    @ViewInject(R.id.recyclerview_category)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout;

    @ViewInject(R.id.recyclerview_wares)
    private RecyclerView mRecyclerViewWares;

    @ViewInject(R.id.refresh_layout)
    private MaterialRefreshLayout mRefreshLayout;


    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;
    private long categoryId = 1;


    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    private CategoryAdapter mAdapter;
    private CategoryWaresAdapter mWaresAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ViewUtils.inject(this, view);

        requestCategoryData();
        requestBannerData();
        initRefreshLayout();
        return view;
    }

    private void requestCategoryData() {

        httpHelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {


            @Override
            public void onSuccess(Response response, List<Category> categories) {

                showCategoryData(categories);

                if (categories != null && categories.size() > 0) {
                    //显示第一个分类的商品数据
                    requestWaresData(categories.get(0).getId());
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }


    private void showCategoryData(List<Category> categories) {

        mAdapter = new CategoryAdapter(getContext(), categories);

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                categoryId = mAdapter.getItem(position).getId();
                //刷新后currPage需要重置
                currPage = 1;
                state = STATE_NORMAL;

                requestWaresData(categoryId);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration
                .VERTICAL_LIST));
    }

    private void requestBannerData() {

        httpHelper.get(Contants.API.BANNER_HOME + "?type=1", new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {

                showBannerData(banners);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void showBannerData(List<Banner> banners) {

        if (banners != null) {
            for (Banner banner : banners) {
                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
            }
        }

        //转场动画
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        // mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        //描述信息的动画
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(3000);
    }

    private void initRefreshLayout() {

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshWaresData();
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
        requestWaresData(categoryId);
    }

    private void refreshWaresData() {

        currPage = 1;
        state = STATE_REFREH;
        requestWaresData(categoryId);
    }


    private void requestWaresData(long categoryId) {

        String url = Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" +
                pageSize;

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

                currPage = waresPage.getCurrentPage();
                pageSize = waresPage.getPageSize();
                totalPage = waresPage.getTotalPage();

                showWaresData(waresPage.getList());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    private void showWaresData(List<Wares> wares) {

        switch (state) {
            case STATE_NORMAL:
                //连续点击分类列表，右边商品分割线会越来越大，需要判断
                if (mWaresAdapter == null) {
                    mWaresAdapter = new CategoryWaresAdapter(getActivity(), wares);
                    mRecyclerViewWares.setAdapter(mWaresAdapter);
                    mRecyclerViewWares.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    mRecyclerViewWares.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerViewWares.addItemDecoration(new DividerGridItemDecoration(getContext()));
                } else {
                    mWaresAdapter.clearData();
                    mWaresAdapter.addData(wares);
                }

                break;

            case STATE_REFREH:
                mWaresAdapter.addData(wares);
                mRecyclerViewWares.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                mWaresAdapter.addData(mWaresAdapter.getDatas().size(), wares);
                mRecyclerViewWares.scrollToPosition(mWaresAdapter.getDatas().size() - wares.size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }

}
