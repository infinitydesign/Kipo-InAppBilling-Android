package io.idco.kipobilling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ir.kipo.billing.KipoBillingHelper;

public class MainActivity extends AppCompatActivity {

    private KipoBillingHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new KipoBillingHelper(this, new KipoBillingHelper.IKipoBillingHelperListener() {
            @Override
            public void onBillingReadyForCheckingToken(String token) {
                Toast.makeText(MainActivity.this, "توکن خرید ثبت شده و باید با سرور چک شود", Toast.LENGTH_LONG).show();
                //send token to your server to verify
            }

            @Override
            public void onBillingFailed(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.Button_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send user to purchase page (1000 rial)
                helper.purchase(1000);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        helper.onNewIntent(intent);
    }
}
