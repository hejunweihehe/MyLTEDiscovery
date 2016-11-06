package com.android.ltediscovery;

import java.lang.reflect.Field;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.ltediscovery.utils.ReflectUtils;
import com.android.ltediscovery.widget.LTESignalView;
import com.android.ltediscovery.widget.PhoneInfoView;
import com.example.ltediscovery.R;

public class SignalFragment extends Fragment {
	private View view;
	private TextView txt_lte_param;
	private String netType = "N/A";
	private TextView txt_data_param;
	private TextView txt_lte_dbm;// lte dbm
	private TextView txt_data_dbm;// 2G,3G dbm
	private TextView txt_data_name;// data name
	private TextView txt_neighbor_title;
	private PhoneInfoView network_info;
	private LTESignalView phone_info;

	private TelephonyManager tm;
	private SignalStrengthListener listener = new SignalStrengthListener();
	int mLteStrength = 0;
	private IntentFilter intentFilter = new IntentFilter();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_signals, container, false);
		initView();
		initNet();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		getActivity().registerReceiver(connectionReceiver, intentFilter);
		tm.listen(listener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
				| PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (connectionReceiver != null) {
			getActivity().unregisterReceiver(connectionReceiver);
		}
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
	}

	private void initNet() {
		tm = (TelephonyManager) getActivity().getSystemService(
				Context.TELEPHONY_SERVICE);
	}

	private void initView() {
		txt_lte_param = (TextView) view.findViewById(R.id.txt_lte_param);
		txt_data_param = (TextView) view.findViewById(R.id.txt_data_param);
		txt_lte_dbm = (TextView) view.findViewById(R.id.txt_lte_dbm);
		txt_data_dbm = (TextView) view.findViewById(R.id.txt_data_dbm);
		txt_data_name = (TextView) view.findViewById(R.id.txt_data_name);
		txt_neighbor_title = (TextView) view
				.findViewById(R.id.txt_neighbor_title);
		network_info = (PhoneInfoView) view.findViewById(R.id.network_info);
		phone_info = (LTESignalView) view.findViewById(R.id.phone_info);
	}

	BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {

		}
	};

	/**
     * 
     */
	private class SignalStrengthListener extends PhoneStateListener {

		@SuppressLint("NewApi")
		@Override
		public void onSignalStrengthsChanged(
				android.telephony.SignalStrength signalStrength) {
			updateSignalStrengths(signalStrength);
			updateDataParam();
			updatePhoneInfo();
			network_info.setSignal((Integer) ReflectUtils.invoke(
					signalStrength, "getDbm"), (Integer) ReflectUtils.invoke(
					signalStrength, "getAsuLevel"));
		}

		@Override
		public void onDataConnectionStateChanged(int state) {
			updateDataConnectionChange();
			updateDataParam();
			updatePhoneInfo();
		}
	}

	private void updatePhoneInfo() {
		network_info.setDataState(tm.getDataState());
		network_info.setNetworkType(tm.getNetworkType(), tm);
		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();

		int cellid = location.getCid();// 获取cid
		int lac = location.getLac(); // 获取LAC
		network_info.setCell(cellid, lac);

		phone_info.setNetworkOperatorName(tm.getNetworkOperatorName());
		phone_info.setSimCountryIso(tm.getSimCountryIso());
		phone_info.setSimOperator(tm.getSimOperator());

		String device = "";
		if (!Build.MANUFACTURER.equals(Build.UNKNOWN)) {
			device = Build.MANUFACTURER;
		}
		if (!Build.BOARD.equals(Build.UNKNOWN)) {
			device += " " + Build.BOARD;
		}
		if (!Build.BRAND.equals(Build.UNKNOWN)) {
			device += " (" + Build.BRAND + ")";
		}
		phone_info.setDevice(device);

		int type = tm.getPhoneType();
		String phoneType = "Unknown";
		switch (type) {
		case TelephonyManager.PHONE_TYPE_NONE:
			phoneType = "Unknown";
			break;
		case TelephonyManager.PHONE_TYPE_GSM:
			phoneType = "GSM";
			break;
		case TelephonyManager.PHONE_TYPE_CDMA:
			phoneType = "CDMA";
			break;
		case TelephonyManager.PHONE_TYPE_SIP:
			phoneType = "SIP";
			break;
		}
		phone_info.setPhoneType(phoneType);
		phone_info.setIsNetworkRoaming(tm.isNetworkRoaming());
	}

	private void updateSignalStrengths(SignalStrength signalStrength) {
		mLteStrength = (Integer) ReflectUtils.invoke(signalStrength,
				"getLteDbm");
		if (mLteStrength == 0 || mLteStrength == 255) {
			txt_lte_dbm.setText(getString(R.string.str_lte_signal) + "N/A");
		} else {
			txt_lte_dbm.setText(getString(R.string.str_lte_signal)
					+ mLteStrength);
		}

		List<CellInfo> cellInfos = tm.getAllCellInfo();
		if (cellInfos != null && cellInfos.size() != 0) {
			for (CellInfo cellInfo : cellInfos) {
				if (cellInfo instanceof CellInfoLte) {
					StringBuilder lteBuilder = new StringBuilder();

					int GCI = ((CellInfoLte) cellInfo).getCellIdentity()
							.getCi();
					if (GCI == Integer.MAX_VALUE) {
						lteBuilder.append("GCI:N/A");
					} else {
						lteBuilder.append("GCI:" + GCI);
					}

					int PCI = ((CellInfoLte) cellInfo).getCellIdentity()
							.getPci();
					if (PCI == Integer.MAX_VALUE) {
						lteBuilder.append("\nPCI:N/A");
					} else {
						lteBuilder.append("\nPCI:" + PCI);
					}

					// rsrq
					int PSRQ = (Integer) ReflectUtils.invoke(signalStrength,
							"getLteRsrq");
					lteBuilder.append("\nPSRQ:" + PSRQ + "dB");

					int TAC = ((CellInfoLte) cellInfo).getCellIdentity()
							.getTac();
					if (TAC == Integer.MAX_VALUE) {
						lteBuilder.append("\nTAC:N/A");
					} else {
						lteBuilder.append("\nTAC:" + TAC);
					}

					// CQI
					int CQI = (Integer) ReflectUtils.invoke(signalStrength,
							"getLteCqi");
					if (CQI == Integer.MAX_VALUE) {
						lteBuilder.append("\nCQI:N/A");
					} else {
						lteBuilder.append("\nCQI:" + CQI);
					}

					// Timing Advance
					int TA = ((CellInfoLte) cellInfo).getCellSignalStrength()
							.getTimingAdvance();
					lteBuilder.append("\nTA:" + TA);
					txt_lte_param.setText(lteBuilder.toString());
				}
			}
		}

		int gsmDbm = (Integer) ReflectUtils.invoke(signalStrength, "getGsmDbm");
		txt_data_dbm.setText("" + gsmDbm);
	}

	private void updateDataConnectionChange() {
		if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
			if (mLteStrength == 0 || mLteStrength == 255) {
				txt_lte_dbm.setText(getString(R.string.str_lte_signal) + "N/A");
			} else {
				txt_lte_dbm.setText(getString(R.string.str_lte_signal)
						+ mLteStrength);
			}
		} else {
			txt_lte_dbm.setText(getString(R.string.str_lte_signal) + "N/A");
			Field[] fields = tm.getClass().getFields();
			netType = "N/A";
			for (Field f : fields) {
				if (f.getName().startsWith("NETWORK_TYPE_")) {
					try {
						int type = (Integer) f.get(tm);
						if (tm.getNetworkType() == type) {
							netType = f.getName();
							netType = netType.substring(13);
							txt_data_name.setText(netType + ": ");
							break;
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
			List<CellInfo> neighboringCellInfos = tm.getAllCellInfo();
			if (neighboringCellInfos != null) {
				txt_neighbor_title.setText("Neighbor Cells("
						+ (neighboringCellInfos == null ? 0
								: neighboringCellInfos.size()) + ")");
			}
		}
	}

	private void updateDataParam() {
		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();

		int cellid = location.getCid();// 获取cid
		int lac = location.getLac(); // 获取LAC
		int psc = location.getPsc();
		StringBuilder builder = new StringBuilder();
		builder.append("LAC: " + lac);
		builder.append("\nCID: " + cellid);
		builder.append("\nPSC: " + psc);
		txt_data_param.setText(builder.toString());
	}

}
