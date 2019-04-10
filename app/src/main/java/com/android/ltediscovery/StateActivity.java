package com.android.ltediscovery;

import java.util.List;

import com.android.ltediscovery.utils.ViewUtils;
import com.android.ltediscovery.widget.TextArea;
import com.example.ltediscovery.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class StateActivity extends Activity {
    public static final String TAG = "PhoneState";
    LinearLayout root;
    TelephonyManager tm;

    TextArea serviceStateChange;
    TextArea messageWaitingIndicatorChange;
    TextArea callForwardingIndicatorChange;
    TextArea cellLocationChange;
    TextArea callStateChanged;
    TextArea dataConnectionStateChanged;
    TextArea dataActivity;
    TextArea signalStrengthsChanged;
    TextArea cellInfoChanged;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        serviceStateChange = new TextArea(this, "Service State");
        messageWaitingIndicatorChange = new TextArea(this,
                "Message Waiting Indicator");
        callForwardingIndicatorChange = new TextArea(this,
                "Call Forwarding Indicator");
        cellLocationChange = new TextArea(this,
                "Cell Location");
        callStateChanged = new TextArea(this, "Call State");
        dataConnectionStateChanged = new TextArea(this, "Data Connection State");
        dataActivity = new TextArea(this, "Data Activity");
        signalStrengthsChanged = new TextArea(this, "Signal Strengths");
        cellInfoChanged = new TextArea(this, "Cll Info");
        root = (LinearLayout) findViewById(R.id.root);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        root.addView(serviceStateChange, layoutParams);
        root.addView(messageWaitingIndicatorChange, layoutParams);
        root.addView(callForwardingIndicatorChange, layoutParams);
        root.addView(cellLocationChange, layoutParams);
        root.addView(callStateChanged, layoutParams);
        root.addView(dataConnectionStateChanged, layoutParams);
        root.addView(dataActivity, layoutParams);
        root.addView(signalStrengthsChanged, layoutParams);
        root.addView(cellInfoChanged, layoutParams);

        tm = (TelephonyManager) getSystemService(
                Context.TELEPHONY_SERVICE);
        tm.listen(new Listener(),
                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                        PhoneStateListener.LISTEN_CALL_STATE |
                        PhoneStateListener.LISTEN_CELL_INFO |
                        PhoneStateListener.LISTEN_CELL_LOCATION |
                        PhoneStateListener.LISTEN_DATA_ACTIVITY |
                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE |
                        PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR |
                        PhoneStateListener.LISTEN_SERVICE_STATE |
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    private class Listener extends PhoneStateListener {

        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            Log.d(TAG, "onServiceStateChanged");
            ViewUtils.updateText(serviceStateChange.getContentTextView(),
                    serviceState);
        }

        @Override
        public void onMessageWaitingIndicatorChanged(boolean mwi) {
            Log.d(TAG, "onMessageWaitingIndicatorChanged");
        }

        @Override
        public void onCallForwardingIndicatorChanged(boolean cfi) {
            Log.d(TAG, "onCallForwardingIndicatorChanged");
        }

        @Override
        public void onCellLocationChanged(CellLocation location) {
            Log.d(TAG, "onCellLocationChanged");
            ViewUtils.updateText(cellLocationChange.getContentTextView(),
                    location);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.d(TAG, "onCallStateChanged");
        }

        @Override
        public void onDataConnectionStateChanged(int state) {
            Log.d(TAG, "onDataConnectionStateChanged");
        }

        @Override
        public void onDataConnectionStateChanged(int state, int networkType) {
            Log.d(TAG, "onDataConnectionStateChanged");
        }

        @Override
        public void onDataActivity(int direction) {
            Log.d(TAG, "onDataActivity");
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            Log.d(TAG, "onSignalStrengthsChanged");
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            Log.d(TAG, "onCellInfoChanged");
            Log.d(TAG, "list" + cellInfo);
            ViewUtils.updateText(cellInfoChanged.getContentTextView(),
                    cellInfo, CellInfo.class);
        }
    }
}
