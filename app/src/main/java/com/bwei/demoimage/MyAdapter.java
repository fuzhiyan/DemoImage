package com.bwei.demoimage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/7.
 * time:
 * author:付智焱
 */

public class MyAdapter extends BaseAdapter {


    private Context context;
    private List<CheckBean> list;

    public MyAdapter( Context context, List<CheckBean> list) {

        this.context = context;
        this.list = list;



    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        private TextView text;
        private CheckBox checkBox;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=View.inflate(context,R.layout.list_item,null);
            vh.text= (TextView) convertView.findViewById(R.id.item_text);
            vh.checkBox= (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
//
        vh.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).ischeck()){
                    list.get(position).setIscheck(false);
                    vh.checkBox.setChecked(false);
                } else {
                    list.get(position).setIscheck(true);
                    vh.checkBox.setChecked(true);
                }

                notifyDataSetChanged();

            }
        });


        vh.text.setText(list.get(position).getContent());
        vh.checkBox.setChecked(list.get(position).ischeck());


        return convertView;
    }
}
