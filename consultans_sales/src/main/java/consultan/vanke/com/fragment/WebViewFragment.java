package consultan.vanke.com.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import consultan.vanke.com.R;
import consultan.vanke.com.constant.ConstantLibrary;
import me.jingbin.progress.WebProgress;

public class WebViewFragment extends BaseFragment {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress)
    WebProgress progressBar;
    private View view;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        initWebSettings();
        initWebClient();
        webView.loadUrl(ConstantLibrary.WEBVIEWURL);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStatusBarColors(getResources().getColor(R.color.color_007AFF),false);
        view = inflater.inflate(R.layout.web_fragment, null);
        return view;
    }

    private void initWebClient() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.show(); // 显示
                progressBar.setWebProgress(50);              // 设置进度
                progressBar.setColor(getString(R.string.char_color9eea5e));             // 设置颜色
//                progressBar.setColor("#00D81B60", "#D81B60"); // 设置渐变色
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.hide();
                super.onPageFinished(view, url);
            }

            // 链接跳转都会走这个方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 强制在当前 WebView 中加载 url
                return true;
            }
        });
    }


    private void initWebSettings() {
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

}
