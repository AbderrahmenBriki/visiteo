package com.appsolute.cel.adapter;

import java.util.List;

import com.appsolute.cel.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EtatElementArrayAdapter extends ArrayAdapter<String> {
	
	private Context context;
	private List<String> etatList;

	public EtatElementArrayAdapter(Context context, int resource,
			List<String> etatList) {
		super(context, resource, etatList);
		
		this.context = context;
		this.etatList = etatList;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int positionGetView, View convertView, ViewGroup parent) {
		
		final Holder holder;
		LayoutInflater viewInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null) {
			convertView = viewInflater.inflate(R.layout.dialog_etat_element, parent, false);
			holder = new Holder();
			holder.textView_dialog_type_element = (TextView) convertView.findViewById(R.id.textView_dialog_etat_element);
			holder.imageView_dialog_etat_element = (ImageView) convertView.findViewById(R.id.imageView_dialog_etat_element);
			convertView.setTag(holder);
		}
		else
			holder = (Holder) convertView.getTag();
		
		holder.textView_dialog_type_element.setText(getItem(positionGetView));
		
		if (getItem(positionGetView).replace("\n", " ").equals(context.getString(R.string.etat_non).replace("\n", " ")))
			holder.imageView_dialog_etat_element.setBackground(context.getResources().getDrawable(R.drawable.etat_non_verifiee));
		
		if (getItem(positionGetView).replace("\n", " ").equals(context.getString(R.string.etat_neuf).replace("\n", " ")))
			holder.imageView_dialog_etat_element.setBackground(context.getResources().getDrawable(R.drawable.etat_neuf));
		
		if (getItem(positionGetView).replace("\n", " ").equals(context.getString(R.string.etat_hors).replace("\n", " ")))
			holder.imageView_dialog_etat_element.setBackground(context.getResources().getDrawable(R.drawable.etat_hors_service));
		
		if (getItem(positionGetView).replace("\n", " ").equals(context.getString(R.string.etat_bon).replace("\n", " ")))
			holder.imageView_dialog_etat_element.setBackground(context.getResources().getDrawable(R.drawable.etat_bon));
		
		if (getItem(positionGetView).replace("\n", " ").equals(context.getString(R.string.etat_mauvais).replace("\n", " ")))
			holder.imageView_dialog_etat_element.setBackground(context.getResources().getDrawable(R.drawable.etat_mauvais));
		
		if (getItem(positionGetView).replace("\n", " ").equals(context.getString(R.string.etat_usage).replace("\n", " ")))
			holder.imageView_dialog_etat_element.setBackground(context.getResources().getDrawable(R.drawable.etat_usage));
			
		
		return convertView;
	}
	
	
	/**
	 * Return size of List
	 */
	@Override
	public int getCount() {
		return this.etatList.size();
	}

	/**
	 * Return an Item of List for a position
	 */
	@Override
	public String getItem (int position) {
		return this.etatList.get(position);
	}
	
	
	public static class Holder {
		public TextView textView_dialog_type_element;
		public ImageView imageView_dialog_etat_element;
	}

}
