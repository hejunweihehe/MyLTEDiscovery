package com.android.ltediscovery.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextArea extends LinearLayout {
    private String title;
    private String content;
    private TextView mTitleText;
    private TextView mContentText;
    private Context context;

    public TextArea(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TextArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextArea(Context context, String title) {
        this(context, title, "");
    }

    public TextArea(Context context, String title, String content) {
        super(context);
        init(context);
        setTitle(title);
        setContent(content);
    }

    public void setTitle(String title) {
        this.title = title;
        mTitleText.setText(title);
    }

    public void setContent(String content) {
        this.content = content;
        mContentText.setText(content);
    }

    public TextView getTileTextView() {
        return this.mTitleText;
    }

    public TextView getContentTextView() {
        return this.mContentText;
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        this.mContentText = new TextView(context);
        this.mTitleText = new TextView(context);
        mTitleText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        addView(mTitleText, lp);
        addView(mContentText, lp);
    }
}
