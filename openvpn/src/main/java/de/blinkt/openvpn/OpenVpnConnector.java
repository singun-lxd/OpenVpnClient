package de.blinkt.openvpn;

import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.text.TextUtils;

import java.io.IOException;
import java.io.StringReader;

import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.core.VPNLaunchHelper;

/**
 * Created by singun on 2017/3/4 0004.
 */

public class OpenVpnConnector {

    public static void connectToVpn(Context context, String configContent, String userName, String password) {
        if (TextUtils.isEmpty(configContent)) {
            throw new RuntimeException("Invalid config content.");
        }
        Intent intent = VpnService.prepare(context);
        if (intent != null) {
            intent = new Intent(context, ConnectVpnAuthActivity.class);
            intent.putExtra(ConnectVpnAuthActivity.KEY_CONFIG, configContent);
            if (!TextUtils.isEmpty(userName)) {
                intent.putExtra(ConnectVpnAuthActivity.KEY_USERNAME, userName);
            }
            if (!TextUtils.isEmpty(password)) {
                intent.putExtra(ConnectVpnAuthActivity.KEY_PASSWORD, password);
            }
            context.startActivity(intent);
        } else {
            startVpnInternal(context, configContent, userName, password);
        }

    }

    static void startVpnInternal(Context context, String configContent, String userName, String password) {
        ConfigParser cp = new ConfigParser();
        try {
            cp.parseConfig(new StringReader(configContent));
            VpnProfile vp = cp.convertProfile();
            vp.mName = "OpenVpnClient";
            if (vp.checkProfile(context) != de.blinkt.openvpn.R.string.no_error_found) {
                throw new RuntimeException(context.getString(vp.checkProfile(context)));
            }

            vp.mProfileCreator = context.getPackageName();
            vp.mUsername = userName;
            vp.mPassword = password;

            ProfileManager.setTemporaryProfile(vp);

            VPNLaunchHelper.startOpenVpn(vp, context);

        } catch (IOException | ConfigParser.ConfigParseError e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void disconnectFromVpn(Context context) {
        Intent intent = new Intent(context, DisconnectVpnActivity.class);
        context.startActivity(intent);
    }

}
