package com.bwei.demoimage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 * time:
 * author:付智焱
 */
public class ThreeActivity extends AppCompatActivity{
    private ListView lv;
    private List<CheckBean> list=new ArrayList<>();
    private boolean checked;
    private int mFirstVisibleItem,mVisibleItemCount,mFotalItemCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lv= (ListView) findViewById(R.id.lv);
        initData();
        final MyAdapter adapter=new MyAdapter(this,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ThreeActivity.this, "" + position, Toast.LENGTH_SHORT).show();

            }
        });
        findViewById(R.id.list_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<list.size();i++){
                    if(!checked){
                        list.get(i).setIscheck(true);
                    }else {
                        list.get(i).setIscheck(false);
                    }
                }
                adapter.notifyDataSetChanged();

                if(!checked){
                    checked = true;
                }else {
                    checked = false;
                }

            }

        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            //滚动 状态改变的时候的方法
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE ){

                    //滚动停止
                    if(mFotalItemCount - mVisibleItemCount - mFirstVisibleItem <= 5){

                        for(int i=51;i<100;i++){
                            CheckBean checkBean = new CheckBean();
                            checkBean.setContent(i+"");
                            list.add(checkBean);
                        }
                        adapter.notifyDataSetChanged();

                    }

                }
            }
            //滚动的时候的方法

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                mFirstVisibleItem = firstVisibleItem;
                mVisibleItemCount = visibleItemCount;
                mFotalItemCount = totalItemCount;

                System.out.println("firstVisibleItem = " + firstVisibleItem);
                System.out.println("visibleItemCount = " + visibleItemCount);
                System.out.println("totalItemCount = " + totalItemCount);

            }
        });


    }

    private void initData() {

        for(int i=0;i<50;i++){
            CheckBean checkBean = new CheckBean();
            checkBean.setContent(i+"");
            list.add(checkBean);
        }
    }

}
