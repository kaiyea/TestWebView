package com.zuliang.testwebview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MiniBrowserActivity extends AppCompatActivity {

    private WebView mWebView;
    private EditText mURL;
    private RelativeLayout main_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_browser);

        mWebView = (WebView) findViewById(R.id.browser);
        mURL = (EditText) findViewById(R.id.url_text);
        main_content = (RelativeLayout) findViewById(R.id.main_content);

       /* WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });*/

        //StringBuilder sb = new StringBuilder();
        /*sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Hello World</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h2>欢迎你访问Android平台</h2>");
        sb.append("</body>");
        sb.append("</html>");*/
        //mWebView.loadData(sb.toString(),"text/html","utf-8");

        /*sb.append("<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                " <head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>欢迎你的访问</title>\n" +
                "\n" +
                "  <style>\n" +
                "\ttd{\n" +
                "\t\tcolor:red;\n" +
                "\t}\n" +
                "  </style>\n" +
                " </head>\n" +
                " <body>\n" +
                "\t<table>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>第一行第一列</td>\n" +
                "\t\t\t<td>第一行第二列</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>第二行第一列</td>\n" +
                "\t\t\t<td>第二行第二列</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>第三行第一列</td>\n" +
                "\t\t\t<td>第三行第二列</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>\n" +
                " </body>\n" +
                "</html>");

        mWebView.loadDataWithBaseURL(null,sb.toString(),"text/html","utf-8",null);*/

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new MyObject(),"myObj");
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    public  void doSearch(View view){
        String url = mURL.getText().toString();
        mWebView.loadUrl("file:///android_asset/html.html");
    }

    class MyObject{
        public MyObject(){

        }
        @JavascriptInterface
        public void showToast(String msg){
            Toast.makeText(MiniBrowserActivity.this,msg,Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void newImageView(final int width, final int height, final String url){
            Toast.makeText(MiniBrowserActivity.this,url,Toast.LENGTH_SHORT).show();

            MiniBrowserActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams pm = new RelativeLayout.LayoutParams(width,height);
                    ImageView img = new ImageView(MiniBrowserActivity.this);
                    //img.setImageResource(R.mipmap.ic_launcher);
                    Picasso.with(MiniBrowserActivity.this).load(url).into(img);
                    pm.leftMargin = 200;
                    pm.topMargin =200;
                    img.setLayoutParams(pm);
                    main_content.addView(img);
                }
            });
        }
    }
}
