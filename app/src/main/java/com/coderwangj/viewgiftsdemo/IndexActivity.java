package com.coderwangj.viewgiftsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.coderwangj.viewgiftsdemo.clipboard.ClipBoardFrom;
import com.coderwangj.viewgiftsdemo.dialog.MenuDialogActivity;
import com.coderwangj.viewgiftsdemo.editwidget.MenuEditWidgetActivity;
import com.coderwangj.viewgiftsdemo.progress.MenuProgressActivity;
import com.coderwangj.viewgiftsdemo.toast.MenuToastActivity;
import com.coderwangj.viewgiftsdemo.touchview.MenuTouchActivity;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        setTitle("一级分类");

        DisplayMetrics x = getResources().getDisplayMetrics();
        Log.e("WangJ", "density: " + x.density);
        Log.e("WangJ", "densityDpi: " + x.densityDpi);
        Log.e("WangJ", "scaledDensity: " + x.scaledDensity);

        initView();
    }

    private void initView() {
        findViewById(R.id.btn_progress).setOnClickListener(this);
        findViewById(R.id.btn_clipboard).setOnClickListener(this);
        findViewById(R.id.btn_toast).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_dialog).setOnClickListener(this);
        findViewById(R.id.btn_).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_progress:
                Intent intent = new Intent(this, MenuProgressActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_clipboard:
                intent = new Intent(this, ClipBoardFrom.class);
                startActivity(intent);
                break;
            case R.id.btn_toast:
                intent = new Intent(this, MenuToastActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_edit:
                intent = new Intent(this, MenuEditWidgetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dialog:
                intent = new Intent(this, MenuDialogActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_:
                intent = new Intent(this, MenuTouchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
