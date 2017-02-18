package com.example.yzl.minaclient.mina;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by YZL on 2017/2/17.
 */
public class MyDeffaultHandler extends IoHandlerAdapter {



    private Context context;

    public MyDeffaultHandler(Context context) {
        this.context = context;
    }


    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
//      保存session到Session manager中
        SessionManger.getInstance().setIoSession(session);
    }


    @Override
    public void sessionClosed(IoSession session) throws Exception {
        SessionManger.getInstance().closeSession();
        super.sessionClosed(session);
    }


    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        if (context != null) {
            Intent intent = new Intent(MinaConstants.MIMA_BRAODCAST_ACTION);
            intent.putExtra(MinaConstants.MESSAGE_KEY, message.toString());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
        super.messageReceived(session, message);

    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }
}
