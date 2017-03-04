package com.singun.openvpn.client;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.singun.openvpn.wrapper.OpenVpnConnectWrapper;

import de.blinkt.openvpn.core.VpnStatus;

public class MainActivity extends AppCompatActivity implements OpenVpnConnectWrapper.VpnStateListener {
    OpenVpnConnectWrapper mVpnConnect;

    TextView mStatusView;
    FloatingActionButton mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mVpnConnect = new OpenVpnConnectWrapper();
        mVpnConnect.init(this, this);

        mStatusView = (TextView) findViewById(R.id.status);
        mActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVpnConnect.connectOrDisconnect();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mVpnConnect.unInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateVpnUIStatus(VpnStatus.ConnectionStatus level, String stateMessage) {
        if (TextUtils.isEmpty(stateMessage)) {
            stateMessage = getString(R.string.state_welcome);
        }
        mStatusView.setText(stateMessage);
        if (level == VpnStatus.ConnectionStatus.LEVEL_CONNECTED) {
            mActionButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            mActionButton.setEnabled(true);
        } else if (level == VpnStatus.ConnectionStatus.LEVEL_NOTCONNECTED) {
            mActionButton.setImageResource(android.R.drawable.ic_menu_send);
            mActionButton.setEnabled(true);
        } else {
            mActionButton.setEnabled(false);
        }
    }

    @Override
    public void onStateChange(VpnStatus.ConnectionStatus level, String message) {
        updateVpnUIStatus(level,message);
    }
}
