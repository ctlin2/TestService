package com.example.ctlin.testservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = "service demo";

    private MyService mMyServ = null;

    private ServiceConnection mServConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(LOG_TAG, "onServiceConnected()" + name.getClassName());
            mMyServ = ((MyService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Button mBtnStartMyService,
                mBtnStopMyService,
                mBtnBindMyService,
                mBtnUnbindService,
                mBtnCallMyServiceMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStartMyService = (Button) findViewById(R.id.btnStartMyService);
        mBtnStopMyService = (Button) findViewById(R.id.btnStopMyService);
        mBtnBindMyService = (Button) findViewById(R.id.btnBindMyService);
        mBtnUnbindService = (Button) findViewById(R.id.btnUnbindService);
        mBtnCallMyServiceMethod = (Button) findViewById(R.id.btnCallMyServiceMethod);

        mBtnStartMyService.setOnClickListener(btnStartMyServiceOnClickListener);
        mBtnStopMyService.setOnClickListener(btnStopMyServiceOnClickListener);
        mBtnBindMyService.setOnClickListener(btnBindMyServiceOnClickListener);
        mBtnUnbindService.setOnClickListener(btnUnbindServiceOnClickListener);
        mBtnCallMyServiceMethod.setOnClickListener(btnCallMyServiceMethodOnClickListener);
    }

    private View.OnClickListener btnStartMyServiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMyServ = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            startService(it); //第一種方式，啟動
        }
    };

    private View.OnClickListener btnStopMyServiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMyServ = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            stopService(it); //第一種方式，停止
        }
    };

    private View.OnClickListener btnBindMyServiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMyServ = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            bindService(it, mServConn,BIND_AUTO_CREATE); //第二種方式，連結
        }
    };

    private View.OnClickListener btnUnbindServiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMyServ = null;
            unbindService(mServConn); //第二種方式，斷開連結
        }
    };

    private View.OnClickListener btnCallMyServiceMethodOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMyServ != null){
                mMyServ.myMethod();
            }
        }
    };
}
