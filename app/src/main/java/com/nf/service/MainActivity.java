package com.nf.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Intent intent;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            建立连接
//            获取服务操作对象
            MyService.MyBinder binder = (MyService.MyBinder) service;
            binder.getService();//获取到Service
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, MyService.class);
        startService(intent);//启动服务
        checkMyPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(intent);
//        程序销毁时，退出服务
    }
    private void checkMyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                } else {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(this, "未被授予权限，相关功能不可用", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
