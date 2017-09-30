package com.coderwangj.viewgiftsdemo.progress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coderwangj.viewgiftsdemo.R;

public class MenuProgressActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_progress);
        setTitle("进度条相关");

        initView();
    }

    private void initView() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                Intent intent = new Intent(this, CircleProgressShow.class);
                startActivity(intent);
                break;
            case R.id.btn_2:
                intent = new Intent(this, PayLoadingShow.class);
                startActivity(intent);
                break;
        }
    }
}
