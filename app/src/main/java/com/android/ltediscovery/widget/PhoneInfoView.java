package com.android.ltediscovery.widget;

import java.lang.reflect.Field;

import com.example.ltediscovery.R;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhoneInfoView extends LinearLayout {
	TextView txt_data_state;
	TextView txt_network_type;
	TextView txt_signal;
	TextView txt_cid;
	TextView txt_lac;

	Context context;

	public PhoneInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PhoneInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		View view = inflate(context, R.layout.item_phone_info, this);
		txt_data_state = (TextView) view.findViewById(R.id.txt_data_state);
		txt_network_type = (TextView) view.findViewById(R.id.txt_network_type);
		txt_signal = (TextView) view.findViewById(R.id.txt_signal);
		txt_cid = (TextView) view.findViewById(R.id.txt_cid);
		txt_lac = (TextView) view.findViewById(R.id.txt_lac);
		this.context = context;
	}

	public void setCell(int cid, int lac) {
		txt_cid.setText("" + cid);
		txt_lac.setText("" + lac);
	}

	public void setSignal(int dbm, int asu) {
		txt_signal.setText(dbm + "dBm , " + asu + "asu");
	}

	public void setNetworkType(int type, TelephonyManager tm) {
		Field[] fields = TelephonyManager.class.getFields();
		for (Field f : fields) {
			if (f.getName().startsWith("NETWORK_TYPE_")) {
				try {
					int val = (Integer) f.get(tm);
					if (val == type) {
						txt_network_type.setText(f.getName().substring(13));
						break;
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setDataState(int state) {
		switch (state) {
		case TelephonyManager.DATA_DISCONNECTED:
			txt_data_state.setText(context
					.getString(R.string.data_disconnected));
			break;
		case TelephonyManager.DATA_CONNECTING:
			txt_data_state.setText(context.getString(R.string.data_connecting));
			break;
		case TelephonyManager.DATA_CONNECTED:
			txt_data_state.setText(context.getString(R.string.data_connected));
			break;
		case TelephonyManager.DATA_SUSPENDED:
			txt_data_state.setText(context.getString(R.string.data_suspended));
			break;
		default:
			txt_data_state.setText(context.getString(R.string.data_unknown));
			break;
		}
	}

	public PhoneInfoView(Context context) {
		super(context);
		init(context);
	}

	// // 更新设备信息
	// private void updatePhone() {
	// txt_carrier.setText("Carrier: ");
	// txt_roaming.setText("Roaming: ");
	// txt_type.setText("Type: ");
	// txt_device.setText("Device: ");
	// String carrier = tm.getNetworkOperatorName() + "(" +
	// tm.getSimOperator() + ")";
	// txt_carrier.append(carrier);
	//
	// boolean tag = tm.isNetworkRoaming();
	// if (tag) {
	// txt_roaming.append("Is roaming");
	// } else {
	// txt_roaming.append("Not roaming");
	// }
	//
	// int type = tm.getPhoneType();
	// switch (type) {
	// case TelephonyManager.PHONE_TYPE_NONE:
	// txt_type.append("Unknown");
	// break;
	// case TelephonyManager.PHONE_TYPE_GSM:
	// txt_type.append("GSM");
	// break;
	// case TelephonyManager.PHONE_TYPE_CDMA:
	// txt_type.append("CDMA");
	// break;
	// case TelephonyManager.PHONE_TYPE_SIP:
	// txt_type.append("SIP");
	// break;
	// }

	// }
}
