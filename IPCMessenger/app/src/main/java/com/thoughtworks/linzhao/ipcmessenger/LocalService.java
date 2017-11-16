package com.thoughtworks.linzhao.ipcmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by linzhao on 16/11/2017.
 */

public class LocalService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();

    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getRandomNumber() {
        int random = mGenerator.nextInt(100);
        Toast.makeText(getApplicationContext(), String.valueOf(random),Toast.LENGTH_SHORT).show();
        return random;
    }
}
