package com.singun.openvpn.wrapper;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by singun on 2017/3/4 0004.
 */

public class VpnAutoConfig {
    private Context mContext;
    private String mCacheString;

    public VpnAutoConfig(Context context) {
        mContext = context;
    }

    public String getAutoConfig() {
        if (TextUtils.isEmpty(mCacheString)){
            mCacheString = getAutoConfigFromAsset();
        }
        return mCacheString;
    }

    private String getAutoConfigFromAsset() {
        String config = "";
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = mContext.getAssets().open("test_config.ovpn");
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while (true) {
                line = br.readLine();
                if (line == null)
                    break;
                config += line + "\n";
            }
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return config;
    }
}
