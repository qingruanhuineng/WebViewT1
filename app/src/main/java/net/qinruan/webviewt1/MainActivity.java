package net.qinruan.webviewt1;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    WebView mWebview;
    WebSettings mWebSettings;
    TextView beginLoading,endLoading,loading,mtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.webView1);
        beginLoading = findViewById(R.id.text_beginLoading);
        endLoading = findViewById(R.id.text_endLoading );
        loading = findViewById(R.id.text_loading);
        mtitle = findViewById(R.id.title);
        mWebSettings = mWebview.getSettings();
        mWebview.loadUrl("http://qinruan.net/");
        //设置不用系统默认浏览器打开，直接用WebView显示
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });
        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient(){
            //获取网址标题
            @Override
            public void onReceivedTitle(WebView view,String title){
                System.out.println("标题再这里");
                mtitle.setText(title);
            }
            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100){
                    String progress = newProgress + "%";
                    loading.setText(progress);
                }else if (newProgress == 100){
                    String progress = newProgress +"%";
                    loading.setText(progress);
                }
            }

        });
        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient(){
            //设置加载前函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
                beginLoading.setText("开始加载了");
            }
            //设置结束加载函数

            @Override
            public void onPageFinished(WebView view, String url) {
                endLoading.setText("结束加载了");
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()){
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onDestroy() {
        if (mWebview != null){
            mWebview.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            mWebview.clearHistory();
            ((ViewGroup)mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
}
