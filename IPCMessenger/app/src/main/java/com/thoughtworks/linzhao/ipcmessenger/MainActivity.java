package com.thoughtworks.linzhao.ipcmessenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.thoughtworks.linzhao.servicelib.LibService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MSG_ALONE_PROCESS_SERVICE = 1;
    private static final int MSG_LIB_SERVICE = 2;

    private Messenger mService = null;
    private Messenger mLibService = null;
    private LocalService mLocalService = null;

    private boolean mServiceBound = false;
    private boolean mLibServiceBound = false;
    private boolean mLocalServiceBound = false;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mServiceBound = false;
        }
    };

    private ServiceConnection mLibServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLibService = new Messenger(service);
            mLibServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mLibServiceBound = false;
        }
    };

    private ServiceConnection mLocalServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mLocalService = binder.getService();
            mLocalServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLocalService = null;
            mLocalServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void bindAloneProcessService(View v) {
        Intent intent = new Intent(this, AloneProcessService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void unbindAloneProcessService(View v) {
        if(mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    public void callAloneProcessService(View v) {
        if(!mServiceBound) return;
        Message message = Message.obtain(null, MSG_ALONE_PROCESS_SERVICE);
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void bindLibService(View v) {
        Intent intent = new Intent(this, LibService.class);
        bindService(intent, mLibServiceConnection, BIND_AUTO_CREATE);
    }

    public void unbindLibService(View v) {
        if(mLibServiceBound) {
            unbindService(mLibServiceConnection);
            mLibServiceBound = false;
        }
    }

    public void callLibService(View v) {
        if(!mLibServiceBound) return;
        Message message = Message.obtain(null, MSG_LIB_SERVICE);
        try {
            mLibService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void bindLocalService(View v) {
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mLocalServiceConnection, BIND_AUTO_CREATE);
    }

    public void unbindLocalService(View v) {
        if(mLocalServiceBound) {
            unbindService(mLocalServiceConnection);
            mLocalServiceBound = false;
        }
    }

    public void callLocalService(View v) {
        if(!mLocalServiceBound) return;
        mLocalService.getRandomNumber();
    }
}
