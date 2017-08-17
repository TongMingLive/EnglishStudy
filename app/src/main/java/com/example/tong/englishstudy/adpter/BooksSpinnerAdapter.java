package com.example.tong.englishstudy.adpter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.tong.englishstudy.entity.Books;

import java.util.List;

/**
 * Created by tong- on 2017/5/9.
 */

public class BooksSpinnerAdapter extends ArrayAdapter {

    private List<Books> list;

    public BooksSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.list = objects;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position).getName();
    }
}
