package com.app4.hamdi.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<User> userArrayList;
    int itemLayoutId;
    Context context;

    public MyAdapter(Context context , final ArrayList<User> userArrayList , int itemLayoutId){

        this.userArrayList = userArrayList;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            convertView = LayoutInflater.from(context).inflate(itemLayoutId , null);

            TextView textView = convertView.findViewById(R.id.textView);
            TextView textView2 = convertView.findViewById(R.id.textView16);
            ImageView imageView = convertView.findViewById(R.id.imageView3);

            MyViewHolder vh = new MyViewHolder();
            vh.imageView = imageView;
            vh.textView = textView;
            vh.textView2 = textView2;

            convertView.setTag(vh);  // take any object and save it just
        }

        MyViewHolder vh = (MyViewHolder) convertView.getTag();

        User user = userArrayList.get(position);

        vh.textView.setText(user.getEmail());
        vh.textView2.setText(user.getUsername());
        vh.imageView.setImageResource(R.drawable.head_turqoise);
        return convertView;
    }

    public class MyViewHolder {

        TextView textView,textView2;
        ImageView imageView;

    }
}