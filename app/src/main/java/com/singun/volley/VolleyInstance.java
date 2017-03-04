package com.singun.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

/**
 * Created by singun on 2017/3/5 0005.
 */

public class VolleyInstance {
    private static VolleyInstance sInstance;

    private RequestQueue mRequestQueue;

    public static synchronized VolleyInstance getInstance() {
        if (sInstance == null) {
            sInstance = new VolleyInstance();
        }
        return sInstance;
    }

    private synchronized RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        }
        return mRequestQueue;
    }

    public <T> void addRequest(Context context, Request<T> request) {
        RequestQueue requestQueue = getRequestQueue(context);
        requestQueue.add(request);
    }
}
