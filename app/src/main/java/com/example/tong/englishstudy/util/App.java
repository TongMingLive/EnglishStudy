package com.example.tong.englishstudy.util;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.tong.englishstudy.entity.Books;

/**
 * Created by tong- on 2017/5/4.
 */

public class App extends Application {

    public static SQLiteDatabase db;
    public static Books books;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
