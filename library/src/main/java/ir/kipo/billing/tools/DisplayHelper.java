package ir.kipo.billing.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by 1HE on 10/22/2017.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
public class DisplayHelper {

    public static float dp;

    @SuppressWarnings("unused")
    public static float f(float f) {
        return f * dp;
    }

    public static float f(int i) {
        return i * dp;
    }

    public static int i(int i) {
        return (int) (i * dp);
    }

    public static int i(float f) {
        return (int) (f * dp);
    }

    public static void create(Context context) {
        dp = context.getResources().getDisplayMetrics().density;
    }

    public static int getActionBarHeight(Context c) {
        TypedValue tv = new TypedValue();
        if (c.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, c.getResources().getDisplayMetrics());
        }
        return 0;
    }

    public static int getStatusBarHeight(Context c) {
        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getNavigationBarHeight(Context c) {
        Resources resources = c.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static Point getDisplaySize(Context c) {
        try {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            assert wm != null;
            Display display = wm.getDefaultDisplay();
            Point p = new Point();
            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            p.x = realMetrics.widthPixels;
            p.y = realMetrics.heightPixels;

            return p;

        } catch (Exception e) {
            e.printStackTrace();
            return new Point();
        }
    }

}
