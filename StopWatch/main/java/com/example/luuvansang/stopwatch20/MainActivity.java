package com.example.luuvansang.stopwatch20;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Khai bao bien
    Button btn1, btn2;
    TextView txtTimer, txtListTime;
    long time=0L;
    long lasttime=0L;
    CountDownTimer countDownTimer;
    String msgTimer="";
    String listTime="";
    int hour, minute, second;
    int countRound=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnBtn1();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnBtn2();
            }
        });

    }

    private void clickOnBtn2() {
        if(btn2.getText().toString().equalsIgnoreCase("Stop"))
        {
            btn1.setText("Continuer");
            btn2.setText("Reset");
            countDownTimer.cancel();
        }else if(btn2.getText().toString().equalsIgnoreCase("Reset"))
        {
            btn1.setText("Start");
            time=0L;
            countRound=0;
            lasttime=0L;
            txtListTime.setText("\n   0     00:00:00");
        }else
        {
            Toast.makeText(MainActivity.this,"Error in clickBtn2",Toast.LENGTH_LONG).show();
        }
    }

    private void clickOnBtn1() {
        if(btn1.getText().toString().equalsIgnoreCase("Start"))
        {
            btn1.setText("Round");
            btn2.setText("Stop");
            countDownTimer.start();
        }else if(btn1.getText().toString().equalsIgnoreCase("Round"))
        {
            //update ListTime
            updatListTime();
            lasttime=time;
        }else if(btn1.getText().toString().equalsIgnoreCase("Continuer"))
        {
            btn1.setText("Round");
            btn2.setText("Stop");
            listTime="";
            countDownTimer.start();
        }else
        {
            Toast.makeText(MainActivity.this,"Error!",Toast.LENGTH_LONG).show();
        }
    }

    private void updatListTime() {
        countRound++;
        listTime="\n   "+countRound+"     "+updateMsgTimer(time)+"   "+updateMsgTimer(time-lasttime)
                    +txtListTime.getText().toString();
        txtListTime.setText(listTime);
    }

    private void addControls() {
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        txtTimer=(TextView)findViewById(R.id.txtTimer);
        txtListTime=(TextView)findViewById(R.id.txtListTime);
        txtListTime.setText("\n   0     00:00:00");

        countDownTimer=new CountDownTimer(3600000,1000) {
            @Override
            public void onTick(long l) {
                time++;

                msgTimer=updateMsgTimer(time);
                txtTimer.setText(msgTimer);
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        };
    }

    private String updateMsgTimer(long t) {
        hour=(int)t/3600;
        t-=hour*3600;
        minute=(int)t/60;
        t=t%60;
        second=(int)t;

        msgTimer=String.format("%02d:%02d:%02d",hour,minute,second);
     return msgTimer;
    }
}
