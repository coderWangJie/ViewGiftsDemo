package com.viewgiftlibs.mypopwindow;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by WangJ on 2017/9/4.
 */

public class MyPopupWindow extends PopupWindow {
    private final int FLAG_PUPUP_SHOW = 0;
    private final int FLAG_PUPUP_DISMISS = 1;

    private Activity activity;
    private PopupWindow instance;

    public MyPopupWindow(Activity activity) {
        super(activity);
    }

    private void changeBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = params.alpha - 0.02f;
        activity.getWindow().setAttributes(params);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_PUPUP_SHOW) {

            } else {

            }
            this.sendEmptyMessageDelayed(1, 4);
        }
    };

}
