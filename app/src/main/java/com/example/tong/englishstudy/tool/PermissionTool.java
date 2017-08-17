package com.example.tong.englishstudy.tool;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.example.tong.englishstudy.BuildConfig;

import java.util.ArrayList;


/**
 * Created by wls on 2017/3/29 18:02.
 */

public class PermissionTool {
    /***
     *  检测是否具有permission权限
     * @param context
     * @param permissions
     * @return 返回没有授权的权限
     */
    public static String[] checkPermission(Context context,String... permissions){
        ArrayList<String> list = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission
                    (context, permission);
            if (PackageManager.PERMISSION_GRANTED != permissionCheck) {
                //没有授权的permission
                list.add(permission);
            }
        }
        //返回没有授权的权限
        return list.toArray(new String[list.size()]);
    }

    /**
     *请求权限
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static boolean requestPermission(Activity activity,String[] permissions,int requestCode){
        //检查是否授权，返回没有授权的权限数组
        String[] denfy_permissions = checkPermission(activity,permissions);
        if (denfy_permissions != null && denfy_permissions.length > 0){
            //含有没有授权的权限
            ActivityCompat.requestPermissions(activity,denfy_permissions,requestCode);
            return false;
        }else {
            //表示全部已经授权
            return true;
        }
    }

    /**
     * 打开用户权限设置
     * @param context
     */
    public static void openPermissionDenity(Activity context){
        context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
    }

    /**
     * 显示是否前往权限管理的对话框
     * @param context
     */
    public static void showDialog(final Activity context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示信息");
        builder.setMessage(message);
        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openPermissionDenity(context);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }


}
