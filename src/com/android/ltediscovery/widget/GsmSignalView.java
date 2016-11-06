package com.android.ltediscovery.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.ltediscovery.R;

public class GsmSignalView extends LinearLayout {
    private Switch btn_visibility;
    private LinearLayout ll_info;

    public GsmSignalView(Context context) {
        super(context);
        init(context);
    }

    public GsmSignalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GsmSignalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("InflateParams")
    private void init(Context context) {
        inflate(context, R.layout.item_gsm_signal, this);
        btn_visibility = (Switch) findViewById(R.id.btn_visibility);
        ll_info = (LinearLayout) findViewById(R.id.ll_info);
        btn_visibility
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                            boolean isChecked) {
                        if (isChecked) {
                            ll_info.setVisibility(View.VISIBLE);
                        } else {
                            ll_info.setVisibility(View.GONE);
                        }
                    }
                });
        btn_visibility.setChecked(true);
    }
}
