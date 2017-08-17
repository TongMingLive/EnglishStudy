package com.example.tong.englishstudy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.tong.englishstudy.R;

/**
 * Created by tong- on 2017/5/15.
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener {
    private View headerView, footerView;
    private int headerHeight, footerHeight;
    //头部view
    public static final int pull_freash = 0;//下拉刷新状态
    public static final int release_freash = 1;//释放刷新状态
    public static final int freashing = 2;//正在刷新状态
    private int currenState = pull_freash;//初始状态
    private int currentY = 0;//按下的Y坐标
    //底部view
    private boolean refresh = false;//默认没有在加载状态
    private int lastIndex = 0;
    private MyInterface myInterface;

    public MyListView(Context context) {
        super(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        //初始化顶部view
        initHeaderView();
        //初始化底部view
        initFooterView();
        //ListView的滑动监听事件
        initListener();
    }

    private void initHeaderView() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.item_lv_header, null);
        headerView.measure(0, 0);//开始测量
        headerHeight = headerView.getMeasuredHeight();//获取测量的高度
        headerView.setPadding(0, -headerHeight, 0, 0);//默认设置headerView不可见
        addHeaderView(headerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //按下
                currentY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动
                if (currenState == freashing) break;//当前状态正在刷新

                int moveHeight = (int) (ev.getY()-currentY);//手指移动的高度
                int paddingTop = moveHeight-headerHeight;//计算headerView距离顶部的高度
                //判断向下拉的距离 并且当前处在第0个item
                if (moveHeight > -headerHeight && getFirstVisiblePosition() == 0){
                    headerView.setPadding(0,paddingTop,0,0);
                    if (paddingTop >= 0 && currenState == pull_freash){
                        currenState = release_freash;//修改当前状态为释放刷新
                    }else if (paddingTop < 0 && currenState == release_freash){
                        currenState = pull_freash;
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //弹起
                if (currenState == release_freash){
                    headerView.setPadding(0,0,0,0);
                    currenState = freashing;
                    myInterface.onFreshing();
                }else {
                    headerView.setPadding(0,-headerHeight,0,0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void initFooterView() {
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.item_lv_footer, null);
        footerView.measure(0, 0);//开始测量
        footerHeight = footerView.getMeasuredHeight();//获取测量的高度
        footerView.setPadding(0, -footerHeight, 0, 0);//默认设置footerView不可见
        addFooterView(footerView);
    }

    private void initListener() {
        setOnScrollListener(this);//注册ListView的监听事件
    }

    //SCROLL_STATE_TOUCH_SCROLL  --  正在滑动
    //SCROLL_STATE_IDLE          --  滑动停止
    //SCROLL_STATE_FLING         --  加速滑动
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //ListView的滚动状态
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE &&
                getLastVisiblePosition() == getCount() - 1 &&
                !refresh) {
            lastIndex = getFirstVisiblePosition()+1;
            //表示用户滑动到最后一个，显示上拉更多布局
            footerView.setPadding(0,0,0,0);//设置为可见
            refresh = true;//正在加载的标识
            if (myInterface != null){
                myInterface.loadMore();//回调到监听者
            }
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            },1000);*/
        }
    }

    //刷新完成
    public void finishView(){
        if (!refresh){
            //下拉刷新
            currenState = pull_freash;
            headerView.setPadding(0,-headerHeight,0,0);
        }else {
            //上拉更多
            refresh = false;
            footerView.setPadding(0,-footerHeight,0,0);
            //setSelection(lastIndex);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //滚动时触发
    }

    //供外部注册监听事件
    public void setOnRefrshListener(MyInterface myInterface){
        this.myInterface = myInterface;
    }

    public interface MyInterface{
        void onFreshing();//下拉刷新
        void loadMore();//上拉更多
    }
}