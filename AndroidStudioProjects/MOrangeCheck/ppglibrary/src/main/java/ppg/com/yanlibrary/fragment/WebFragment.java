package ppg.com.yanlibrary.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ppg.com.yanlibrary.R;
import ppg.com.yanlibrary.activity.BaseTopBarActivity;


/**
 * 
 * @author jie.yang
 *
 */
public class WebFragment extends Fragment implements BaseTopBarActivity.KeyDownListener {

	public final static String TAG = WebFragment.class.getSimpleName();
	public final static String URL_ID = "url_id";
	public final static String HTML_DATA = "html_data";
	public final static String SELF_ADAPTION = "self-adaption";
	private WebView webView;
	private String data;
	private boolean isSelfAdaption = true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_webview, null);
		webView = (WebView) root.findViewById(R.id.fragment_webview_webview);
		
		data = getActivity().getIntent().getStringExtra(HTML_DATA);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");  
		webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                        view.loadUrl(url);
                        return false;
                        
                }
        });
		//支持javascript
		webView.getSettings().setJavaScriptEnabled(true);
		// 自适应
//		isSelfAdaption = getActivity().getIntent().getBooleanExtra(SELF_ADAPTION, true);
//		if(isSelfAdaption) {
			// 设置可以支持缩放
			webView.getSettings().setSupportZoom(true);
			// 设置出现缩放工具
			webView.getSettings().setBuiltInZoomControls(true);
			//扩大比例的缩放
			webView.getSettings().setUseWideViewPort(true);
//			//自适应屏幕
			webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
			webView.getSettings().setLoadWithOverviewMode(true);
//		}
		
		if(TextUtils.isEmpty(data)) {
			webView.loadUrl(getActivity().getIntent().getStringExtra(URL_ID));
		}else {
			webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
		}
		return root;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(webView.canGoBack()) {
				webView.goBack(); // goBack()表示返回WebView的上一页面
				return true;
			}
		}
		return false;
	}
}
