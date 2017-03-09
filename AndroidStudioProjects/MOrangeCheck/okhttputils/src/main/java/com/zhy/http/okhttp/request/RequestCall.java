package com.zhy.http.okhttp.request;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import com.zhy.http.okhttp.utils.JsonBaseBean;
import com.zhy.http.okhttp.utils.JsonFileCache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 15/12/15.
 * 对OkHttpRequest的封装，对外提供更多的接口：cancel(),readTimeOut()...
 */
public class RequestCall {
    private OkHttpRequest okHttpRequest;
    private Request request;
    private Call call;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;

    private OkHttpClient clone;

    public RequestCall(OkHttpRequest request) {
        this.okHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(Callback callback) {
        request = generateRequest(callback);

        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;

            clone = OkHttpUtils.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            call = clone.newCall(request);
        } else {
            call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        }
        return call;
    }

    private Request generateRequest(Callback callback) {
        return okHttpRequest.generateRequest(callback);
    }

    public void execute(Callback callback) {
        buildCall(callback);

        if (callback != null) {
            callback.onBefore(request, getOkHttpRequest().getId());
        }

        OkHttpUtils.getInstance().execute(this, callback);
    }

    /**
     * 优先使用缓存的回调
     *
     * @param mContent  上下文
     * @param callback  第一个参数是上下文
     * @param cacheType 缓存策略
     */
    public void executeCatch(Context mContent, Callback callback, RequestCall.CacheMode cacheType) {
        buildCall(callback);
        if (callback != null) {
            callback.onBefore(request, getOkHttpRequest().getId());
        }
        //根据模式选择是否读取网络数据
        switch (cacheType) {
            case ONLY_CACHE://如果缓存存在,则使用缓存,不在则读取网络
                if (JsonFileCache.isExistData(mContent, request.url().toString())) {
                    String content = JsonFileCache.extractData(mContent, request.url().toString());
                    JsonBaseBean object = new JsonBaseBean();
                    object.analysisJson(content);
                    callback.onResponse(object, getOkHttpRequest().getId());
                    callback.onAfter(getOkHttpRequest().getId());
                } else {
                    OkHttpUtils.getInstance().execute(this, callback);
                }
                break;
            case ONLY_NETWORK://只读网络
                OkHttpUtils.getInstance().execute(this, callback);
                break;
            case CACHE_AND_NETWORK://如果缓存存在先读缓存,然后再读网络.
                if (JsonFileCache.isExistData(mContent, request.url().toString())) {
                    String content = JsonFileCache.extractData(mContent, request.url().toString());
                    JsonBaseBean object = new JsonBaseBean();
                    object.analysisJson(content);
                    callback.onResponse(object, getOkHttpRequest().getId());
                }
                OkHttpUtils.getInstance().execute(this, callback);
                break;
            default:
                OkHttpUtils.getInstance().execute(this, callback);
                break;
        }
    }


    public Call getCall() {
        return call;
    }

    public Request getRequest() {
        return request;
    }

    public OkHttpRequest getOkHttpRequest() {
        return okHttpRequest;
    }

    public Response execute() throws IOException {
        buildCall(null);
        return call.execute();
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    /**
     * ONLY_CACHE //如果缓存存在,则使用缓存,不在则读取网络
     * ONLY_NETWORK://只读网络
     * CACHE_AND_NETWORK://如果缓存存在先读缓存,然后再读网络.
     */
    public enum CacheMode {
        ONLY_CACHE,
        CACHE_AND_NETWORK,
        ONLY_NETWORK,
        //  CYCLE_NETWORK;
    }

}
