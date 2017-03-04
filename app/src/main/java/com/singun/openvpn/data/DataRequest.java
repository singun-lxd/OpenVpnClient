package com.singun.openvpn.data;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.singun.openvpn.data.model.VpnConfigFile;
import com.singun.volley.VolleyInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by singun on 2017/3/4 0004.
 */

class DataRequest {
    private Context mContext;
    private RequestListener mListener;

    public DataRequest(Context context) {
        mContext = context;
    }

    public void setRequestListener(RequestListener requestListener) {
        mListener = requestListener;
    }

    public void startRequest(int localVersion) {
        //// TODO: 2017/3/4 0004
        onDataResponse();
    }

    public void stopRequest() {
        //// TODO: 2017/3/4 0004  
    }

    private void onDataResponse() {
        if (mListener != null) {
            List<VpnConfigFile> listData = new ArrayList<>();
            listData.add(new VpnConfigFile("1","VpnConfigUS",
                    "http://freevpnsoftware.net/downloads/US.freevpnsoftware.net.ovpn"));
            listData.add(new VpnConfigFile("2","VpnConfigUK",
                    "http://freevpnsoftware.net/downloads/UK.freevpnsoftware.net.ovpn"));
            mListener.onDataResponse(1, listData);
        }
    }

    public interface RequestListener {
        void onDataResponse(int version, List<VpnConfigFile> listData);
        void onDataRequestFailed(int errCode);
    }
}
