package com.singun.openvpn.data.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.singun.openvpn.data.model.VpnConfigFile;

/**
 * Created by singun on 2017/3/5 0005.
 */

public class ConfigRequest extends JsonRequest<VpnConfigFile> {

    public ConfigRequest(int method, String url, String requestBody, Response.Listener<VpnConfigFile> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<VpnConfigFile> parseNetworkResponse(NetworkResponse response) {
        //// TODO: 2017/3/5 0005
        return null;
    }
}
