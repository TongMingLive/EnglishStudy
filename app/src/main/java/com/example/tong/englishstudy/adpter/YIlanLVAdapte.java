package com.example.tong.englishstudy.adpter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tong.englishstudy.R;
import com.example.tong.englishstudy.entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong- on 2017/5/11.
 */

public class YIlanLVAdapte extends BaseAdapter {
    private Context context;
    private List<Book> book = new ArrayList<>();

    public YIlanLVAdapte(Context context){
        this.context = context;
    }

    //设置数据源
    public void setDataBook(List<Book> book){
        this.book.clear();
        this.book = book;
        notifyDataSetChanged();//刷新适配器
    }

    @Override
    public int getCount() {
        return book.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_yilan,null);
            holder.page = (LinearLayout) convertView.findViewById(R.id.yilan_item_page);
            holder.phonetic_alphabet = (TextView) convertView.findViewById(R.id.yilan_item_phonetic_alphabet);
            holder.example = (TextView) convertView.findViewById(R.id.yilan_item_example);
            holder.phrase = (TextView) convertView.findViewById(R.id.yilan_item_phrase);
            holder.meanning = (TextView) convertView.findViewById(R.id.yilan_item_meanning);
            holder.zhangwo = (Button) convertView.findViewById(R.id.yilan_item_zhangwo);
            holder.shengci = (Button) convertView.findViewById(R.id.yilan_item_shengci);
            holder.spelling = (TextView) convertView.findViewById(R.id.yilan_item_spelling);
            holder.id = (TextView) convertView.findViewById(R.id.yilan_item_id);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.phonetic_alphabet.setText(book.get(position).getPhonetic_alphabet());
        holder.id.setText(book.get(position).getID()+"");
        holder.spelling.setText(book.get(position).getSpelling());
        holder.meanning.setText(book.get(position).getMeanning());
        holder.phrase.setText(book.get(position).getPhrase());
        holder.example.setText(Html.fromHtml(book.get(position).getExample().replace("#","<br>")));

        holder.page.setVisibility(book.get(position).isExample_flag()?View.VISIBLE:View.GONE);
        holder.zhangwo.setVisibility(book.get(position).isExample_flag()?View.VISIBLE:View.GONE);
        holder.shengci.setVisibility(book.get(position).isExample_flag()?View.VISIBLE:View.GONE);

        return convertView;
    }

    class ViewHolder{
        LinearLayout page;
        TextView phonetic_alphabet,example,phrase,meanning,spelling,id;
        Button zhangwo,shengci;
    }
}
