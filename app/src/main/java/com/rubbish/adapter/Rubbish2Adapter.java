package com.rubbish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubbish.R;
import com.rubbish.model.Rubbish2;
import com.rubbish.utils.GlobalFunction;

import java.util.List;

public class Rubbish2Adapter extends RecyclerView.Adapter<Rubbish2Adapter.MyViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private List<Rubbish2> items;
    // 图片数组
    public Rubbish2Adapter(Context context, List<Rubbish2> items) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.rubbish_item2, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos = position;
        holder.setPos(pos);
        Rubbish2 item = items.get(pos);
        holder.tvName.setText(item.getName());
        holder.tvType.setText(GlobalFunction.getRubbishName(item.getType()));
        holder.tvDesc.setText(item.getTip());
        holder.tvExplain.setText(item.getExplain());
        holder.tvContain.setText(item.getContain());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvType;
        private TextView tvDesc;
        private TextView tvExplain;
        private TextView tvContain;
        private int pos;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public MyViewHolder(View convertView) {
            super(convertView);
            tvType = (TextView) convertView.findViewById(R.id.tv_type);
            tvName = (TextView) convertView.findViewById(R.id.ty_name);
            tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
            tvExplain = (TextView) convertView.findViewById(R.id.tv_explain);
            tvContain = (TextView) convertView.findViewById(R.id.tv_contain);
        }
    }


}
