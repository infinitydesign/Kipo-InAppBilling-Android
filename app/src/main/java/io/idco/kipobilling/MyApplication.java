package io.idco.kipobilling;

import android.app.Application;

import ir.kipo.billing.KipoBillingHelper;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KipoBillingHelper.init(this, "09082001061");
    }
}
