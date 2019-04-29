package com.app4.hamdi.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder extends  RecyclerView.ViewHolder {

    TextView shapTv;
    TextView massgeTv;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        shapTv = itemView.findViewById(R.id.textView9);
        massgeTv = itemView.findViewById(R.id.textView10);

    }

}
