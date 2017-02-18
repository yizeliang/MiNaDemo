package com.example.yzl.minaclient.mina;

import android.content.Context;

/**
 * Created by YZL on 2017/2/17.
 */
public class MinaConfig {

    private Context context;
    private String ip;
    private int port;
    private long connectionTimeOut;
    private int readBufferSize;

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public static class Build {
        private Context context;
        private String ip = "127.0.0.1";
        private int port = 8080;
        private long connectionTimeOut = 3000;
        private int readBufferSize = 2048;

        public Build(Context context) {
            this.context = context;
        }

        public Build setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Build setPort(int port) {
            this.port = port;
            return this;
        }

        public Build setConnectionTimeOut(long connectionTimeOut) {
            this.connectionTimeOut = connectionTimeOut;
            return this;

        }

        public Build setReadBufferSize(int readBufferSize) {
            this.readBufferSize = readBufferSize;
            return this;
        }

        private void applyConfig(MinaConfig config) {
            config.context = this.context;
            config.ip = this.ip;
            config.port = this.port;
            config.connectionTimeOut = this.connectionTimeOut;
            config.readBufferSize = this.readBufferSize;
        }

        public MinaConfig builder() {
            MinaConfig config = new MinaConfig();
            applyConfig(config);
            return config;
        }

    }


    @Override
    public String toString() {
        return "MinaConfig{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", connectionTimeOut=" + connectionTimeOut +
                ", readBufferSize=" + readBufferSize +
                '}';
    }
}
