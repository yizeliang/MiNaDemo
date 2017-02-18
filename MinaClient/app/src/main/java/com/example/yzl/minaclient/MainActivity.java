package com.example.yzl.minaclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.yzl.minaclient.mina.MinaConstants;
import com.example.yzl.minaclient.mina.MinaService;
import com.example.yzl.minaclient.mina.SessionManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv1)
    TextView tv1;

    private MinaRecevier minaRecevier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        minaRecevier = new MinaRecevier();
        registerMinaRecevier();
    }

    private void registerMinaRecevier() {
        IntentFilter filter = new IntentFilter(MinaConstants.MIMA_BRAODCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(minaRecevier, filter);
    }

    private void unRegisterMinaRecevier() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(minaRecevier);
    }

    @Override
    protected void onDestroy() {
        unRegisterMinaRecevier();
        stopService(new Intent(this, MinaService.class));
        super.onDestroy();
    }

    int i = 1;

    @OnClick({R.id.but1, R.id.but2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but1:
                startService(new Intent(this, MinaService.class));
                break;
            case R.id.but2:
                i++;
                SessionManger.getInstance().writeToServer("消息   ::  " + i);
                break;
        }
    }

    class MinaRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            tv1.setText(intent.getStringExtra(MinaConstants.MESSAGE_KEY));
        }
    }
}
