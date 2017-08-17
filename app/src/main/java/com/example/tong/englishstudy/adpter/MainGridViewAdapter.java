package com.example.tong.englishstudy.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tong.englishstudy.R;
import com.example.tong.englishstudy.util.DensityUtil;

/**
 * Created by tong- on 2017/5/8.
 */

public class MainGridViewAdapter extends BaseAdapter {
    private Context context;
    private String[] menus;
    private String[] colors;

    public MainGridViewAdapter(Context context){
        this.context = context;
        menus = context.getResources().getStringArray(R.array.main_title);
        colors = context.getResources().getStringArray(R.array.main_color);
    }

    @Override
    public int getCount() {
        return menus.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid,null);
            holder.textView = (TextView) convertView.findViewById(R.id.main_grid_tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setHeight(parent.getHeight()/3 - DensityUtil.dip2px(context,10));
        holder.textView.setText(menus[position]);
        holder.textView.setBackgroundColor(Color.parseColor(colors[position]));
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
