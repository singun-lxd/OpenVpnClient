package com.singun.openvpn.client;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import de.blinkt.openvpn.OpenVpnConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private boolean mConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectOrDisconnect();
            }
        });
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

    private void connectOrDisconnect() {
        if (mConnected) {
            disconnectFromVpn();
            mConnected = false;
        } else {
            connectToVpn();
            mConnected = true;
        }
    }

    private void connectToVpn() {
        try {
            InputStream conf = getAssets().open("test_config.ovpn");
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
            OpenVpnConnector.connectToVpn(this, config, null, null);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void disconnectFromVpn() {
        OpenVpnConnector.disconnectFromVpn(this);
    }
}
