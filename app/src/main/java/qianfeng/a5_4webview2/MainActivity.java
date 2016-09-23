package qianfeng.a5_4webview2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private String html = "<p>【性质】平</p> <p>【五味】辛</p> <p>【热量】900.00大卡(千焦)/100克 </p> <p>【功效】抑癌抗瘤</p> <p>【棕榈油是什么】 棕榈油是从油棕树上的棕果(Elaeis Guineensis)中榨取出来的，果肉压榨出的油称为棕榈油( Palm Oil)，而果仁压榨出的油称为棕榈仁油(Palm Kernel Oil)，两种油的成分大不相同。棕榈油主要含有棕榈酸(C 16)和油酸(C 18)两种最普通的脂肪酸，棕榈油的饱和程度约为50%;棕榈仁油主要含有月桂酸(C 12)，饱和程度达80...</p>";
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.tv);
        // 解析不完整的html段落，而不是一个.html格式的文件
        // 1.如果是纯文本的html，里面没有图片，那就可以用TextView来显示
        // 2.如果那段html里面含有图片的话，那就用WebView来解决，textView里面解析html格式的文件含有图片是比较麻烦的。
        tv.setText(Html.fromHtml(html));// 用android包下的Html类的fromHtml()方法来解析html格式的段落

        webView = ((WebView) findViewById(R.id.webView));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        // 运用webView的loadData()方法来加载一个html段落，因为网页里编码格式一般都写在content="text/html;charset=utf-8"  因为它们都写在一起，所以这里要合在一起写，才可以解析出正确格式的数据，否则，数据的格式就不正确了。
        webView.loadData(getContent(),"text/html;charset=utf-8",null);
    }


    public String getContent() {

        StringBuffer buffer = new StringBuffer();
        String str = "";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("html.txt")));
            while((str=br.readLine())!=null)
            {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }else
        {
            super.onBackPressed();
        }
    }
}
