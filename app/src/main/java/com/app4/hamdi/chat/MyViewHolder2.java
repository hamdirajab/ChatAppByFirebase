package com.app4.hamdi.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder2 extends  RecyclerView.ViewHolder {

    TextView textView,textView2;
    ImageView imageView;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);
        textView2 = itemView.findViewById(R.id.textView16);
        imageView = itemView.findViewById(R.id.imageView3);

    }

}
