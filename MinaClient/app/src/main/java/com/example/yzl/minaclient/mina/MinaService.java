package com.example.yzl.minaclient.mina;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by YZL on 2017/2/17.
 */

public class MinaService extends Service {

    private ConnectionThread connectionThread;

    @Override
    public void onCreate() {
        super.onCreate();
        connectionThread = new ConnectionThread("mima", getApplicationContext());
        connectionThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        connectionThread.disConnection();
        connectionThread = null;
        super.onDestroy();
    }

    /**
     * 负责 连接的线程
     */
    class ConnectionThread extends HandlerThread {

        Context context;
        boolean isConnection = false;

        ConnectionManager connectionManager;

        public ConnectionThread(String name, Context context) {
            super(name);
            this.context = context;
            MinaConfig minaConfig = new MinaConfig.Build(context)
                    .setIp("192.168.191.1")
                    .setPort(9090)
                    .builder();
            connectionManager = new ConnectionManager(minaConfig);
        }


        /**
         * 开始连接服务器
         */
        @Override
        protected void onLooperPrepared() {
            while (true) {
                isConnection = connectionManager.connect();
                if (isConnection) {
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void disConnection() {
            connectionManager.disConnection();
        }
    }
}
