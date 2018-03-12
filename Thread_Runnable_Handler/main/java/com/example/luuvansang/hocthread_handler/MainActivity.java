package com.example.luuvansang.hocthread_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MESSAGE_CLOCK=100;
    private static final int EMPTY_MESSAGE=200;
    private Button btn_Start;
    private TextView txt_Time;
    private Handler mHandler;
    int second=60;
    int tictac=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        btn_Start.setOnClickListener(this);
    }

    private void addControls() {
        btn_Start=findViewById(R.id.btn_Start);
        txt_Time=findViewById(R.id.txt_Time);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                txt_Time.setText(String.format("%02d.%02d",msg.arg1,msg.arg2));
                if (msg.what==EMPTY_MESSAGE)
                {
                    Toast.makeText(MainActivity.this,"Time over!",Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        second=60;
        tictac=0;
        countDown();
    }

    private void countDown() {
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(tictac+second*100>0) {
                    try {
                        Thread.sleep(10);

                        tictac--;
                        if (tictac < 0) {
                            tictac = 99;
                            second--;
                        }

                        Message msg = new Message();
                        msg.what = MESSAGE_CLOCK;
                        msg.arg1 = second;
                        msg.arg2 = tictac;
                        mHandler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(EMPTY_MESSAGE);
            }

        });
        thread.start();
    }
}
