package webapps.MOrangeCheck.com.Tool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.PopupWindow;


import ppg.com.yanlibrary.utils.ToastUtil;
import webapps.MOrangeCheck.com.Views.ShareModalDialog;

/**
 * Created by ppg on 2016/7/20.
 */
public class SharePopwind {

    private Activity context;
    private PopupWindow sharePopupWindow;
    private View rootView;
    private Fragment fragment;
    private ShareModalDialog mModalDialog;
  //  PlatformActionListener Plistener;


//    public SharePopwind(Fragment fragment, View rootView, PlatformActionListener Plistener) {
//        this.Plistener = Plistener;
//        this.fragment = fragment;
//        this.context = fragment.getActivity();
//        this.rootView = rootView;
//    }

    public SharePopwind(Fragment fragment, View rootView) {
        this.fragment = fragment;
        this.context = fragment.getActivity();
        this.rootView = rootView;
    }

//    public void createShareModalDialog() {
//        if (mModalDialog == null) {
//            mModalDialog = new ShareModalDialog(context, null);
//            mModalDialog.setOnModalDialogClickListener(new ShareModalDialog.OnModalFormClick() {
//                @Override
//                public void onModalClick(View v) {
//                    Intent intent;
//                    switch (v.getId()) {
//                        case R.id.clickWeChatFriend:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            Wechat.ShareParams params = new Wechat.ShareParams();
//                            params.shareType = Platform.SHARE_WEBPAGE;
//                            params.setTitle("一个自由买茶、平等社交、有趣分享的平台");
//                            params.setTitleUrl("http://www.utchina.com/");
//                            params.setText("我正在使用UT有茶APP,UT平台上的茶漂亮又便宜，茶约活动好玩又有趣,还有很多你意想不到的惊喜呢！下载地址是http://www.utchina.com/，" +
//                                    "注册时填写平台开户赠送的包邮兑换卡" + SPUtils.get(context, "invite_code", "") + "，即可获得商城全年包邮特权。");
//                            params.setImageUrl("http://ut-bucket.img-cn-shenzhen.aliyuncs.com/assets/160819_7cbd1a2601da84fda10258423573b231.png");
//                            params.setUrl("http://www.utchina.com/download.html");
//                            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//                            if (Plistener != null) {
//                                wechat.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            }
//                            wechat.share(params);
//                            break;
//                        case R.id.clickWeChatCircles:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            WechatMoments.ShareParams params2 = new WechatMoments.ShareParams();
//                            params2.shareType = Platform.SHARE_WEBPAGE;
//                            params2.setTitle("一个自由买茶、平等社交、有趣分享的平台");
//                            params2.setText("我正在使用UT有茶APP,UT平台上的茶漂亮又便宜，茶约活动好玩又有趣,还有很多你意想不到的惊喜呢！下载地址是http://www.utchina.com/，" +
//                                    "注册时填写平台开户赠送的包邮兑换卡" + SPUtils.get(context, "invite_code", "") + "，即可获得商城全年包邮特权。");
//                            params2.setImageUrl("http://ut-bucket.img-cn-shenzhen.aliyuncs.com/assets/160819_7cbd1a2601da84fda10258423573b231.png");
//                            params2.setUrl("http://www.utchina.com/download.html");
//                            Platform wechatMonments = ShareSDK.getPlatform(WechatMoments.NAME);
//                            if (Plistener != null) {
//                                wechatMonments.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            }
//                            wechatMonments.share(params2);
//                            break;
//                        case R.id.clickQQFriend:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            QQ.ShareParams params3 = new QQ.ShareParams();
//                            params3.setTitle("一个自由买茶、平等社交、有趣分享的平台");
//                            params3.setTitleUrl("http://www.utchina.com/download.html");
//                            params3.setText("我正在使用UT有茶APP,UT平台上的茶漂亮又便宜，茶约活动好玩又有趣,还有很多你意想不到的惊喜呢！下载地址是http://www.utchina.com/download.html，" +
//                                    "注册时填写平台开户赠送的包邮兑换卡" + SPUtils.get(context, "invite_code", "") + "，即可获得商城全年包邮特权。");
//                            params3.setImageUrl("http://ut-bucket.img-cn-shenzhen.aliyuncs.com/assets/160819_7cbd1a2601da84fda10258423573b231.png");
//                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
//                            if (Plistener != null) {
//                                qq.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            }
//                            // 执行图文分享
//                            qq.share(params3);
//                            break;
//                        case R.id.clickWeiBo:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            SinaWeibo.ShareParams params4 = new SinaWeibo.ShareParams();
//                            params4.setText("我正在使用UT有茶APP,UT平台上的茶漂亮又便宜，茶约活动好玩又有趣,还有很多你意想不到的惊喜呢！下载地址是http://www.utchina.com/，" +
//                                    "注册时填写平台开户赠送的包邮兑换卡" + SPUtils.get(context, "invite_code", "") + "，即可获得商城全年包邮特权。");
//                            params4.setImageUrl("http://ut-bucket.img-cn-shenzhen.aliyuncs.com/assets/160819_7cbd1a2601da84fda10258423573b231.png");
//                            params4.setUrl("http://www.utchina.com/download.html");
//                            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//                            if (Plistener != null) {
//                                weibo.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            }
//                            weibo.share(params4);
//                            break;
//                        case R.id.clickSMS:
//                            sendSMS("我正在使用UT有茶APP，UT平台上的茶漂亮又便宜，茶约活动好玩又有趣,还有很多你意想不到的惊喜呢！下载地址是http://www.utchina.com/download.html，" +
//                                    "注册时填写平台开户赠送的包邮兑换卡" + SPUtils.get(context, "invite_code", "") + "，即可获得商城全年包邮特权。");
//                            break;
//                        case R.id.clickMail:
//                            intent = new Intent(Intent.ACTION_SEND);
//                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
//                            intent.putExtra(Intent.EXTRA_SUBJECT, "UT有茶");
//                            intent.putExtra(Intent.EXTRA_TEXT, "我正在使用UT有茶APP，UT平台上的茶漂亮又便宜，茶约活动好玩又有趣,还有很多你意想不到的惊喜呢！下载地址是http://www.utchina.com/download.html，" +
//                                    "注册时填写平台开户赠送的包邮兑换卡" + SPUtils.get(context, "invite_code", "") + "，即可获得商城全年包邮特权。");
//                            intent.setType("text/html");
//                            context.startActivity(intent);
//                            break;
//                    }
//                }
//            });
//        }
//        mModalDialog.show();
//    }

