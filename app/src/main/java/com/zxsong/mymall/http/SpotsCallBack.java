package com.zxsong.mymall.http;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dmax.dialog.SpotsDialog;

/**
 * Created by zxsong on 2016/2/21.
 */
public abstract class SpotsCallBack<T> extends BaseCallback<T> {

    private Context mContext;

    private SpotsDialog mDialog;

    public SpotsCallBack(Context Context) {

        mContext = Context;

        mDialog = new SpotsDialog(mContext);
    }

    public void showDialog() {

        mDialog.show();
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    public void setLoadMessage(int resId) {

        mDialog.setMessage(mContext.getString(resId));

    }

    @Override
    public void onBeforeRequest(Request request) {

        showDialog();
    }

    @Override
    public void onFailure(Request request, Exception e) {

        dismissDialog();
    }

    @Override
    public void onResponse(Response response) {

        dismissDialog();
    }

}
