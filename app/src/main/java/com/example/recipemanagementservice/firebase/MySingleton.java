package com.example.recipemanagementservice.firebase;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Berk on 24.07.2019.
 */
public class MySingleton {
    // This class for Notifications with Server
    private static MySingleton mInstance;
    private static Context mContext;
    private RequestQueue requestQueue;

    private MySingleton(Context context) {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getmInstance(Context context) {
        if(mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}