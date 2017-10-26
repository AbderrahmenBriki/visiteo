package com.appsolute.cel.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsolute.cel.DAO.CEL_Elements_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.activity.PieceActivity;
import com.appsolute.cel.models.CEL_Elements;

import java.util.List;

/**
 * 
 * Custom class who implement TextWatcher, it allow to catch event from TextView and EditText
 * 
 * @author lucien
 *
 */
public class MutableWatcher implements TextWatcher {

	private int mPosition;
	private boolean isActive;
	private holder holder;
	private Context context;
	private Boolean isXML;
	private List<CEL_Elements> elementsList;

	public MutableWatcher(holder holder, Context context, 
			Boolean isXML, List<CEL_Elements> elementsList) {
		this.holder = holder;
		this.context = context;
		this.isXML = isXML;
		this.elementsList = elementsList;
	}

	void setPosition(int position) {
		mPosition = position;
	}

	void setActive(boolean active) {
		isActive = active;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) { }

	@Override
	public void afterTextChanged(Editable s) {
		if (isActive && (mPosition < elementsList.size())) {
			if(isXML) {

                CEL_Elements_DAO elements_dao = new CEL_Elements_DAO(context);
                elements_dao.updateValue(elementsList.get(mPosition));

				if(holder.etatElement_TextView.getText().toString().equals("NF"))
					PieceActivity.colorValuesSparseIntArray.put(mPosition, ContextCompat.getColor(context, R.color.green_neuf));
				if(holder.etatElement_TextView.getText().toString().equals("BE"))
					PieceActivity.colorValuesSparseIntArray.put(mPosition, ContextCompat.getColor(context, R.color.green_bon_etat));
				if(holder.etatElement_TextView.getText().toString().equals("EU"))
					PieceActivity.colorValuesSparseIntArray.put(mPosition, ContextCompat.getColor(context, R.color.blue_etat_usage));
				if(holder.etatElement_TextView.getText().toString().equals("ME"))
					PieceActivity.colorValuesSparseIntArray.put(mPosition, ContextCompat.getColor(context, R.color.orange_mauvais_etat));
				if(holder.etatElement_TextView.getText().toString().equals("HS"))
					PieceActivity.colorValuesSparseIntArray.put(mPosition, ContextCompat.getColor(context, R.color.red_hs));
				if(holder.etatElement_TextView.getText().toString().equals("NV"))
					PieceActivity.colorValuesSparseIntArray.put(mPosition, ContextCompat.getColor(context, R.color.gray_no_verified));
			}
			else {
				CEL_Elements_DAO elements_dao = new CEL_Elements_DAO(context);
				elements_dao.updateValue(elementsList.get(mPosition));

			}
		}
	}

	public static class holder {
		public MutableWatcher mutableWatcher;
		public TextView descriptionElement_TextView;
		public TextView etatElement_TextView;
		public TextView ent_obervations_val;
		public TextView actionElement_TextView;
		public TextView quantiteElement_TextView;
		public TextView trouElement_TextView;
		public TextView typeElement_TextView;
		public CheckBox nettoyagelement_CheckBox;
		public LinearLayout imageLayout;
		public ImageView listImg;
	}
}