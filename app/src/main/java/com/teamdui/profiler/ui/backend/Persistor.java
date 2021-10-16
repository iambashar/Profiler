package com.teamdui.profiler.ui.backend;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Persistor extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
