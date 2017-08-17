package com.example.tong.englishstudy.entity;

import java.io.Serializable;

/**
 * Created by tong- on 2017/5/9.
 */

public class Books implements Serializable {
    private String ID;
    private String name;
    private String generate_time;
    private int numoflist;
    private int numofword;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenerate_time() {
        return generate_time;
    }

    public void setGenerate_time(String generate_time) {
        this.generate_time = generate_time;
    }

    public int getNumoflist() {
        return numoflist;
    }

    public void setNumoflist(int numoflist) {
        this.numoflist = numoflist;
    }

    public int getNumofword() {
        return numofword;
    }

    public void setNumofword(int numofword) {
        this.numofword = numofword;
    }
}
