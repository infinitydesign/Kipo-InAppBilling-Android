package ir.kipo.billing.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import ir.kipo.billing.R;
import ir.kipo.billing.tools.DisplayHelper;
import ir.kipo.billing.tools.DrawableHelper;

/**
 * Created by 1HE on 10/22/2017.
 */

@SuppressWarnings({"unused", "DefaultFileTemplate"})
public class HEImageView extends AppCompatImageView {

    private int resVector;
    private int color;
    private int size;

    public HEImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeFromXml(context, attrs);
        initializeViews();
    }

    public HEImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeFromXml(context, attrs);
        initializeViews();
    }

    private void setAttributeFromXml(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HEImageView, 0, 0);

        try {
            resVector = a.getResourceId(R.styleable.HEImageView_he_imageview_vector, 0);
            color = a.getColor(R.styleable.HEImageView_he_imageview_color, 0);
            size = a.getDimensionPixelSize(R.styleable.HEImageView_he_imageview_size, DisplayHelper.i(24));
        } finally {
            a.recycle();
        }
    }

    private void initializeViews() {
        if (resVector == 0)
            return;

        setImageDrawable(DrawableHelper.changeColorDrawableVector(getContext(), resVector, color));
    }

    public void setVector(Item item) {
        if (item == null) {
            setImageResource(android.R.color.transparent);
            return;
        }

        this.resVector = item.getResVector();
        this.color = item.getColor();

        setImageDrawable(DrawableHelper.changeColorDrawableVector(getContext(), resVector, color));
    }

    public void setColor(int color) {
        this.color = color;
        setImageDrawable(DrawableHelper.changeColorDrawableVector(getContext(), resVector, color));
    }

    public void setResVector(int resVector) {
        this.resVector = resVector;
        setImageDrawable(DrawableHelper.changeColorDrawableVector(getContext(), resVector, color));
    }

    public void setSize(int size) {
        this.size = size;
        requestLayout();
    }

    public int getSize() {
        return size;
    }

    @SuppressWarnings("unused")
    public void setResVectorWithAnim(int resVector, int resAnim) {
        if (this.resVector == resVector)
            return;
        this.resVector = resVector;
        if (Build.VERSION.SDK_INT >= 21) {
            AnimatedVectorDrawable a = (AnimatedVectorDrawable) getContext().getDrawable(resAnim);
            setImageDrawable(a);
            if (a != null)
                a.start();
        } else {
            setImageDrawable(DrawableHelper.changeColorDrawableVector(getContext(), resVector, color));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newSize = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(newSize, newSize);
    }

    @SuppressWarnings("unused")
    public static class Item {

        private int color;
        private int resVector;

        public Item() {

        }

        public Item(int color, int resVector) {
            setColor(color);
            setResVector(resVector);
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        @SuppressWarnings("WeakerAccess")
        public int getResVector() {
            return resVector;
        }

        @SuppressWarnings("WeakerAccess")
        public void setResVector(int resVector) {
            this.resVector = resVector;
        }
    }
}
