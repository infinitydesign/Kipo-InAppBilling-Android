package ir.kipo.billing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ir.kipo.billing.activities.KipoBillingActivity;
import ir.kipo.billing.tools.KipoSPHelper;
import ir.kipo.billing.tools.KipoStringHelper;

public class KipoBillingHelper {

    public static final int CODE_SUCCESS = 1;
    public static final int CODE_FAILED = 2;
    public static final int CODE_CANCELED = 3;

    public static final String RESULT_CODE = "code";
    public static final String RESULT_TOKEN = "token";
    public static final String RESULT_MESSAGE = "message";
    public static final String RESULT_ERROR_CODE = "errorCode";

    public static final int REQUEST_CODE = 3005;

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
        Intent intent = new Intent(activity, KipoBillingActivity.class);
        intent.putExtra(KipoBillingActivity.KEY_AMOUNT, amount);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE || resultCode != Activity.RESULT_OK)
            return;

        if (data == null) {
            String m = String.format(activity.getString(R.string.errorCode), 99);
            m = defaultMessage + " " + m;
            listener.onBillingFailed(m);
            return;
        }

        int code = data.getIntExtra(RESULT_CODE, CODE_FAILED);
        String message = data.getStringExtra(RESULT_MESSAGE);
        String token = data.getStringExtra(RESULT_TOKEN);
        int errorCode = data.getIntExtra(RESULT_ERROR_CODE, 100);

        if (code == CODE_CANCELED) {
            listener.onBillingCanceled();
            return;
        }

        if (code == CODE_FAILED) {
            String m = KipoStringHelper.isEmpty(message) ? defaultMessage : message;
            if (errorCode != 0)
                m += " " + String.format(activity.getString(R.string.errorCode), errorCode);
            listener.onBillingFailed(m);
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

        void onBillingCanceled();

    }

}
