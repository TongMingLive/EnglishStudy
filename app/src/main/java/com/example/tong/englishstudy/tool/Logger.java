package com.example.tong.englishstudy.tool;

import android.util.Log;

/**
 * Created by tong- on 2017/5/8.
 */

public class Logger {
    //控制日志文件的打印
    private static boolean flag = true;//控制所有日志的打印

    public static void e(Class name,String log){
        if (flag){
            Log.e(name.getName(),log);
        }
    }

    public static void d(Class name,String log){
        if (flag){
            Log.d(name.getName(),log);
        }
    }

    public static void i(Class name,String log){
        if (flag){
            Log.i(name.getName(),log);
        }
    }

    public static void v(Class name,String log){
        if (flag){
            Log.v(name.getName(),log);
        }
    }
}
