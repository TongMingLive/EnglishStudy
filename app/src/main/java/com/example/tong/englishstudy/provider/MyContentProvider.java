package com.example.tong.englishstudy.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by tong- on 2017/5/23.
 */

public class MyContentProvider extends ContentProvider{
    private final static int BOOKS_CODE = 1;
    private static UriMatcher matcher = new UriMatcher(BOOKS_CODE);
    private SQLiteDatabase db;
    static {
        matcher.addURI("com.example.tong.englishstudy","BOOKS",BOOKS_CODE);
    }

    @Override //初始化提供程序
    public boolean onCreate() {
        File sdFile = new File(Environment.getExternalStorageDirectory(),"wordroid.db");
        db = getContext().openOrCreateDatabase(sdFile.getAbsolutePath(),getContext().MODE_PRIVATE,null);
        return false;
    }

    /**
     * 查询
     * @param uri 查询的uri
     * @param projection 查询的列（null全部）
     * @param selection 筛选的条件
     * @param selectionArgs 包含查询所需的所有其他信息的软件包。Bundle中的值可能包含SQL样式参数。
     * @param sortOrder 排序
     * @return 它将数据返回给呼叫者
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        if (matcher.match(uri) == BOOKS_CODE){
            cursor = db.query("BOOKS",projection,selection,selectionArgs,null,null,sortOrder);
        }
        return cursor;
    }

    @Nullable
    @Override //返回MIME类型
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
