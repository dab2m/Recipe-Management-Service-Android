package com.example.recipemanagementservice.firebase;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.recipemanagementservice.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Berk on 24.07.2019.
 */
public class FcmInstanceIdService extends FirebaseInstanceIdService {
    // This class for Notifications with Server

    @Override
    public void onTokenRefresh() {
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("deneme "+recentToken);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN), recentToken);
        editor.commit();
    }
}