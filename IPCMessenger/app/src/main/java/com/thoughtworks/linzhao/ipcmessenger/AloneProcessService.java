package com.thoughtworks.linzhao.ipcmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by linzhao on 15/11/2017.
 */

public class AloneProcessService extends Service {
    private static final String TAG = AloneProcessService.class.getSimpleName();
    private static final int MSG_ALONE_PROCESS_SERVICE = 1;
    private static final int MSG_LIB_SERVICE = 2;

    private final Messenger messenger = new Messenger(new ServiceHandler());

    private class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ALONE_PROCESS_SERVICE:
                    Toast.makeText(getApplicationContext(), "alone process service", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_LIB_SERVICE:
                    Toast.makeText(getApplicationContext(), "lib service", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"==========>destroy" + TAG);
    }
}
