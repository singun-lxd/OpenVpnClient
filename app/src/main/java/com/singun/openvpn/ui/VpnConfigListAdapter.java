package com.singun.openvpn.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.singun.openvpn.client.R;
import com.singun.openvpn.data.model.VpnConfigFile;

import java.util.List;

/**
 * Created by singun on 2017/3/4 0004.
 */

public class VpnConfigListAdapter extends RecyclerView.Adapter<VpnConfigListAdapter.ViewHolder> {
    private List<VpnConfigFile> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
            textView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public VpnConfigListAdapter(List<VpnConfigFile> dataSet) {
        setData(dataSet);
    }

    public void setData(List<VpnConfigFile> dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vpn_config_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        VpnConfigFile vpnConfigFile = mDataSet.get(position);
        viewHolder.getTextView().setText(vpnConfigFile.getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

