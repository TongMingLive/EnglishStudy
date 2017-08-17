package com.example.tong.englishstudy.util;

import android.content.Context;

/**
 * Created by tong- on 2017/5/9.
 */

public class DensityUtil {
    //根据手机分辨率从dp单位转为px像素
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    //根据手机的分辨率从px转为dp
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
}
