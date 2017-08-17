package com.example.tong.englishstudy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.tong.englishstudy.BaseActivity;
import com.example.tong.englishstudy.R;
import com.example.tong.englishstudy.tool.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线翻译
 * Created by tong- on 2017/5/22.
 */

public class TranslationActivity extends BaseActivity {
    private android.widget.SearchView translationsearch;
    private android.widget.TextView translationpage;
    private android.widget.Switch translationswitch;
    private boolean autoTranslation = true;
    private String path;
    private Map<String,Object> map = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        this.translationswitch = (Switch) findViewById(R.id.translation_switch);
        this.translationpage = (TextView) findViewById(R.id.translation_page);
        this.translationsearch = (SearchView) findViewById(R.id.translation_search);

        translationsearch.setSubmitButtonEnabled(true);
        translationsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String key = null;
                try {
                    key = URLEncoder.encode(query.trim(),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                getTranslation(key);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (autoTranslation){
                    String key = null;
                    try {
                        key = URLEncoder.encode(newText.trim(),"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    getTranslation(key);
                }
                return true;
            }
        });

        translationswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoTranslation = isChecked;
            }
        });
    }

    private void getTranslation(String key){
        path = "http://fanyi.youdao.com/openapi.do?keyfrom=wei54544545&key=86156187&type=data&doctype=json&version=1.1&q="+key;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(path);
                    Logger.e(TranslationActivity.class,path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    if (httpURLConnection.getResponseCode() == 200){
                        InputStream is = httpURLConnection.getInputStream();
                        int len;
                        byte[] b = new byte[1024];
                        StringBuffer sb = new StringBuffer();
                        while ((len=is.read(b))!=-1){
                            sb.append(new String(b,0,len,"utf-8"));
                        }

                        JSONObject jsonObject = new JSONObject(sb.toString());
                        map.put("translation",jsonObject.getString("translation"));
                        map.put("phonetic",jsonObject.getJSONObject("basic").getString("phonetic"));
                        map.put("explains",jsonObject.getJSONObject("basic").getString("explains"));
                        map.put("query",jsonObject.getString("query"));
                        JSONArray jsonArray = jsonObject.getJSONArray("web");
                        List<Map<String,Object>> webList = new ArrayList<>();
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject webJson = jsonArray.getJSONObject(i);
                            webJson.getString("value");
                            Map<String,Object> webMap = new HashMap<>();
                            webMap.put("value",webJson.getString("value"));
                            webMap.put("key",webJson.getString("key"));
                            webList.add(webMap);
                        }
                        map.put("web",webList);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                translationpage.setText(map.get("query")+" "+map.get("translation"));
                            }
                        });

                        is.close();
                        httpURLConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return true;
    }
}
