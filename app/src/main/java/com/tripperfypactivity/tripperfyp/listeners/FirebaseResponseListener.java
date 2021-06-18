package com.tripperfypactivity.tripperfyp.listeners;

public interface FirebaseResponseListener {
    public void onSuccess(boolean isOffline);
    public void onFailure(boolean isOffline);
}
