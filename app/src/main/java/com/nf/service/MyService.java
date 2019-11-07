package com.nf.service;

import android.app.AlertDialog;
import android.app.AliasActivity;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.sql.Time;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    //  全局声明Handler
    private Handler mHandler;

    public MyService() {
    }

    //  创建一个服务
    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
    }

    //    服务启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyServer", "服务启动了");
        Timer timer = new Timer();

        int n = 5 * 60 * 10;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Looper.prepare();
//                        // TODO: 2019/11/7
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MyApp.getContext());
//                        builder.setTitle("提示");
//                        builder.setMessage("已经过去五分钟了\n且行且珍惜");
//                        builder.setNegativeButton("明白了", null);
//                        Dialog dialog = builder.create();
//                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                        dialog.show();
//                        Looper.loop();
//                    }
//                }).start();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MyApp.getContext());
//                        builder.setTitle("提示");
//                        builder.setMessage("已经过去五分钟了\n且行且珍惜");
//                        builder.setNegativeButton("明白了", null);
//                        Dialog dialog = builder.create();
//                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                        dialog.show();
                        Log.i("MyServer", "服务启动了");
                        showDialog();
                    }
                });
                Log.i("MyServer", "五分钟了");
            }
        }, 10000, n);

        return super.onStartCommand(intent, flags, startId);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("提示")
                .setMessage("已经五分钟过去了\n 时间不等人")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        AlertDialog dialog = builder.create();
        //设置点击其他地方不可取消此 Dialog
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY，否则弹不出
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Objects.requireNonNull(dialog.getWindow()).setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
        }else {
            Objects.requireNonNull(dialog.getWindow()).setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        dialog.show();
    }

    //    服务销毁
    @Override
    public void onDestroy() {
        Log.i("MyServer", "服务死了");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    class MyBinder extends Binder {
        /**
         * 获取Service的方法 * @return 返回PlayerService
         */
        public MyService getService() {
            return MyService.this;
        }
    }

}
