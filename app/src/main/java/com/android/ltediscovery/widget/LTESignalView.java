package com.android.ltediscovery.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ltediscovery.R;

public class LTESignalView extends LinearLayout {
	private LinearLayout ll_info;
	TextView txt_network_operator_name;
	TextView txt_country_iso;
	TextView txt_mnc;
	TextView txt_device;
	TextView txt_phone_type;
	TextView txt_is_network_roaming;

	public void setIsNetworkRoaming(boolean isNetworkRoaming) {
		txt_is_network_roaming.setText(isNetworkRoaming == true ? "是" : "否");
	}

	public void setPhoneType(String phoneType) {
		txt_phone_type.setText(phoneType);
	}

	public void setDevice(String device) {
		txt_device.setText(device);
	}

	public void setNetworkOperatorName(String operator) {
		txt_network_operator_name.setText(operator);
	}

	public void setSimCountryIso(String simCountryIso) {
		txt_country_iso.setText(simCountryIso);
	}

	public void setSimOperator(String simOperator) {
		txt_mnc.setText(simOperator);
	}

	public LTESignalView(Context context) {
		super(context);
		init(context);
	}

	public LTESignalView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public LTESignalView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@SuppressLint("InflateParams")
	private void init(Context context) {
		inflate(context, R.layout.item_lte_signal, this);
		ll_info = (LinearLayout) findViewById(R.id.ll_info);
		txt_network_operator_name = (TextView) findViewById(R.id.txt_network_operator_name);
		txt_country_iso = (TextView) findViewById(R.id.txt_country_iso);
		txt_mnc = (TextView) findViewById(R.id.txt_mnc);
		txt_device = (TextView) findViewById(R.id.txt_device);
		txt_phone_type = (TextView) findViewById(R.id.txt_phone_type);
		txt_is_network_roaming = (TextView) findViewById(R.id.txt_is_network_roaming);
	}
}
