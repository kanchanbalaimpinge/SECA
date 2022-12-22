package com.seca.skincare.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;



import java.io.File;

public class Controller extends Application implements LifecycleObserver {
    public static String IMAGE_PATH = null;
    private static final String FORMAT = "hh:mm a";
    public static final String APP_NAME = "SECA";
    static final String APPLICATION_ID = "93585";
    static final String AUTH_KEY = "Fsr-pDLTFnuJsTr";
    static final String AUTH_SECRET = "mxNpGSqYKgfpZYO";
    static final String ACCOUNT_KEY = "MdZ6885x9y7RnXLwMzQf";
    public static boolean wasInBackground = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
        makeDirectory();

    //    ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

//        QBSettings.getInstance().setEnablePushNotification(false);

//        try {
//            SubscribeService.unSubscribeFromPushes(this);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }



    public static String getImagePath() {
        return IMAGE_PATH;
    }

    private void makeDirectory() {
        File directory = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + APP_NAME);
            if (!directory.exists())
                directory.mkdirs();
        } else {
            directory = getApplicationContext().getDir(APP_NAME, Context.MODE_PRIVATE);
            if (!directory.exists())
                directory.mkdirs();
        }

        if (directory != null) {
            File _image = new File(directory + File.separator + "Images");

            if (!_image.exists())
                _image.mkdirs();


            IMAGE_PATH = directory + File.separator + "Images";
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        // app moved to foreground
        wasInBackground=false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        // app moved to background
        wasInBackground =true;
    }
}
