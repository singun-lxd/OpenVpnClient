package com.singun.openvpn.data;

import android.content.Context;

import com.singun.openvpn.data.model.VpnConfigFile;

import java.util.Collections;
import java.util.List;

/**
 * Created by singun on 2017/3/4 0004.
 */

class DataStorage {
    private Context mContext;
    private DataLoadListener mListener;
    private int mVersion;

    public DataStorage(Context context) {
        mContext = context;
    }

    public void setDataLoadListener(DataLoadListener dataLoadListener) {
        mListener = dataLoadListener;
    }

    public void startLoadData() {
        onDataLoaded();
        //// TODO: 2017/3/4 0004  
    }

    private void onDataLoaded() {
        if (mListener !=null) {
            mListener.onDataLoaded(0, Collections.EMPTY_LIST);
        }
    }

    public void saveData(int version, List<VpnConfigFile> listData) {
        //// TODO: 2017/3/4 0004  
    }

    public int getVersion() {
        return mVersion;
    }

    public void setVersion(int version) {
        mVersion = version;
        //// TODO: 2017/3/4 0004 save version
    }

    public void clearData() {
        clearData(false);
    }

    public void clearData(boolean withFile) {
        //// TODO: 2017/3/4 0004
    }

    public interface DataLoadListener {
        void onDataLoaded(int version, List<VpnConfigFile> listData);
    }
}
