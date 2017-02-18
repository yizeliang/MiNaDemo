package com.example.yzl.minaclient.mina;

import android.content.Context;
import android.util.Log;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by YZL on 2017/2/17.
 */

public class ConnectionManager {
    private MinaConfig mMinaConfig;

    private WeakReference<Context> mContext;

    private NioSocketConnector mSocketConnector;

    private IoSession mIoSession;

    private InetSocketAddress mAddress;


    public ConnectionManager(MinaConfig config) {
        this.mMinaConfig = config;
        this.mContext = new WeakReference<Context>(config.getContext());
        init();
    }

    private void init() {
        mAddress = new InetSocketAddress(mMinaConfig.getIp(), mMinaConfig.getPort());

        mSocketConnector = new NioSocketConnector();
        mSocketConnector.getSessionConfig().setReadBufferSize(mMinaConfig.getReadBufferSize());

        mSocketConnector.setConnectTimeoutMillis(mMinaConfig.getConnectionTimeOut());
        mSocketConnector.getFilterChain().addLast("Logger", new LoggingFilter());

        mSocketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));

        mSocketConnector.setHandler(new MyDeffaultHandler(mContext.get()));

    }

    /**
     * 连接
     *
     * @return
     */

    public boolean connect() {
        try {

            ConnectFuture future = mSocketConnector.connect(mAddress);

            Log.d("ip:::", mAddress.toString());
            //等待连接创建完成
            future.awaitUninterruptibly();

            mIoSession = future.getSession();
        } catch (Exception e) {
            Log.e("MiNa", mMinaConfig.toString());
            e.printStackTrace();
            return false;
        }
        Log.e("MiNa", mMinaConfig.toString());
        Log.e("MiNa", "connection...." + mIoSession == null ? "fail" : "sucess");
        return mIoSession == null ? false : true;
    }

    /**
     * 断开连接
     */
    public void disConnection() {
        mSocketConnector.dispose();
        mMinaConfig = null;

        mContext = null;

        mIoSession = null;

        mAddress = null;
    }



}
