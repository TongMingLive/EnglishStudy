package com.example.tong.englishstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tong.englishstudy.activity.TranslationActivity;
import com.example.tong.englishstudy.adpter.BooksSpinnerAdapter;
import com.example.tong.englishstudy.adpter.MainGridViewAdapter;
import com.example.tong.englishstudy.activity.YilanStudyActivity;
import com.example.tong.englishstudy.constants.Constant;
import com.example.tong.englishstudy.dao.BooksDao;
import com.example.tong.englishstudy.entity.Books;
import com.example.tong.englishstudy.spf.SharePFTool;

import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private android.widget.Spinner mainsp;
    private android.widget.ImageView mainbtnupdate;
    private android.widget.TextView maintvwordname;
    private android.widget.TextView maintvwordnum;
    private android.widget.TextView maintvwordtime;
    private android.widget.GridView maingridview;
    private List<Books> booksList;
    private Books book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.maingridview = (GridView) findViewById(R.id.main_grid_view);
        this.maintvwordtime = (TextView) findViewById(R.id.main_tv_word_time);
        this.maintvwordnum = (TextView) findViewById(R.id.main_tv_word_num);
        this.maintvwordname = (TextView) findViewById(R.id.main_tv_word_name);
        this.mainbtnupdate = (ImageView) findViewById(R.id.main_btn_update);
        this.mainsp = (Spinner) findViewById(R.id.main_sp);
        //获取所有的单元
        BooksDao booksDao = new BooksDao();
        booksList = booksDao.getAllBooks();
        BooksSpinnerAdapter spinnerAdapter = new BooksSpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,booksList);
        mainsp.setAdapter(spinnerAdapter);
        mainsp.setOnItemSelectedListener(spinnerSelectListener);

        mainbtnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(maingridview, "数据库已同步更新", Snackbar.LENGTH_SHORT).show();
            }
        });

        maingridview.setAdapter(new MainGridViewAdapter(this));
        maingridview.setOnItemClickListener(this);
        //取出SharepreFerence用户喜好的下标position
        String wordsPosition = SharePFTool.getInstance(this).getValueByKey(Constant.SPF_WORD_POSITION);
        if (wordsPosition != null){
            int position = Integer.parseInt(wordsPosition);
            mainsp.setSelection(position);
        }

    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        /*Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int topHeight = rect.top;//状态栏的高度
        Logger.e(MainActivity.class,"状态栏高度："+topHeight);
        //获取grid view的像素高度
        int top_grid = getWindow().findViewById(R.id.main_grid_view).getTop();
        Logger.e(MainActivity.class,"gridview上面的高度："+top_grid);
        //计算gridview的高度
        int grid_height = (int)map.get("height")-topHeight-top_grid;
        Logger.e(MainActivity.class,"gridview显示区域的高度："+grid_height);*//**//*
        maingridview.setAdapter(new MainGridViewAdapter(this));*/
    }

    private AdapterView.OnItemSelectedListener spinnerSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            maintvwordname.setText("词库名称："+booksList.get(position).getName());
            maintvwordnum.setText("总词汇量："+booksList.get(position).getNumofword());
            maintvwordtime.setText("创建时间："+booksList.get(position).getGenerate_time());
            book = booksList.get(position);
            //存储用户的喜好名称
            SharePFTool.getInstance(MainActivity.this).putKeyValue(Constant.SPF_WORD_NAME,booksList.get(position).getName());
            //存储position
            SharePFTool.getInstance(MainActivity.this).putKeyValue(Constant.SPF_WORD_POSITION,position+"");
            //存储表名ID
            SharePFTool.getInstance(MainActivity.this).putKeyValue(Constant.SPF_WORD_TABLE,booksList.get(position).getID());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                //一览学习
                Intent intent = new Intent(this, YilanStudyActivity.class);
                intent.putExtra("book",book);
                startActivity(intent);
                break;
            case 1:
                //逐词学习
                break;
            case 2:
                //单元学习
                break;
            case 3:
                //单元测试
                break;
            case 4:
                //生词复习
                break;
            case 5:
                //在线翻译
                startActivity(new Intent(this, TranslationActivity.class));
                break;
        }
    }
}
