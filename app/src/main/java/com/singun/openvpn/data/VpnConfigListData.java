package com.singun.openvpn.data;

import android.content.Context;

import com.singun.openvpn.data.model.VpnConfigFile;

import java.util.List;

/**
 * Created by singun on 2017/3/4 0004.
 */

public class VpnConfigListData implements DataStorage.DataLoadListener, DataRequest.RequestListener {
    private DataStorage mDataStorage;
    private DataRequest mDataRequest;
    private DataChangeListener mListener;

    private List<VpnConfigFile> mList;

    public VpnConfigListData(Context context) {
        mDataStorage = new DataStorage(context);
        mDataStorage.setDataLoadListener(this);
        mDataRequest = new DataRequest(context);
        mDataRequest.setRequestListener(this);
    }

    public void init(DataChangeListener dataChangeListener) {
        mListener = dataChangeListener;
        mDataStorage.startLoadData();
    }

    public void release() {
        mDataRequest.stopRequest();
    }

    @Override
    public void onDataLoaded(int version, List<VpnConfigFile> listData) {
        mList = listData;
        if (mListener != null) {
            mListener.onDataChange(listData);
        }
        mDataRequest.startRequest(version);
    }

    @Override
    public void onDataResponse(int version, List<VpnConfigFile> listData) {
        int oldVersion = mDataStorage.getVersion();
        if (version == oldVersion) {
            if (mListener != null) {
                mListener.onNotifyUpdated();
            }
            return;
        }
        mDataStorage.clearData();
        mList = listData;
        mDataStorage.saveData(version, listData);

        if (mListener != null) {
            mListener.onDataChange(listData);
        }
    }

    @Override
    public void onDataRequestFailed(int errCode) {
        if (mListener != null) {
            mListener.onNotifyFailed(errCode);
        }
    }

    public interface DataChangeListener {
        void onDataChange(List<VpnConfigFile> listData);
        void onNotifyUpdated();
        void onNotifyFailed(int errCode);
    }
}
