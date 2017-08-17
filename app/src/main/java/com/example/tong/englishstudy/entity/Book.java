package com.example.tong.englishstudy.entity;

/**
 * Created by tong- on 2017/5/11.
 */

public class Book {
    private int ID;
    private String spelling; //单词
    private String meanning; //中文意思
    private String phonetic_alphabet; //英标
    private int list; //单元
    private String example; //例句
    private int operation; //是否掌握
    private String phrase; //组词
    private String pronunciation; //发音
    private boolean example_flag;//例句的显示与隐藏标识符状态

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getMeanning() {
        return meanning;
    }

    public void setMeanning(String meanning) {
        this.meanning = meanning;
    }

    public String getPhonetic_alphabet() {
        return phonetic_alphabet;
    }

    public void setPhonetic_alphabet(String phonetic_alphabet) {
        this.phonetic_alphabet = phonetic_alphabet;
    }

    public int getList() {
        return list;
    }

    public void setList(int list) {
        this.list = list;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public boolean isExample_flag() {
        return example_flag;
    }

    public void setExample_flag(boolean example_flag) {
        this.example_flag = example_flag;
    }
}
