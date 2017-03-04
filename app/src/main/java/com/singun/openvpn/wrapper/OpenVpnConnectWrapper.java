package com.singun.openvpn.wrapper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.blinkt.openvpn.OpenVpnConnector;
import de.blinkt.openvpn.core.VpnStatus;

/**
 * Created by singun on 2017/3/4 0004.
 */

public class OpenVpnConnectWrapper implements VpnStatus.StateListener {
    private Context mContext;
    private Handler mHandler;
    private VpnStateListener mListener;

    public void init(Context context, VpnStateListener listener) {
        mContext = context;
        mHandler= new Handler(Looper.getMainLooper());
        mListener = listener;
        VpnStatus.addStateListener(this);
    }

    public void unInit() {
        VpnStatus.removeStateListener(this);
        mHandler = null;
        mContext = null;
        mListener = null;
    }

    public void connectOrDisconnect() {
        boolean isActive = VpnStatus.isVPNActive();
        if (isActive) {
            disconnectFromVpn();
        } else {
            connectToVpn();
        }
    }

    public void connectToVpn() {
        try {
            InputStream conf = mContext.getAssets().open("test_config.ovpn");
            InputStreamReader isr = new InputStreamReader(conf);
            BufferedReader br = new BufferedReader(isr);
            String config = "";
            String line;
            while (true) {
                line = br.readLine();
                if (line == null)
                    break;
                config += line + "\n";
            }
            br.readLine();
            OpenVpnConnector.connectToVpn(mContext, config, null, null);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void disconnectFromVpn() {
        OpenVpnConnector.disconnectFromVpn(mContext);
    }

    @Override
    public void updateState(String state, String logmessage, int localizedResId, final VpnStatus.ConnectionStatus level) {
        if (mHandler == null) {
            return;
        }
        final String stateMessage = VpnStatus.getLastCleanLogMessage(mContext);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onStateChange(level, stateMessage);
                }
            }
        });
    }

    public interface VpnStateListener {
        void onStateChange(VpnStatus.ConnectionStatus level, String message);
    }
}
