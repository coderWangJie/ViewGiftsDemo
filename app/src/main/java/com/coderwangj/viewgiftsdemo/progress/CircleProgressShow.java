package com.coderwangj.viewgiftsdemo.progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.coderwangj.viewgiftsdemo.R;
import com.viewgiftlibs.circleProgress.CircleProgress;

public class CircleProgressShow extends AppCompatActivity {

    private CircleProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_show);
        progress = (CircleProgress) findViewById(R.id.pro);
        progress.setOnProgressListener(new CircleProgress.OnProgressListener() {
            @Override
            public void onProgress(int current) {
                Log.e("WangJ", current + "%");
            }

            @Override
            public void onFinish() {
                Toast.makeText(CircleProgressShow.this, "完成了！", Toast.LENGTH_SHORT).show();

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progressInt = 0;
                while (progressInt <= 100) {
                    handler.sendEmptyMessage(progressInt);
                    progressInt++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.setProgress(msg.what);
        }
    };
}