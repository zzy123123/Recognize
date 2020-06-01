package com.rubbish.adapter;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rubbish.R;
import com.rubbish.utils.GlobalVariables;

public class ImageAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private List<String> list;
	private boolean isUrl;
	public ImageAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	public boolean isUrl() {
		return isUrl;
	}

	public void setUrl(boolean url) {
		isUrl = url;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.fx_item_gridview_image, null);
		ImageView sdvImage = (ImageView) convertView
				.findViewById(R.id.sdv_image);
		String path = list.get(position);
		if(TextUtils.isEmpty(path)){
			Glide.with(context).load(R.mipmap.add).into(sdvImage);
		}else{
			if(isUrl){
//				Glide.with(context).load(GlobalVariables.BASE_URL1 + path).into(sdvImage);
			}else{
				Glide.with(context).load(path).into(sdvImage);
			}
		}
		return convertView;
	}
}
