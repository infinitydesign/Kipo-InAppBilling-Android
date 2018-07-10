package ir.kipo.billing.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URI;

import ir.kipo.billing.KipoBillingHelper;
import ir.kipo.billing.R;
import ir.kipo.billing.tools.SPHelper;
import ir.kipo.billing.tools.StringHelper;
import ir.kipo.billing.views.HEProgressBar;
import ir.kipo.billing.views.MyTextView;

public class BillingActivity extends AppCompatActivity {

    public static final String KEY_AMOUNT = "amount";
    private long amount;

    private MyTextView tv_url;
    private WebView wv;
    private HEProgressBar pb;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(KEY_AMOUNT, amount);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle;
        if (savedInstanceState == null)
            bundle = getIntent().getExtras();
        else
            bundle = savedInstanceState;
        if (bundle != null) {
            amount = bundle.getLong(KEY_AMOUNT);
        } else {
            finish();
        }
        //todo error check for amount > 0;
        //todo error check for mobile is correct;

        setContentView(R.layout.activity_billing);

        tv_url = findViewById(R.id.MyTextView_billing_url);
        wv = findViewById(R.id.WebView_billing);
        pb = findViewById(R.id.HEProgressBar_billing);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                URI uri = null;
                try {
                    uri = new URI(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (uri == null || uri.getScheme() == null) {
                    finishActivity(KipoBillingHelper.CODE_FAILED, "", 101);
                    return false;
                }

                if (uri.getScheme().startsWith(SPHelper.getString(BillingActivity.this, SPHelper.SETTING, SPHelper.KEY_MERCHANT_SCHEMA, ""))) {
                    if (uri.getHost().equals("app")) {
                        String path = uri.getPath();
                        String[] array = path.split("/");
                        if (array.length >= 2) {
                            if (array[1].equals("token")) {
                                String tokenValue = array[2];
                                if (StringHelper.isEmpty(tokenValue))
                                    finishActivity(KipoBillingHelper.CODE_FAILED, "", 102);
                                else
                                    finishActivity(KipoBillingHelper.CODE_SUCCESS, tokenValue, 103);

                            } else if (array[1].equals("error")) {
                                String message = array[1];
                                finishActivity(KipoBillingHelper.CODE_FAILED, message, 0);
                            } else {
                                finishActivity(KipoBillingHelper.CODE_FAILED, "", 105);
                            }
                        } else {
                            finishActivity(KipoBillingHelper.CODE_FAILED, "", 106);
                        }
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (pb != null)
                    pb.show();
                setUrl(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (pb != null)
                    pb.hide();
                setUrl(url);
            }
        });
        wv.loadUrl("http://he.idco.io/my_files/main.html");
    }

    private void setUrl(String url) {
        if (tv_url == null)
            return;

        if (StringHelper.isEmpty(url)) {
            tv_url.setText("");
            return;
        }

        if (url.contains("http://")) {
            url = url.replaceAll("http://", "");
        }

        if (url.contains("https://")) {
            url = url.replaceAll("https://", "");
        }

        tv_url.setText(url);
    }

    private void finishActivity(int code, String m, int errorCode) {
        Intent intent = new Intent();
        intent.putExtra(KipoBillingHelper.RESULT_CODE, code);
        if (code == KipoBillingHelper.CODE_SUCCESS)
            intent.putExtra(KipoBillingHelper.RESULT_TOKEN, m);
        else
            intent.putExtra(KipoBillingHelper.RESULT_MESSAGE, m);
        intent.putExtra(KipoBillingHelper.RESULT_ERROR_CODE, errorCode);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishActivity(KipoBillingHelper.CODE_CANCELED, "", 0);
    }
}
