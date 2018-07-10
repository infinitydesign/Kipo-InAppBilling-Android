package ir.kipo.billing.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * Created by 1HE on 10/22/2017.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
public class DrawableHelper {

    public static Drawable changeColorDrawableVector(Context c, int resDrawable, int color) {
        if (c == null)
            return null;

        Drawable d = VectorDrawableCompat.create(c.getResources(), resDrawable, null);
        if (d == null)
            return null;

        d.mutate();
        if (color != -2)
            d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return d;
    }

    public static Drawable changeColorDrawable(Context c, int resDrawable, int color) {
        if (c == null)
            return null;
        Drawable d = ContextCompat.getDrawable(c, resDrawable);
        if (d == null)
            return null;

        d.mutate();
        if (color != -2)
            d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return d;
    }

    public static void changeBackgroundColor(View view, int color) {
        if (view == null)
            return;

        Drawable d = view.getBackground();
        if (d == null)
            return;

        d.mutate();
        d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
