package com.android.ltediscovery;

import java.util.List;
import com.example.ltediscovery.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CellFragment extends Fragment {
    View view;
    private TelephonyManager tm;
    private SignalStrengthListener listener = new SignalStrengthListener();
    ListView listView;
    MyAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cell, container,
                false);
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
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
            adapter.notifyDataSetChanged(tm.getAllCellInfo());
        }

        @Override
        public void onDataConnectionStateChanged(int state) {
            adapter.notifyDataSetChanged(tm.getAllCellInfo());
        }
    }

    class MyAdapter extends BaseAdapter {
        List<CellInfo> data;
        LayoutInflater mInflater;

        public MyAdapter() {
            mInflater = LayoutInflater.from(getActivity());
        }

        public void notifyDataSetChanged(List<CellInfo> cellInfos) {
            data = cellInfos;
            super.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (data == null)
                return 0;
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_cell_info, null);
                holder = new ViewHolder();

                holder.txt_network_type = (TextView) convertView
                        .findViewById(R.id.txt_network_type);
                holder.txt_lac = (TextView) convertView
                        .findViewById(R.id.txt_lac);
                holder.txt_cid = (TextView) convertView
                        .findViewById(R.id.txt_cid);
                holder.txt_psc = (TextView) convertView
                        .findViewById(R.id.txt_psc);
                holder.txt_mcc = (TextView) convertView
                        .findViewById(R.id.txt_mcc);
                holder.txt_cid = (TextView) convertView
                        .findViewById(R.id.txt_cid);
                holder.txt_mnc = (TextView) convertView
                        .findViewById(R.id.txt_mnc);
                holder.txt_asu = (TextView) convertView
                        .findViewById(R.id.txt_asu);
                holder.txt_power = (TextView) convertView
                        .findViewById(R.id.txt_power);
                holder.txt_dbm = (TextView) convertView
                        .findViewById(R.id.txt_power);

                holder.txt_4g_cid = (TextView) convertView
                        .findViewById(R.id.txt_4g_cid);
                holder.txt_4g_mcc = (TextView) convertView
                        .findViewById(R.id.txt_4g_mcc);
                holder.txt_4g_mnc = (TextView) convertView
                        .findViewById(R.id.txt_4g_mnc);
                holder.txt_4g_pci = (TextView) convertView
                        .findViewById(R.id.txt_4g_pci);
                holder.txt_4g_tac = (TextView) convertView
                        .findViewById(R.id.txt_4g_tac);
                holder.txt_4g_asu = (TextView) convertView
                        .findViewById(R.id.txt_4g_asu);
                holder.txt_4g_dbm = (TextView) convertView
                        .findViewById(R.id.txt_4g_dbm);
                holder.txt_4g_ta = (TextView) convertView
                        .findViewById(R.id.txt_4g_ta);
                holder.txt_4g_power = (TextView) convertView
                        .findViewById(R.id.txt_4g_power);

                holder.ll_2g_3g_root = convertView
                        .findViewById(R.id.ll_2g_3g_root);
                holder.ll_4g_root = convertView
                        .findViewById(R.id.ll_4g_root);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CellInfo cellInfo = data.get(position);

            String networkType = "";
            if (cellInfo instanceof CellInfoLte) {
                networkType = "LTE";
                holder.ll_2g_3g_root.setVisibility(View.GONE);
                holder.ll_4g_root.setVisibility(View.VISIBLE);

                holder.txt_4g_cid.setText(""
                        + ((CellInfoLte) cellInfo).getCellIdentity().getCi());
                holder.txt_4g_mcc.setText(""
                        + ((CellInfoLte) cellInfo).getCellIdentity().getMcc());
                holder.txt_4g_mnc.setText(""
                        + ((CellInfoLte) cellInfo).getCellIdentity().getMnc());
                holder.txt_4g_pci.setText(""
                        + ((CellInfoLte) cellInfo).getCellIdentity().getPci());
                holder.txt_4g_tac.setText(""
                        + ((CellInfoLte) cellInfo).getCellIdentity().getTac());
                holder.txt_4g_asu.setText(""
                        + ((CellInfoLte) cellInfo).getCellSignalStrength()
                                .getAsuLevel());
                holder.txt_4g_dbm.setText(""
                        + ((CellInfoLte) cellInfo).getCellSignalStrength()
                                .getDbm());
                holder.txt_4g_ta.setText(""
                        + ((CellInfoLte) cellInfo).getCellSignalStrength()
                                .getTimingAdvance());
                holder.txt_4g_power.setText(""
                        + ((CellInfoLte) cellInfo).getCellSignalStrength()
                                .getLevel());
            }
            else if (cellInfo instanceof CellInfoGsm) {
                holder.ll_2g_3g_root.setVisibility(View.VISIBLE);
                holder.ll_4g_root.setVisibility(View.GONE);
                networkType = "GSM";

                holder.txt_lac.setText(""
                        + ((CellInfoGsm) cellInfo).getCellIdentity().getLac());
                holder.txt_cid.setText(""
                        + ((CellInfoGsm) cellInfo).getCellIdentity().getCid());
                holder.txt_psc.setText(""
                        + ((CellInfoGsm) cellInfo).getCellIdentity().getPsc());
                holder.txt_mcc.setText(""
                        + ((CellInfoGsm) cellInfo).getCellIdentity().getMcc());

                holder.txt_mnc.setText(""
                        + ((CellInfoGsm) cellInfo).getCellIdentity().getMnc());
                holder.txt_asu.setText(""
                        + ((CellInfoGsm) cellInfo).getCellSignalStrength()
                                .getAsuLevel());
                holder.txt_dbm.setText(""
                        + ((CellInfoGsm) cellInfo).getCellSignalStrength()
                                .getDbm());
                holder.txt_power.setText(""
                        + ((CellInfoGsm) cellInfo).getCellSignalStrength()
                                .getLevel());
            }
            else if (cellInfo instanceof CellInfoCdma) {
                holder.ll_2g_3g_root.setVisibility(View.VISIBLE);
                holder.ll_4g_root.setVisibility(View.GONE);
                networkType = "CDMA";
            }
            else if (cellInfo instanceof CellInfoWcdma) {
                holder.ll_2g_3g_root.setVisibility(View.VISIBLE);
                holder.ll_4g_root.setVisibility(View.GONE);
                networkType = "WCDMA";

                holder.txt_lac
                        .setText(""
                                + ((CellInfoWcdma) cellInfo).getCellIdentity()
                                        .getLac());
                holder.txt_cid
                        .setText(""
                                + ((CellInfoWcdma) cellInfo).getCellIdentity()
                                        .getCid());
                holder.txt_psc
                        .setText(""
                                + ((CellInfoWcdma) cellInfo).getCellIdentity()
                                        .getPsc());
                holder.txt_mcc
                        .setText(""
                                + ((CellInfoWcdma) cellInfo).getCellIdentity()
                                        .getMcc());

                holder.txt_mnc
                        .setText(""
                                + ((CellInfoWcdma) cellInfo).getCellIdentity()
                                        .getMnc());
                holder.txt_asu.setText(""
                        + ((CellInfoWcdma) cellInfo).getCellSignalStrength()
                                .getAsuLevel());
                holder.txt_dbm.setText(""
                        + ((CellInfoWcdma) cellInfo).getCellSignalStrength()
                                .getDbm());
                holder.txt_power.setText(""
                        + ((CellInfoWcdma) cellInfo).getCellSignalStrength()
                                .getLevel());
            }
            holder.txt_network_type.setText(networkType);
            return convertView;
        }

        class ViewHolder {
            TextView txt_network_type;
            TextView txt_lac;// lac
            TextView txt_cid;// ucid
            TextView txt_psc;// psc
            TextView txt_mcc;// mcc
            TextView txt_mnc;// mnc
            TextView txt_asu;// asu
            TextView txt_dbm;// dbm
            TextView txt_power;// power

            // 4g
            TextView txt_4g_cid;// 4g cids
            TextView txt_4g_mcc;// 4g mcc
            TextView txt_4g_mnc;// 4g mnc
            TextView txt_4g_pci;// 4g pci
            TextView txt_4g_tac;// 4g pac
            TextView txt_4g_asu;// 4g asu
            TextView txt_4g_dbm;// 4g dbm
            TextView txt_4g_ta;// 4g ta
            TextView txt_4g_power;// power

            View ll_2g_3g_root;
            View ll_4g_root;
        }
    }
}
