package com.example.tong.englishstudy.dao;

import android.database.Cursor;

import com.example.tong.englishstudy.entity.Book;
import com.example.tong.englishstudy.entity.Books;
import com.example.tong.englishstudy.util.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong- on 2017/5/9.
 */

public class BooksDao {
    //查询所有的英语名称
    public List<Books> getAllBooks(){
        String sql = "select * from BOOKS";
        List<Books> list = new ArrayList<>();
        Cursor cursor = App.db.rawQuery(sql,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Books books = new Books();
                books.setID(cursor.getString(cursor.getColumnIndex("ID")));
                books.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                books.setGenerate_time(cursor.getString(cursor.getColumnIndex("GENERATE_TIME")));
                books.setNumoflist(cursor.getInt(cursor.getColumnIndex("NUMOFLIST")));
                books.setNumofword(cursor.getInt(cursor.getColumnIndex("NUMOFWORD")));
                list.add(books);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 获取单个系列的单词数据（所有）
     * @param currentPosition 当前显示单词的第一个下标
     * @param total 显示单词的行数
     * @param tableName 表名
     * @param flag 显示隐藏状态
     * @return 单词数据
     */
    public List<Book> getAllBook(int currentPosition, int total,String tableName,boolean flag){
        String sql = "select * from "+tableName+" limit "+currentPosition+","+total;
        List<Book> list = new ArrayList<>();
        Cursor cursor = App.db.rawQuery(sql,null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book();
                book.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                book.setSpelling(cursor.getString(cursor.getColumnIndex("SPELLING")));
                book.setMeanning(cursor.getString(cursor.getColumnIndex("MEANNING")));
                book.setPhonetic_alphabet(cursor.getString(cursor.getColumnIndex("PHONETIC_ALPHABET")));
                book.setList(cursor.getInt(cursor.getColumnIndex("LIST")));
                book.setExample(cursor.getString(cursor.getColumnIndex("EXAMPLE")));
                book.setOperation(cursor.getInt(cursor.getColumnIndex("OPERATION")));
                book.setPhrase(cursor.getString(cursor.getColumnIndex("PHRASE")));
                book.setPronunciation(cursor.getString(cursor.getColumnIndex("PRONUNCIATION")));
                book.setExample_flag(flag);
                list.add(book);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 获取单个系列的单词数据（已经掌握的词汇）
     * @param currentPosition 当前显示单词的第一个下标
     * @param total 显示单词的行数
     * @param tableName 表名
     * @param flag 显示隐藏状态
     * @return 单词数据
     */
    public List<Book> getBookByOperation(int currentPosition, int total,String tableName,boolean flag){
        String sql = "select * from "+tableName+"where OPERATION = '1' limit "+currentPosition+","+total;
        List<Book> list = new ArrayList<>();
        Cursor cursor = App.db.rawQuery(sql,null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book();
                book.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                book.setSpelling(cursor.getString(cursor.getColumnIndex("SPELLING")));
                book.setMeanning(cursor.getString(cursor.getColumnIndex("MEANNING")));
                book.setPhonetic_alphabet(cursor.getString(cursor.getColumnIndex("PHONETIC_ALPHABET")));
                book.setList(cursor.getInt(cursor.getColumnIndex("LIST")));
                book.setExample(cursor.getString(cursor.getColumnIndex("EXAMPLE")));
                book.setOperation(cursor.getInt(cursor.getColumnIndex("OPERATION")));
                book.setPhrase(cursor.getString(cursor.getColumnIndex("PHRASE")));
                book.setPronunciation(cursor.getString(cursor.getColumnIndex("PRONUNCIATION")));
                book.setExample_flag(flag);
                list.add(book);
            }
            cursor.close();
        }
        return list;
    }
}
