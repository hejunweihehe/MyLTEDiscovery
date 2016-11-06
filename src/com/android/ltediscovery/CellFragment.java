package com.android.ltediscovery;

import java.util.List;
import com.android.ltediscovery.utils.ReflectUtils;
import com.example.ltediscovery.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CellFragment extends Fragment {
    View view;
    private LinearLayout ly_advanced_data;
    private TextView cellInfo;
    private TelephonyManager tm;
    private SignalStrengthListener listener = new SignalStrengthListener();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cell, container,
                false);
        cellInfo = new TextView(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        ly_advanced_data = (LinearLayout) view
                .findViewById(R.id.ly_advanced_data);
        ly_advanced_data.addView(cellInfo, layoutParams);
        initNet();
        return view;
    }

    private void initNet() {
        tm = (TelephonyManager) getActivity().getSystemService(
                Context.TELEPHONY_SERVICE);
    }

    @Override
    public void onStart() {
        super.onStart();
        tm.listen(listener, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    @Override
    public void onStop() {
        super.onStop();
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
    }

    private class SignalStrengthListener extends PhoneStateListener {

        @SuppressLint("NewApi")
        @Override
        public void onSignalStrengthsChanged(
                android.telephony.SignalStrength signalStrength) {
            updateAdvancedData();
        }

        @Override
        public void onDataConnectionStateChanged(int state) {
            updateAdvancedData();
        }
    }

    @SuppressLint("NewApi")
    public void updateAdvancedData() {
        List<CellInfo> cellInfos = tm.getAllCellInfo();
        // init cell info
        StringBuilder builder = new StringBuilder();
        if (cellInfos != null) {
            cellInfo.setText("Cell Info (" + cellInfos.size() + ")\n\n");
            List<CellInfo> cellInfoList = tm.getAllCellInfo();
            for (CellInfo c : cellInfoList) {
                if (c instanceof CellInfoLte) {
                    builder.append("LTE\n");
                    CellIdentityLte mCellIdentityLte = ((CellInfoLte) c)
                            .getCellIdentity();
                    builder.append(ReflectUtils.getAllResult(mCellIdentityLte));
                    CellSignalStrengthLte mCellSignalStrengthLte = ((CellInfoLte) c)
                            .getCellSignalStrength();
                    builder.append(ReflectUtils
                            .getAllResult(mCellSignalStrengthLte) + "\n");
                }
                else if (c instanceof CellInfoGsm) {
                    builder.append("GSM\n");
                    CellIdentityGsm mCidGsm = ((CellInfoGsm) c)
                            .getCellIdentity();
                    builder.append(ReflectUtils.getAllResult(mCidGsm));
                    CellSignalStrengthGsm mStrengthGsm = ((CellInfoGsm) c)
                            .getCellSignalStrength();
                    builder.append(ReflectUtils.getAllResult(mStrengthGsm)
                            + "\n");
                }
                else if (c instanceof CellInfoCdma) {
                    builder.append("CDMA\n");
                    CellIdentityCdma mCdmaId = ((CellInfoCdma) c)
                            .getCellIdentity();
                    builder.append(ReflectUtils.getAllResult(mCdmaId));
                    CellSignalStrengthCdma mCdmaSignalStrength = ((CellInfoCdma) c)
                            .getCellSignalStrength();
                    builder.append(ReflectUtils
                            .getAllResult(mCdmaSignalStrength)
                            + "\n");
                }
                else if (c instanceof CellInfoWcdma) {
                    builder.append("WCDMA\n");
                    CellIdentityWcdma wcdmaId = ((CellInfoWcdma) c)
                            .getCellIdentity();
                    builder.append(ReflectUtils.getAllResult(wcdmaId));
                    CellSignalStrengthWcdma wcdmaSignal = ((CellInfoWcdma) c)
                            .getCellSignalStrength();
                    builder.append(ReflectUtils.getAllResult(wcdmaSignal)
                            + "\n");
                }
            }
        }

        // init Network Info
        builder.append("\n--------------------");
        builder.append("\n");
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        if (networkInfos != null) {
            builder.append("Network Info (" + networkInfos.length + "):");
            for (NetworkInfo tmp : networkInfos) {
                // type
                String typeName = tmp.getTypeName();
                String subTypename = tmp.getSubtypeName();
                if (!TextUtils.isEmpty(subTypename)) {
                    typeName += "[" + subTypename + "]";
                }
                builder.append("\nType: " + typeName);

                // state
                String state = tmp.getState() + "/" + tmp.getDetailedState();
                builder.append("\nState: " + state);

                // Reason
                builder.append("\nReason: " + tmp.getReason());

                // Extra
                String extra = tmp.getExtraInfo();
                builder.append("\nExtra: " + (extra == null ? "(none)" : extra));

                // Roaming
                builder.append("\nRoaming: " + tmp.isRoaming());
                // Failover
                builder.append("\nFailover: " + tmp.isFailover());
                // Availability
                builder.append("\nAvailability: " + tmp.isAvailable() + "\n");
            }
        }
        cellInfo.setText(builder.toString());
    }
}
