package com.android.ltediscovery;

import java.util.List;
import com.android.ltediscovery.utils.ViewUtils;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NeighboringFragment extends Fragment {
    ListView listView;
    private SignalStrengthListener listener = new SignalStrengthListener();
    private TelephonyManager tm;
    MyAdapter adapter = new MyAdapter();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        listView = new ListView(getActivity());
        tm = (TelephonyManager) getActivity().getSystemService(
                Context.TELEPHONY_SERVICE);
        listView.setAdapter(adapter);
        return listView;
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

    class MyAdapter extends BaseAdapter {
        List<NeighboringCellInfo> data;

        public void notifyDataSetChanged(List<NeighboringCellInfo> data) {
            this.data = data;
            super.notifyDataSetChanged();
        }

        public MyAdapter() {
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = new TextView(getActivity());
                holder = new ViewHolder();
                holder.cellInfo = (TextView) convertView;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NeighboringCellInfo cellInfo = data.get(position);

            ViewUtils.updateText(holder.cellInfo, cellInfo);
            return convertView;
        }

        class ViewHolder {
            TextView cellInfo;
        }
    }

    private class SignalStrengthListener extends PhoneStateListener {

        @SuppressLint("NewApi")
        @Override
        public void onSignalStrengthsChanged(
                android.telephony.SignalStrength signalStrength) {
            adapter.notifyDataSetChanged(tm.getNeighboringCellInfo());
        }

        @Override
        public void onDataConnectionStateChanged(int state) {
            adapter.notifyDataSetChanged(tm.getNeighboringCellInfo());
        }
    }
}
