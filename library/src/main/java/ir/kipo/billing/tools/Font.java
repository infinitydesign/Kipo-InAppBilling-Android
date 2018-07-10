package ir.kipo.billing.tools;

import android.content.Context;
import android.graphics.Typeface;

import ir.kipo.billing.R;


/**
 * Created by 1HE on 06/28/2016.
 */
@SuppressWarnings("ALL")
public class Font {

    private static Typeface mainRegular;
    private static Typeface mainBold;
    private static Typeface mainBlack;
    private static Typeface mainLight;
    private static Typeface mainMedium;
    private static Typeface mainUltraLight;

    public static Typeface mainBold(Context c) {
        if (mainBold == null) {
            mainBold = Typeface.createFromAsset(c.getAssets(), c.getString(R.string.font_mainBold));
        }
        return mainBold;
    }

    public static Typeface mainBlack(Context c) {
        if (mainBlack == null) {
            mainBlack = Typeface.createFromAsset(c.getAssets(), c.getString(R.string.font_mainBlack));
        }
        return mainBold;
    }

    public static Typeface mainLight(Context c) {
        if (mainLight == null) {
            mainLight = Typeface.createFromAsset(c.getAssets(), c.getString(R.string.font_mainLight));
        }
        return mainLight;
    }

    public static Typeface mainMedium(Context c) {
        if (mainMedium == null) {
            mainMedium = Typeface.createFromAsset(c.getAssets(), c.getString(R.string.font_mainMedium));
        }
        return mainMedium;
    }

    public static Typeface mainRegular(Context c) {
        if (mainRegular == null) {
            mainRegular = Typeface.createFromAsset(c.getAssets(), c.getString(R.string.font_mainRegular));
        }
        return mainRegular;
    }

    public static Typeface mainUltraLight(Context c) {
        if (mainUltraLight == null) {
            mainUltraLight = Typeface.createFromAsset(c.getAssets(), c.getString(R.string.font_mainUltraLight));
        }
        return mainUltraLight;
    }

    public static Typeface getFont(Context c, String name) {

        if (name.equals(c.getString(R.string.font_mainBold))) {
            return mainBold(c);
        } else if (name.equals(c.getString(R.string.font_mainBlack))) {
            return mainBlack(c);
        } else if (name.equals(c.getString(R.string.font_mainLight))) {
            return mainLight(c);
        } else if (name.equals(c.getString(R.string.font_mainMedium))) {
            return mainMedium(c);
        } else if (name.equals(c.getString(R.string.font_mainRegular))) {
            return mainRegular(c);
        } else if (name.equals(c.getString(R.string.font_mainUltraLight))) {
            return mainUltraLight(c);//todo may be no need
        } else {
            return mainRegular(c);
        }
    }

    public static int getPaddingExtra(Context context, String font) {
        int[] resPadding = new int[]{R.string.font_mainBold, R.string.font_mainBlack, R.string.font_mainMedium,
                R.string.font_mainRegular, R.string.font_mainLight, R.string.font_mainUltraLight};

        for (int r : resPadding) {
            if (context.getString(r).endsWith(font)) {
                return context.getResources().getDimensionPixelSize(R.dimen.fontPaddingBottom);
            }
        }
        return 0;
    }
}
