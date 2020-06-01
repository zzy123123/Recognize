package com.rubbish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubbish.R;
import com.rubbish.model.Rubbish;
import com.rubbish.utils.GlobalFunction;

import java.util.List;

public class RubbishAdapter extends RecyclerView.Adapter<RubbishAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private List<Rubbish> items;
    // 图片数组
    public RubbishAdapter(Context context, List<Rubbish> items) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.rubbish_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos = position;
        holder.setPos(pos);
        Rubbish item = items.get(pos);
        holder.tvName.setText(item.getName());
        holder.tvType.setText(GlobalFunction.getRubbishName(item.getLajitype()));
        holder.tvDesc.setText(item.getLajitip());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvType;
        private TextView tvDesc;
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
        }
    }


}
