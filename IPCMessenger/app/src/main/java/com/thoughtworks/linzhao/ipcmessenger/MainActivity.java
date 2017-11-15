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
    private boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
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
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void unbindAloneProcessService(View v) {
        if(mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void callAloneProcessService(View v) {
        if(!mBound) return;
        Message message = Message.obtain(null, MSG_ALONE_PROCESS_SERVICE);
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void bindLibService(View v) {
        Intent intent = new Intent(this, LibService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void unbindLibService(View v) {
        if(mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void callLibService(View v) {
        if(!mBound) return;
        Message message = Message.obtain(null, MSG_LIB_SERVICE);
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
