package com.rubbish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rubbish.R;
import com.rubbish.model.DataBean;

import java.util.List;

public class RecyclerViewadapter extends RecyclerView.Adapter {
    private List<DataBean> lists;
    private Context context;


    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public RecyclerViewadapter(List<DataBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    class myholder extends RecyclerView.ViewHolder{

        private TextView tv1,tv2;
        private CardView cardview;
        public myholder(View itemView) {
            super(itemView);
            tv1= (TextView) itemView.findViewById(R.id.tv1);
            tv2= (TextView) itemView.findViewById(R.id.tv2);
            cardview= itemView.findViewById(R.id.cardview);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myholder holder =new myholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: "+lists.get(position).autor);
        ((myholder)holder).tv1.setText(lists.get(position).autor);
        ((myholder)holder).tv2.setText(lists.get(position).content);
        ((myholder)holder).cardview.setBackgroundColor(lists.get(position).color);
        ((myholder)holder).cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
