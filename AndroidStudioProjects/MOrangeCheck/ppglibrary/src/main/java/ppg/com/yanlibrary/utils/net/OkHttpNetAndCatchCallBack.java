package ppg.com.yanlibrary.utils.net;


import android.content.Context;
import android.text.TextUtils;


import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.utils.JsonBaseBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import MyLogger.Logger;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import ppg.com.yanlibrary.utils.ToastUtil;
import ppg.com.yanlibrary.utils.json.JsonFileCache;
import xprogressdialog.XProgressDialog;

/**
 * 先从缓存中读取数据,在度网络数据,onResponse会走两次
 * Created by ppg on 2016/4/14.
 */
public abstract class OkHttpNetAndCatchCallBack {
    /**
     * 解析为JsonBaseBean,回调给调用者,post方式,会打印参数
     */
    public static abstract class PostCatchTaskCallBack extends Callback<JsonBaseBean> {

        Context mContent;
        private XProgressDialog toastProgress;
        private boolean isShowProgress = true;
        private String progressMsg;
        public String URL;

        /**
         * 缓存模式，会以URL为key缓存数据到share中，每次访问前都会判断share中是否有数据，有的话直接返回数据，不访问网络
         *
         * @param Content 上下文
         * @param Msg     进度条的信息,没有则用""表示不开启进度条
         */
        public PostCatchTaskCallBack(Context Content, String Msg) {
            this.mContent = Content;
            this.progressMsg = Msg;
            if (mContent != null)
                toastProgress = new XProgressDialog(mContent, 2);
        }

        @Override
        public JsonBaseBean parseNetworkResponse(Response response, int id) throws Exception {
            JsonBaseBean object = new JsonBaseBean();
            String content = response.body().string();
            object.analysisJson(content);
            String url = response.request().url().toString();
            String mkey = url;
            //以url+post的参数为KEY,把成功获取到的数据转成String存入share
            if (!TextUtils.isEmpty(content)) {
                JsonFileCache.storeData(mContent, mkey, content);
            }
            return object;
        }


        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            //开启进度条
            if (mContent != null) {
                this.isShowProgress = true;
                if (TextUtils.isEmpty(progressMsg))
                    isShowProgress = false;
                if (isShowProgress) {
                    progressStart(progressMsg);
                }
            }
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            progressClose();
        }


        @Override
        public void onError(Call call, Exception e, int id) {
            if (call.isCanceled()) {
                ToastUtil.toast2_bottom(mContent, "网络连接异常");
                return;
            }
            Logger.init("okdebug");
            Logger.d("onError:  " + e.getMessage());
            progressClose();
            ToastUtil.toast2_bottom(mContent, "网络连接异常");
        }

        /**
         * 简单的进度启动与关闭
         *
         * @param message
         */
        protected void progressStart(String message) {
            if (null == toastProgress)
                return;
            toastProgress.setMessage(message);
            if (!toastProgress.isShowing()) {
                toastProgress.show();
                //((Activity) progressDialog.getContext()).getWindow().setLayout(50, 50);
            }
        }

        protected void progressClose() {
            if (null == toastProgress)
                return;
            if (toastProgress.isShowing())
                toastProgress.dismiss();
        }

        //循环拼接post请求中的参数
        public StringBuffer fromBodyLog(FormBody formBody) {
            StringBuffer body = new StringBuffer();
            for (int i = 0; i < formBody.size(); i++) {
                try {
                    body.append("" + formBody.encodedName(i) + " = " + URLDecoder.decode(formBody.encodedValue(i), "UTF-8") + "\n");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return body;
        }

        //循环拼接post请求中的参数,去掉/n的方法,如果加了\n第二次取share的数据会取不到
        public StringBuffer fromBodyLog2(FormBody formBody) {
            StringBuffer body = new StringBuffer();
            for (int i = 0; i < formBody.size(); i++) {
                try {
                    body.append("" + formBody.encodedName(i) + "=" + URLDecoder.decode(formBody.encodedValue(i), "UTF-8") + "");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return body;
        }

    }


    public static abstract class GetTaskCallBack extends Callback<JsonBaseBean> {

        Context mContent;
        private XProgressDialog toastProgress;
        private boolean isShowProgress = true;
        private String progressMsg;
        public String URL;

        /**
         * @param Content 上下文
         * @param Msg     进度条的信息,没有则用""表示不开启进度条
         */
        public GetTaskCallBack(Context Content, String Msg) {
            this.mContent = Content;
            this.progressMsg = Msg;
            if (mContent != null)
                toastProgress = new XProgressDialog(mContent, 2);
        }

        @Override
        public JsonBaseBean parseNetworkResponse(Response response, int id) throws Exception {
            JsonBaseBean object = new JsonBaseBean();
            String content = response.body().string();
            object.analysisJson(content);
            String url = response.request().url().toString();
            String mkey = url;
            //以url+post的参数为KEY,把成功获取到的数据转成String存入share
            if (!TextUtils.isEmpty(content)) {
                JsonFileCache.storeData(mContent, mkey, content);
            }
            return object;
        }


        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            //开启进度条
            if (mContent != null) {
                this.isShowProgress = true;
                if (TextUtils.isEmpty(progressMsg))
                    isShowProgress = false;
                if (isShowProgress) {
                    progressStart(progressMsg);
                }
            }
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Logger.init("okdebug");
            Logger.d("onError:  " + e.getMessage());
            progressClose();
            ToastUtil.toast2_bottom(mContent, "网络连接异常");
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            progressClose();
        }

        /**
         * 简单的进度启动与关闭
         *
         * @param message
         */
        protected void progressStart(String message) {
            if (null == toastProgress)
                return;
            toastProgress.setMessage(message);
            if (!toastProgress.isShowing()) {
                toastProgress.show();
                //((Activity) progressDialog.getContext()).getWindow().setLayout(50, 50);
            }
        }

        protected void progressClose() {
            if (null == toastProgress)
                return;
            if (toastProgress.isShowing())
                toastProgress.dismiss();
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
