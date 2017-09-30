package com.coderwangj.viewgiftsdemo.progress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.coderwangj.viewgiftsdemo.R;
import com.viewgiftlibs.payLoading.PayLoading;

public class PayLoadingShow extends AppCompatActivity {
    Button btnSuccess;
    Button btnFail;
    PayLoading payLoading1;
    PayLoading payLoading2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_loading_show);

        payLoading1 = (PayLoading) findViewById(R.id.payLoading1);
        payLoading2 = (PayLoading) findViewById(R.id.payLoading2);
        btnSuccess = (Button) findViewById(R.id.btn_success);
        btnFail = (Button) findViewById(R.id.btn_fail);

        initView();
    }

    private void initView() {
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payLoading1.success();
            }
        });

        btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payLoading2.fail();
            }
        });
    }
}
