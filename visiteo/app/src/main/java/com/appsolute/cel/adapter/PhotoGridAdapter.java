package com.appsolute.cel.adapter;

import java.util.ArrayList;

import com.appsolute.cel.R;
import com.appsolute.cel.models.OPERA_Photos;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoGridAdapter extends BaseAdapter {
	
	ArrayList<OPERA_Photos> photo_list;
	Context context;
	
	public PhotoGridAdapter(ArrayList<OPERA_Photos> list, Context context) {
		super();
		this.photo_list = list;
		this.context = context;
	}
	
	public int getCount() {
		return photo_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		TextView textView;
		LayoutInflater viewInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		BitmapDrawable bd=(BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.img);
		int height = bd.getBitmap().getHeight()-12;
		int width = bd.getBitmap().getWidth()-12;
		
		if (convertView == null) {
			convertView = viewInflater.inflate(R.layout.photo_view_item, parent, false);
			
			imageView = (ImageView) convertView.findViewById(R.id.photos_view_image);
			textView = (TextView) convertView.findViewById(R.id.photo_view_name);
            
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(photo_list.get(position).getPhoto(), 0, 
    				photo_list.get(position).getPhoto().length));
            imageView.getLayoutParams().height = height;
            imageView.getLayoutParams().width = width;
            
    		textView.setText(photo_list.get(position).getPhotoName());
        }
		
        return convertView;
	}
}
