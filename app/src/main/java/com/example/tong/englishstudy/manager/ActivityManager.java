package com.example.tong.englishstudy.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity的管理
 * Created by tong- on 2017/5/8.
 */

public class ActivityManager {
    private List<Activity> activities = new ArrayList<>();//装载Activity的容器
    private static ActivityManager activityManager = null;
    private ActivityManager(){}//将构造方法定义为私有，外部类不能实例化

    //供外部使用App的单例对象
    public static ActivityManager getInstance(){
        if (activityManager == null){
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    //添加Activity
    public void addActivity(Activity activity){
        activities.add(activity);
    }

    //移除Activity
    public void removerActivity(Activity activity){
        //检查list中是否包含
        if (activities.contains(activity)){
            activity.finish();
            activities.remove(activity);
        }
    }

    //退出程序
    public void exit(){
        for (Activity activity:activities){
            activity.finish();
        }
    }
}
