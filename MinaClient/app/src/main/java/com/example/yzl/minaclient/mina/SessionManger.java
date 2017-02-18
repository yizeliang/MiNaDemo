package com.example.yzl.minaclient.mina;

import org.apache.mina.core.session.IoSession;

import java.util.Stack;

/**
 * Created by YZL on 2017/2/17.
 */

public class SessionManger {

    private static SessionManger mInstance = null;

    private IoSession mSeesion;

    private SessionManger() {

    }

    public static SessionManger getInstance() {
        if (mInstance == null) {
            synchronized (SessionManger.class) {
                if (mInstance == null) {
                    mInstance = new SessionManger();
                }
            }
        }
        return mInstance;
    }

    public void setIoSession(IoSession ioSession) {
        this.mSeesion = ioSession;
    }

    public IoSession getIoSession() {
        return mSeesion;
    }

    public void writeToServer(String msg) {
        if (mSeesion != null)
            mSeesion.write(msg);
    }

    public void closeSession() {
        if (mSeesion != null) {
            mSeesion.closeOnFlush();
            removeSession();
        }
    }

    public void removeSession() {
        this.mSeesion = null;
    }
}
