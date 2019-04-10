package com.android.ltediscovery;

import java.util.List;
import com.android.ltediscovery.utils.ReflectUtils;
import com.android.ltediscovery.utils.ViewUtils;
import com.example.ltediscovery.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AllCellActivity extends Activity {
    LinearLayout leftLayout;
    LinearLayout rightLayout;
    TelephonyManager tm;
    Listener listener = new Listener();
    TextView txt_signal;
    TextView txt_cell_size;
    TextView txt_all_cell;
    public static final String TAG = "AllCellActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cell);

        leftLayout = (LinearLayout) findViewById(R.id.ly_left_container);
        rightLayout = (LinearLayout) findViewById(R.id.ly_right_container);
        txt_signal = (TextView) findViewById(R.id.txt_signal);
        txt_cell_size = (TextView) findViewById(R.id.txt_cell_size);
        txt_all_cell = (TextView) findViewById(R.id.txt_all_cell);

        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(
                listener,
                Listener.LISTEN_SIGNAL_STRENGTHS
                        | Listener.LISTEN_DATA_CONNECTION_STATE);
        Log.d(TAG, tm.toString());
    }

    private class Listener extends PhoneStateListener {
        @Override
        public void onDataConnectionStateChanged(int state) {
            Log.d(TAG, "onDataConnectionStateChanged");
            updateCellInfo();
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            Log.d(TAG, "onSignalStrengthsChanged");
            ViewUtils.updateText(txt_signal, signalStrength);
            updateCellInfo();
        }
    }

    @SuppressLint("NewApi")
    private void updateCellInfo() {
        List<CellInfo> cellInfoList = tm.getAllCellInfo();
        if (cellInfoList == null) {
            return;
        }
        txt_cell_size.setText("周围基站信息(" + cellInfoList.size() + "):");
        StringBuilder builder = new StringBuilder();
        for (CellInfo c : cellInfoList) {
            if (c instanceof CellInfoLte) {
                CellIdentityLte mCellIdentityLte = ((CellInfoLte) c)
                        .getCellIdentity();
                builder.append(ReflectUtils.getAllResult(mCellIdentityLte)
                        + "\n");
                CellSignalStrengthLte mCellSignalStrengthLte = ((CellInfoLte) c)
                        .getCellSignalStrength();
                builder.append(ReflectUtils
                        .getAllResult(mCellSignalStrengthLte) + "\n");
            }
            else if (c instanceof CellInfoGsm) {
                CellIdentityGsm mCidGsm = ((CellInfoGsm) c)
                        .getCellIdentity();
                builder.append(ReflectUtils.getAllResult(mCidGsm) + "\n");
                CellSignalStrengthGsm mStrengthGsm = ((CellInfoGsm) c)
                        .getCellSignalStrength();
                builder.append(ReflectUtils.getAllResult(mStrengthGsm) + "\n");
            }
            else if (c instanceof CellInfoCdma) {
                CellIdentityCdma mCdmaId = ((CellInfoCdma) c)
                        .getCellIdentity();
                builder.append(ReflectUtils.getAllResult(mCdmaId) + "\n");
                CellSignalStrengthCdma mCdmaSignalStrength = ((CellInfoCdma) c)
                        .getCellSignalStrength();
                builder.append(ReflectUtils.getAllResult(mCdmaSignalStrength)
                        + "\n");
            }
            else if (c instanceof CellInfoWcdma) {
                CellIdentityWcdma wcdmaId = ((CellInfoWcdma) c)
                        .getCellIdentity();
                builder.append(ReflectUtils.getAllResult(wcdmaId) + "\n");
                CellSignalStrengthWcdma wcdmaSignal = ((CellInfoWcdma) c)
                        .getCellSignalStrength();
                builder.append(ReflectUtils.getAllResult(wcdmaSignal) + "\n");
            }
        }
        txt_all_cell.setText(builder.toString());
    }

}
