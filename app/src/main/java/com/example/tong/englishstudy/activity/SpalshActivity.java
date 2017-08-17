package com.example.tong.englishstudy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

import com.example.tong.englishstudy.BaseActivity;
import com.example.tong.englishstudy.MainActivity;
import com.example.tong.englishstudy.R;
import com.example.tong.englishstudy.tool.DBcopyTool;
import com.example.tong.englishstudy.tool.PermissionTool;

import java.io.IOException;

public class SpalshActivity extends BaseActivity {

    private android.widget.ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        this.pb = (ProgressBar) findViewById(R.id.pb);
        checkSdkVersion();
    }

    private void spalsh_start(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (pb.getProgress()<100){
                    try {
                        sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //ui线程
                                pb.setProgress(pb.getProgress()+5);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (pb.getProgress() >= pb.getMax()){
                    startActivity(new Intent(SpalshActivity.this, MainActivity.class));
                    finish();
                }
            }
        }.start();
    }

    //判断sdk版本
    private void checkSdkVersion(){
        if (Build.VERSION.SDK_INT >= 23){
            //sdk版本6.0及以后
            boolean b = PermissionTool.requestPermission(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0X123);
            if (b){
                spalsh_start();
                try {
                    DBcopyTool.copyDatebase(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            spalsh_start();
            try {
                DBcopyTool.copyDatebase(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //权限回调事件
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x123){
            int count = 0;
            for (int result : grantResults){
                if (PackageManager.PERMISSION_DENIED == result){
                    //用户拒绝授权
                    /*PermissionTool.showDialog(this,"拒绝后你将无法使用部分功能，是否前往修改");
                    break;*/
                    count++;
                }
            }
            if (count == grantResults.length){
                //表示全部授权
                try {
                    DBcopyTool.copyDatebase(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            spalsh_start();
        }
    }
}
