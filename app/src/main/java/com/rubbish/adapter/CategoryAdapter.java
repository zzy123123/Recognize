package com.rubbish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rubbish.R;
import com.rubbish.model.Category;
import com.rubbish.utils.GlobalFunction;
import com.rubbish.utils.GlobalVariables;

import java.util.List;

/**
 * Created by firefox on 2017/12/28.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private List<Category> items;

    // 图片数组
    public CategoryAdapter(Context context, List<Category> items) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.push_item, parent, false));
        return viewHolder;
    }

    // 创建View方法
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos = position;
        Category item = items.get(pos);
        holder.tvTitle.setText(item.getName());
        holder.tvContent.setText(item.getContent());
        holder.tvTime.setText(GlobalFunction.formatDate(item.getCreateTime()));
//        Glide.with(context)
//                .load(GlobalVariables.BASE_URL1+item.getAvatar())
//                .into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private ImageView ivAvatar;
        private int pos;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public MyViewHolder(View convertView) {
            super(convertView);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        }
    }


}
