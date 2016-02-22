package com.zxsong.mymall.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by zxsong on 2016/2/21.
 */
public class OkHttpHelper {

    private static OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;

    private Handler mHandler;

    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {
        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());
    }


    public static OkHttpHelper getInstance() {

        return mInstance;
    }

    public void get(String url, BaseCallback callback) {

        Request request = buildGetRequest(url);
        request(request, callback);
    }

    public void post(String url, Map<String, String> params, BaseCallback callback) {

        Request request = buildPostRequest(url, params);
        request(request, callback);
    }

    public void request(Request request, final BaseCallback callback) {

        callback.onBeforeRequest(request);

        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                callback.onResponse(response);

                if (response.isSuccessful()) {

                    String result = response.body().string();

                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, result);
                    } else {
                        try {
                            Object obj = mGson.fromJson(result, callback.mType);
                            callbackSuccess(callback, response, obj);
                        } catch (JsonParseException e) {

                            callbackError(callback, response, e);
                        }

                    }
                } else {
                    callbackError(callback, response, null);
                }
            }
        });
    }

    private void callbackError(final BaseCallback callback, final Response response, final Exception e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }

    private void callbackSuccess(final BaseCallback callback, final Response response, final Object obj) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }

    private Request buildPostRequest(String url, Map<String, String> params) {

        return buildRequst(url, HttpMethodType.POST, params);
    }

    private Request buildGetRequest(String url) {

        return buildRequst(url, HttpMethodType.GET, null);
    }

    private Request buildRequst(String url, HttpMethodType methodType, Map<String, String> params) {

        Request.Builder builder = new Request.Builder().url(url);

        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody body = buildFormData(params);
            builder.post(body);
        }

        return builder.build();
    }

    private RequestBody buildFormData(Map<String, String> params) {

        FormEncodingBuilder builder = new FormEncodingBuilder();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }

        return builder.build();
    }

    enum HttpMethodType {

        GET,
        POST
    }
}
