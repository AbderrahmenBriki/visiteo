package com.appsolute.cel.adapter;

import java.util.List;

import com.appsolute.cel.R;
import com.appsolute.cel.models.ItemType;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TypeElementArrayAdapter extends ArrayAdapter<ItemType> {

	private Context context;
	private List<ItemType> typeList;

	public TypeElementArrayAdapter(Context context, int resource,
			List<ItemType> typeList) {
		super(context, resource, typeList);

		this.context = context;
		this.typeList = typeList;
	}


	@Override
	public View getView(final int positionGetView, View convertView, ViewGroup parent) {

		final Holder holder;
		LayoutInflater viewInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null) {
			convertView = viewInflater.inflate(R.layout.dialog_type_element, parent, false);
			holder = new Holder();
			holder.textView_dialog_type_element = (TextView) convertView.findViewById(R.id.textView_dialog_type_element);
			holder.imageView_dialog_type_element = (ImageView) convertView.findViewById(R.id.imageView_dialog_type_element);
			convertView.setTag(holder);
		}
		else
			holder = (Holder) convertView.getTag();


		holder.textView_dialog_type_element.setText(typeList.get(positionGetView).getDescriptionItemType());
		String uri = "@drawable/"+typeList.get(positionGetView).getPictureIDItemType();
		uri = uri.toLowerCase();
		int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
		Drawable res;
		try {
			res = ContextCompat.getDrawable(context, imageResource);
			holder.imageView_dialog_type_element.setBackground(res);

		} catch (NotFoundException e) {
			e.printStackTrace();
			holder.imageView_dialog_type_element.setBackground(ContextCompat.getDrawable(context, R.drawable.icon_unknow));
		}


		return convertView;
	}


	/**
	 * Return size of List
	 */
	@Override
	public int getCount() {
		return this.typeList.size();
	}

	/**
	 * Return an Item of List for a position
	 */
	@Override
	public ItemType getItem (int position) {
		return this.typeList.get(position);
	}


	public static class Holder {
		public TextView textView_dialog_type_element;
		public ImageView imageView_dialog_type_element;
	}

}
