package com.coderwangj.viewgiftsdemo.dialog;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.coderwangj.viewgiftsdemo.R;

public class MenuDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private float alpha;
    private PopupWindow popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dialog);
        setTitle("对话框相关");

        initView();
    }

    private void initView() {
        findViewById(R.id.btn_popMenu).setOnClickListener(this);
        findViewById(R.id.btn_popWindow).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_popMenu:
                showPopupMenu(v);
                break;
            case R.id.btn_popWindow:
                showPopupWindow(v);
                break;
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popMenu = new PopupMenu(this, v);
        popMenu.getMenuInflater().inflate(R.menu.menu_test, popMenu.getMenu());
        popMenu.show();
    }

    private void showPopupWindow(View v) {
        View view = getLayoutInflater().inflate(R.layout.view_popup_window, null);
        view.findViewById(R.id.img_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuDialogActivity.this, "haha", Toast.LENGTH_SHORT).show();
            }
        });

        popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
//        popWindow.setOutsideTouchable(false); // TODO 好像没什么作用
//        popWindow.setFocusable(true);  // ture-点击BACK取消PopupWindow; false-点BACK关闭外部Activity
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.anim_popupwindow);
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                alpha = 0.5f;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //此处while的条件alpha不能<= 否则会出现黑屏
                        while (alpha < 1) {
                            try {
                                Thread.sleep(30);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = handler.obtainMessage();
                            msg.what = 1;
                            alpha += 0.05f;
                            msg.obj = alpha;
                            handler.sendMessage(msg);
                        }
                    }
                }).start();

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                alpha = 1;
                while (alpha > 0.5f) {
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.05f;
                    msg.obj = alpha;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = (float) msg.obj;
                getWindow().setAttributes(params);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                Log.e("WangJ", "alpha: " + msg.obj.toString());
            }
        }
    };

}
