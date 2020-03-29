package com.owant.datasave;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SDCardDemoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.SDCardDemo_bt_login)
    Button SDCardDemoBtLogin;
    @BindView(R.id.sd_card_check_btn)
    Button sdCardCheckBtn;
    private String TAG = "SDCardDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdkard_demo);
        ButterKnife.bind(this);
        listenter();
        SDCardDemoBtLogin.setOnClickListener(this);
        String externalStorageState = Environment.getExternalStorageState();
        Log.d(TAG, "externalStorageState--->" + externalStorageState);
    }

    private void listenter() {
        SDCardDemoBtLogin.setOnClickListener(this);
        sdCardCheckBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SDCardDemo_bt_login:
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(externalStorageDirectory, "info.txt");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write("sunweihao".getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sd_card_check_btn:
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    Log.d(TAG, "内存卡已经安装，媒介安装了");
                } else if (state.equals(Environment.MEDIA_UNMOUNTED)) {
                    Log.d(TAG, "没有安装内存卡,已经移除了");
                } else {
                    Log.d(TAG, "都没有执行");
                }
                break;
        }
    }
}
