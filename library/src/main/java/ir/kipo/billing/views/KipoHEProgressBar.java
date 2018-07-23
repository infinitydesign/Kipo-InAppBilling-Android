package ir.kipo.billing.views;

import android.content.Context;
import android.util.AttributeSet;

import ir.kipo.billing.libraries.materialprogressbar.MaterialProgressBar;


/**
 * Created by 1HE on 10/22/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class KipoHEProgressBar extends MaterialProgressBar {

    public KipoHEProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeFromXml(context, attrs);
        initializeViews();
    }

    @SuppressWarnings({"UnusedParameters", "EmptyMethod"})
    private void setAttributeFromXml(Context context, AttributeSet attrs) {

    }

    @SuppressWarnings("EmptyMethod")
    private void initializeViews() {

    }

    public void show() {
        showOrHide(true);
    }

    public void hide() {
        showOrHide(false);
    }

    private void showOrHide(final boolean show) {
        if (show) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }
}