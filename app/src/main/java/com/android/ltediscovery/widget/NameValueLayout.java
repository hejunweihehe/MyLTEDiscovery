package com.android.ltediscovery.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author hjw
 * 
 */
public class NameValueLayout extends LinearLayout {
    private TextView mNameText;
    private TextView mValueText;

    public NameValueLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public NameValueLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NameValueLayout(Context context) {
        this(context, "");
    }

    public NameValueLayout(Context context, String name) {
        super(context);
        init(context);
        setNameText(name);
    }

    public void setNameText(String name) {
        mNameText.setText(name);
    }

    public void setValueText(String value) {
        mValueText.setText(value);
    }

    private void init(Context context) {
        mNameText = new TextView(context);
        mValueText = new TextView(context);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        addView(mNameText, layoutParams);
        addView(mValueText, layoutParams);
    }
}
