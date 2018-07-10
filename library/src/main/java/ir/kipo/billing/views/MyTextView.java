package ir.kipo.billing.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import ir.kipo.billing.R;
import ir.kipo.billing.tools.Font;
import ir.kipo.billing.tools.StringHelper;

/**
 * Created by 1HE on 10/22/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MyTextView extends AppCompatTextView {

    private String fontName = "";
    private boolean haveStar = false;
    private boolean isPersian;
    private boolean haveDouble;
    private boolean haveSpan;
    private boolean forcePaddingFont;

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeFromXml(context, attrs);
        initializeViews();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeFromXml(context, attrs);
        initializeViews();
    }

    private void setAttributeFromXml(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextView, 0, 0);

        try {
            fontName = a.getString(R.styleable.MyTextView_fontName);
            haveStar = a.getBoolean(R.styleable.MyTextView_haveStar, false);
            haveDouble = a.getBoolean(R.styleable.MyTextView_haveDouble, false);
            isPersian = a.getBoolean(R.styleable.MyTextView_isPersian, true);
            haveSpan = a.getBoolean(R.styleable.MyTextView_haveSpan, false);
            forcePaddingFont = a.getBoolean(R.styleable.MyTextView_forcePaddingFont, false);
        } finally {
            a.recycle();
        }
    }

    private void initializeViews() {
        if (!StringHelper.isEmpty(fontName))
            setTypeface(Font.getFont(getContext(), fontName));
        String result = getText().toString();
        if (haveStar)
            result = StringHelper.getStarredText(result);
        if (isPersian)
            result = StringHelper.toPersianNumber(result);
        setText(result);

        setIncludeFontPadding(forcePaddingFont);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (haveSpan) {
            super.setText(text, type);
            return;
        }

        if (text == null) {
            super.setText("", type);
            return;
        }

        String result = text.toString();
        if (haveStar)
            result = StringHelper.getStarredText(result);
        if (haveDouble)
            result = StringHelper.getDoubledText(result);
        if (isPersian)
            result = StringHelper.toPersianNumber(result);
        super.setText(result, type);
    }

}
