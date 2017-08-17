package com.example.tong.englishstudy.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tong.englishstudy.BaseActivity;
import com.example.tong.englishstudy.R;
import com.example.tong.englishstudy.adpter.YIlanLVAdapte;
import com.example.tong.englishstudy.constants.Constant;
import com.example.tong.englishstudy.dao.BooksDao;
import com.example.tong.englishstudy.entity.Book;
import com.example.tong.englishstudy.entity.Books;
import com.example.tong.englishstudy.spf.SharePFTool;
import com.example.tong.englishstudy.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong- on 2017/5/10.
 */

public class YilanStudyActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemLongClickListener {
    private android.widget.TextView yilanname;
    private android.widget.Button yilanneedstudy, yilanmasterstudy, yilannv, yilanli;
    private android.widget.RadioButton yilanradioone, yilanradiotwo, yilanradiothree;
    private android.widget.RadioGroup yilancheckbox;
    private MyListView yilanlv;
    private Books books;
    private List<Book> book = new ArrayList<>();
    private YIlanLVAdapte yIlanLVAdapte;
    private int currentPosition = 0;//第一个单词的下标
    private int total = 20;//显示的个数
    private boolean example_flag = false;//true显示例子 false不显示例子
    private final int NEEDWORDS = 0;//需要学习的单词
    private final int NONEEDSWORD = 1;//已经掌握的单词
    private int currentState = NEEDWORDS;//当前状态
    private int noNeedPosition = 0;//已经掌握单词的下标

    private void initView() {
        this.yilanlv = (MyListView) findViewById(R.id.yilan_lv);
        this.yilanli = (Button) findViewById(R.id.yilan_li);
        this.yilannv = (Button) findViewById(R.id.yilan_nv);
        this.yilancheckbox = (RadioGroup) findViewById(R.id.yilan_checkbox);
        this.yilanradiothree = (RadioButton) findViewById(R.id.yilan_radio_three);
        this.yilanradiotwo = (RadioButton) findViewById(R.id.yilan_radio_two);
        this.yilanradioone = (RadioButton) findViewById(R.id.yilan_radio_one);
        this.yilanmasterstudy = (Button) findViewById(R.id.yilan_master_study);
        this.yilanneedstudy = (Button) findViewById(R.id.yilan_need_study);
        this.yilanname = (TextView) findViewById(R.id.yilan_name);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_lan_study);

        initView();

        books = (Books) getIntent().getSerializableExtra("book");
        yilanname.setText(books.getName());

        yilanneedstudy.setOnClickListener(this);
        yilanmasterstudy.setOnClickListener(this);
        yilannv.setOnClickListener(this);
        yilanli.setOnClickListener(this);

        yilancheckbox.setOnCheckedChangeListener(this);

        getDataBook();
        yIlanLVAdapte = new YIlanLVAdapte(this);
        yIlanLVAdapte.setDataBook(book);
        yilanlv.setOnRefrshListener(new MyListView.MyInterface() {
            @Override
            public void onFreshing() {
                //下拉刷新
                int num = total;
                currentPosition = book.get(0).getID() - 1 - total;
                if (currentPosition >= total) {
                    num = total;
                } else {
                    num = book.get(0).getID() - 1;
                }
                String tableName = SharePFTool.getInstance(YilanStudyActivity.this).getValueByKey(Constant.SPF_WORD_TABLE);
                BooksDao dao = new BooksDao();
                book.addAll(0, dao.getAllBook(currentPosition, num, tableName,example_flag));
                yilanlv.finishView();
                yIlanLVAdapte.notifyDataSetChanged();
            }

            @Override
            public void loadMore() {
                //上拉更多
                currentPosition = book.get(book.size() - 1).getID();
                getDataBook();
                yilanlv.finishView();
            }
        });
        yilanlv.setAdapter(yIlanLVAdapte);
        yilanlv.setOnItemLongClickListener(this);
    }


    //获取所有单词数据
    private void getDataBook() {
        String tableName = SharePFTool.getInstance(this).getValueByKey(Constant.SPF_WORD_TABLE);
        BooksDao dao = new BooksDao();
        book.addAll(dao.getAllBook(currentPosition, total, tableName,example_flag));
    }

    //获取已掌握的单词数据
    private void getDataOperation() {
        String tableName = SharePFTool.getInstance(this).getValueByKey(Constant.SPF_WORD_TABLE);
        BooksDao dao = new BooksDao();
        book.addAll(dao.getBookByOperation(noNeedPosition, total, tableName,example_flag));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            //ActivityManager.getInstance().removerActivity(this);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yilan_need_study:
                yilanneedstudy.setTextColor(Color.parseColor("#FFFFFF"));
                yilanneedstudy.setBackgroundColor(Color.parseColor("#009688"));
                yilanmasterstudy.setTextColor(Color.parseColor("#000000"));
                yilanmasterstudy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                book.clear();
                getDataBook();
                yIlanLVAdapte.notifyDataSetChanged();
                currentState = NEEDWORDS;
                break;
            case R.id.yilan_master_study:
                yilanmasterstudy.setTextColor(Color.parseColor("#FFFFFF"));
                yilanmasterstudy.setBackgroundColor(Color.parseColor("#009688"));
                yilanneedstudy.setTextColor(Color.parseColor("#000000"));
                yilanneedstudy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                book.clear();
                getDataOperation();
                yIlanLVAdapte.notifyDataSetChanged();
                currentState = NONEEDSWORD;
                break;
            case R.id.yilan_nv:
                break;
            case R.id.yilan_li:
                //例句显示
                example_flag = !example_flag;
                for (Book b : book) {
                    b.setExample_flag(example_flag);
                }
                yIlanLVAdapte.notifyDataSetChanged();
                yilanli.setText(example_flag ? "例句隐藏" : "例句显示");
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.yilan_radio_one:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.yilan_radio_two:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.yilan_radio_three:
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        book.get(position - 1).setExample_flag(!book.get(position - 1).isExample_flag());
        yIlanLVAdapte.notifyDataSetChanged();
        return true;
    }
}
