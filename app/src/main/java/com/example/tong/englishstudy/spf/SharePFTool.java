package com.example.tong.englishstudy.spf;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tong- on 2017/5/11.
 */

public class SharePFTool {
    //单例模式
    private SharePFTool(){}
    private static SharePFTool sharePFTool = null;
    private static SharedPreferences spfs;
    private static final String shareName = "words";//轻量级

    public static SharePFTool getInstance(Context context){
        if (sharePFTool == null){
            sharePFTool = new SharePFTool();
        }
        spfs = context.getSharedPreferences(shareName,context.MODE_PRIVATE);
        return sharePFTool;
    }

    /**
     * 通过键名查值
     * @param key 键名
     * @return value结果
     */
    public String getValueByKey(String key){
        if (spfs != null){
            return spfs.getString(key,null);
        }
        return null;
    }

    /**
     * 存储值
     * @param key 键名
     * @param value 值
     */
    public void putKeyValue(String key,String value){
        SharedPreferences.Editor editor = spfs.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
