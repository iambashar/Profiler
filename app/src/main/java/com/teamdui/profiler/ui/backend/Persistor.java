package com.teamdui.profiler.ui.backend;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;

import com.google.firebase.database.FirebaseDatabase;


public class Persistor extends Application {

    private static final int REQUEST_READ_EXTERNAL_STORAGE_DATA = 21412;
    public static boolean isPersistorEnabled = false;
    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        isPersistorEnabled = true;
    }
}
