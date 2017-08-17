package com.example.tong.englishstudy.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.tong.englishstudy.util.App;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tong- on 2017/5/5.
 */

public class DBcopyTool {
    //复制文件到手机
    public static void copyDatebase(Context context) throws IOException {
        File sdFile = null;

        //判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //sd卡可读可写
            sdFile = Environment.getExternalStorageDirectory();//sd卡的根目录
        }else {
            //无sd卡
            sdFile = Environment.getRootDirectory();//手机根目录
        }

        File file = new File(sdFile,"wordroid.db");
        if (!file.exists()){
            //文件不存在，开始复制文件
            AssetManager assetManager = context.getAssets();//获取Assets管理者对象
            InputStream is = assetManager.open("wordroid.db");//打开文件
            BufferedInputStream bis = new BufferedInputStream(is);//缓冲区输入流
            FileOutputStream fos = new FileOutputStream(file);//获取文件输出流
            BufferedOutputStream bos = new BufferedOutputStream(fos);//缓冲区输出流

            byte[] b = new byte[1024];
            int len;
            while ((len = bis.read(b)) != -1){
                bos.write(b,0,len);
            }
            //关闭流
            bos.close();
            fos.close();
            bis.close();
            is.close();
        }

        //打开数据库
        App.db = context.openOrCreateDatabase(
                file.getAbsolutePath(), SQLiteDatabase.OPEN_READWRITE,null);
    }
}
