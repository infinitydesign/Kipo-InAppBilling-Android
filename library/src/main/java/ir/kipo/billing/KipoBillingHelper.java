package ir.kipo.billing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import ir.kipo.billing.tools.KipoSPHelper;
import ir.kipo.billing.tools.KipoStringHelper;

public class KipoBillingHelper {

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_FAILED = 2;

    private Activity activity;
    private IKipoBillingHelperListener listener;

    private String defaultMessage;

    public KipoBillingHelper(Activity activity, IKipoBillingHelperListener listener) {
        this.activity = activity;
        this.listener = listener;
        if (activity == null) {
            throw new NullPointerException("activity must be not null!");
        }
        if (listener == null) {
            throw new NullPointerException("listener must be not null!");
        }
        defaultMessage = activity.getString(R.string.error_unknown);
    }

    public void purchase(long amount) {
        if (amount <= 0) {
            Toast.makeText(activity, R.string.error_needAmount, Toast.LENGTH_LONG).show();
            return;
        }

        long invoiceId = KipoSPHelper.getLong(activity, KipoSPHelper.SETTING, KipoSPHelper.KEY_LAST_INVOICE_ID, KipoSPHelper.DEFAULT_INVOICE_ID);
        invoiceId++;
        String merchantSchema = KipoSPHelper.getString(activity, KipoSPHelper.SETTING, KipoSPHelper.KEY_MERCHANT_SCHEMA, "");
        String merchantId = KipoSPHelper.getString(activity, KipoSPHelper.SETTING, KipoSPHelper.KEY_MERCHANT_ID, "");

        String url = "http://iap.kipopay.com/?bi=" + merchantSchema + "&in=" + invoiceId + "&a=" + amount + "&mp=" + merchantId + "&os=android";

        try {
//            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
//
//            intentBuilder.setToolbarColor(ContextCompat.getColor(activity, R.color.toolbarBackground));
//            intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(activity, R.color.toolbarBackground));
//
//            CustomTabsIntent customTabsIntent = intentBuilder.build();
//            customTabsIntent.launchUrl(activity, Uri.parse(url));

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(i);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void onNewIntent(Intent data) {
        if (data == null)
            return;

        Uri uri = data.getData();
        if (uri == null || uri.getScheme() == null) {
            callListener(KipoBillingHelper.CODE_FAILED, "", 101);
            return;
        }

        String merchantSchema = KipoSPHelper.getString(activity, KipoSPHelper.SETTING, KipoSPHelper.KEY_MERCHANT_SCHEMA, "");

        try {
            if (uri.getScheme().startsWith(merchantSchema)) {
                if (uri.getHost().equals("app")) {
                    String path = uri.getPath();
                    String[] array = path.split("/");
                    if (array.length >= 3) {
                        if (array[1].equals("token")) {
                            String tokenValue = array[2];
                            if (KipoStringHelper.isEmpty(tokenValue))
                                callListener(KipoBillingHelper.CODE_FAILED, "", 102);
                            else
                                callListener(KipoBillingHelper.CODE_SUCCESS, tokenValue, 103);

                        } else if (array[1].equals("error")) {
                            String message = array[2];
                            message = message.replaceAll("[+]", " ");
                            callListener(KipoBillingHelper.CODE_FAILED, message, 0);

                        } else {
                            callListener(KipoBillingHelper.CODE_FAILED, "", 105);
                        }
                    } else {
                        callListener(KipoBillingHelper.CODE_FAILED, "", 106);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            callListener(KipoBillingHelper.CODE_FAILED, "", 107);
        }
    }

    private void callListener(int code, String m, int errorCode) {
        String message = "";
        String token = "";
        if (code == KipoBillingHelper.CODE_SUCCESS)
            token = m;
        else
            message = m;

        if (code == CODE_FAILED) {
            String ms = KipoStringHelper.isEmpty(message) ? defaultMessage : message;
            if (errorCode != 0)
                ms += " " + String.format(activity.getString(R.string.errorCode), errorCode);
            listener.onBillingFailed(ms);
            return;
        }

        if (code == CODE_SUCCESS) {
            listener.onBillingReadyForCheckingToken(token);
        }
    }

    public static void init(Context context, String merchantId) {
        if (!KipoStringHelper.isValidMobile(merchantId)) {
            throw new IllegalArgumentException("merchant id is invalid. (example : 09123456789)");
        }
        KipoSPHelper.setString(context, KipoSPHelper.SETTING, KipoSPHelper.KEY_MERCHANT_ID, merchantId);
        KipoSPHelper.setString(context, KipoSPHelper.SETTING, KipoSPHelper.KEY_MERCHANT_SCHEMA, context.getPackageName());
    }

    public interface IKipoBillingHelperListener {

        void onBillingReadyForCheckingToken(String token);

        void onBillingFailed(String message);

    }

}
