package com.coderwangj.viewgiftsdemo.toast;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coderwangj.viewgiftsdemo.R;

public class MenuToastActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_toast);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                Intent intent = new Intent(this, SingleToastShow.class);
                startActivity(intent);
                break;
            case R.id.btn_2:
                showCustomLocationToast("设置了Toast的显示位置");
                break;
            case R.id.btn_3:
                showCustomLookToast("设置了Toast的显示外观");
                break;
        }
    }

    private void showCustomLocationToast(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showCustomLookToast(String str) {
        RelativeLayout view = (RelativeLayout) getLayoutInflater().inflate(R.layout.view_toast, null);

        ((TextView) view.findViewById(R.id.tv_message)).setText(str);

        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_icon);
        // 这里帧动画不用启动也可以播放
        AnimationDrawable animation = (AnimationDrawable) imgIcon.getDrawable();
        animation.start();

        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