    //自定义分享
//    public void createShareModalDialog(final String title, final String str, final String url, final String imgUrl) {
//        if (mModalDialog == null) {
//            mModalDialog = new ShareModalDialog(context, null);
//            mModalDialog.setOnModalDialogClickListener(new ShareModalDialog.OnModalFormClick() {
//                @Override
//                public void onModalClick(View v) {
//                    Intent intent;
//                    switch (v.getId()) {
//                        case R.id.clickWeChatFriend:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            Wechat.ShareParams params = new Wechat.ShareParams();
//                            params.shareType = Platform.SHARE_WEBPAGE;
//                            params.setTitle(title);
//                            params.setTitleUrl(url);
//                            params.setText(str);
//                            params.setImageUrl(imgUrl);
//                            params.setUrl(url);
//                            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//                            wechat.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            wechat.share(params);
//                            break;
//                        case R.id.clickWeChatCircles:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            WechatMoments.ShareParams params2 = new WechatMoments.ShareParams();
//                            params2.shareType = Platform.SHARE_WEBPAGE;
//                            params2.setTitle(title);
//                            params2.setText(str);
//                            params2.setImageUrl(imgUrl);
//                            params2.setUrl(url);
//                            Platform wechatMonments = ShareSDK.getPlatform(WechatMoments.NAME);
//                            wechatMonments.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            wechatMonments.share(params2);
//                            break;
//                        case R.id.clickQQFriend:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            QQ.ShareParams params3 = new QQ.ShareParams();
//                            params3.setTitle(title);
//                            params3.setTitleUrl(url);
//                            params3.setText(str);
//                            params3.setImageUrl(imgUrl);
////                params.setSite("车知道");
////                params.setSiteUrl("http://www.gxczd.cn/");
//                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
//                            qq.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            // 执行图文分享
//                            qq.share(params3);
//                            break;
//                        case R.id.clickWeiBo:
//                            ToastUtil.toast2_bottom(context, "启动应用中");
//                            SinaWeibo.ShareParams params4 = new SinaWeibo.ShareParams();
//                            params4.setText(title + "," + str + "  " + url);
//                            params4.setImageUrl(imgUrl);
//                            params4.setUrl(url);
//                            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//                            weibo.setPlatformActionListener(Plistener); // 设置分享事件回调
//                            weibo.share(params4);
//                            break;
//                        case R.id.clickSMS:
//                            sendSMS(title + "," + str + "  " + url);
//                            break;
//                        case R.id.clickMail:
//                            intent = new Intent(Intent.ACTION_SEND);
//                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
//                            intent.putExtra(Intent.EXTRA_SUBJECT, "UT有茶");
//                            intent.putExtra(Intent.EXTRA_TEXT, title + "," + str + "  " + url);
//                            intent.setType("text/html");
//                            context.startActivity(intent);
//                            break;
//                    }
//                }
//            });
//        }
//        mModalDialog.findViewById(R.id.clickQQFriend).setVisibility(View.GONE);
//        mModalDialog.findViewById(R.id.clickWeiBo).setVisibility(View.GONE);
//        mModalDialog.findViewById(R.id.clickSMS).setVisibility(View.GONE);
//        mModalDialog.findViewById(R.id.clickMail).setVisibility(View.GONE);
//        mModalDialog.show();
//    }

    /**
     * 发送短信
     *
     * @param smsBody
     */
    private void sendSMS(String smsBody) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }


}
