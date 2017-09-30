package com.coderwangj.viewgiftsdemo.toast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.coderwangj.viewgiftsdemo.R;

public class SingleToastShow extends AppCompatActivity {

    private Toast toast;
    private String[] messageArr = {"盲僧", "银河之力", "维恩", "希维尔", "瑞兹", "小炮"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_toast_show);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleToast(messageArr[(int) (Math.random() * messageArr.length)]);
            }
        });
    }

    private void showSingleToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
