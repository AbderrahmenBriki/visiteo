package com.appsolute.cel.adapter;

import java.util.List;

import com.appsolute.cel.fragment.ClesFrag;
import com.appsolute.cel.models.CEL_Clefs;
import com.appsolute.cel.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class CEL_ClefsArrayAdapter extends ArrayAdapter<CEL_Clefs>{
	
	private Context context;
	public List<CEL_Clefs> listCEL_Clefs;
	
	public CEL_ClefsArrayAdapter(Context context, int resource, List<CEL_Clefs> listCEL_Clefs) {
		super(context, resource, listCEL_Clefs);
		this.context = context;
		this.listCEL_Clefs = listCEL_Clefs;
	}

	public CEL_Clefs getCEL_Elements(int i) {
		return listCEL_Clefs.get(i);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater viewInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null) {
			convertView = viewInflater.inflate(R.layout.cles_item, parent, false);
		}
		
		CEL_Clefs clef = getItem(position);
		
		if(clef != null) {
			ImageView delete = (ImageView)convertView.findViewById(R.id.delete_key);
			ImageView minus = (ImageView)convertView.findViewById(R.id.minus);
			ImageView pluse = (ImageView)convertView.findViewById(R.id.pluse);
			ImageView minus1 = (ImageView)convertView.findViewById(R.id.minus1);
			ImageView pluse1 = (ImageView)convertView.findViewById(R.id.pluse1);
			final TextView cles_verifiees = (TextView)convertView.findViewById(R.id.cles_verifiees);	
			final TextView cles_nonverifiees = (TextView)convertView.findViewById(R.id.cles_nonverifiees);
			final TextView cls_total = (TextView)convertView.findViewById(R.id.cls_total);
			Spinner keys_Spinner = (Spinner)convertView.findViewById(R.id.cles_spinner);	
			CheckBox cls_hs = (CheckBox)convertView.findViewById(R.id.cls_hs);
			EditText commentKeys_EditText = (EditText)convertView.findViewById(R.id.commentKey_EditText);
			keys_Spinner.setTag(0);
			
			try {
				if(listCEL_Clefs.size()!=0 && listCEL_Clefs.size()>=position) {
					cles_verifiees.setText(""+listCEL_Clefs.get(position).getNombreClefVerifiee());
					cles_nonverifiees.setText(""+listCEL_Clefs.get(position).getNombreClefNonVerifiee());
					cls_total.setText(""+listCEL_Clefs.get(position).getTotalClefs());
					if(listCEL_Clefs.get(position).getComment() == null || listCEL_Clefs.get(position).getComment().equalsIgnoreCase("null"))
						commentKeys_EditText.setText("");
					else
						commentKeys_EditText.setText(""+listCEL_Clefs.get(position).getComment());
					if(listCEL_Clefs.get(position).isHsClefs()) {
						cls_hs.setChecked(true);
					}
					keys_Spinner.setSelection(listCEL_Clefs.get(position).getTypeClefs());
					keys_Spinner.setTag(listCEL_Clefs.get(position).getIdClefs());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			keys_Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent,
						View view, int pos, long id) {
					listCEL_Clefs.get(position).setTypeClefs(pos);
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					return;
				}
			});
			
			cls_hs.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listCEL_Clefs.get(position).setHsClefs(!listCEL_Clefs.get(position).isHsClefs());
				}
			});
			
			commentKeys_EditText.addTextChangedListener(new TextWatcher() {
				@Override    
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					
				}
				
				@Override    
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if(s.length() != 0) {
						listCEL_Clefs.get(position).setComment(s.toString());
					}
					else {
						listCEL_Clefs.get(position).setComment("");
					}
				}
			});
			
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					listCEL_Clefs.remove(position);
					notifyDataSetChanged();
				}
			});
			
			pluse.setOnClickListener(new OnClickListener() {					
				@Override
				public void onClick(View v) {
					ClesFrag.total_keys = ClesFrag.total_keys + 1;
					ClesFrag.total_txt.setText(String.valueOf(ClesFrag.total_keys));
					int val = Integer.parseInt(cles_verifiees.getText().toString());
					val++;
					cles_verifiees.setText(""+val);
					int total = val + Integer.parseInt(cles_nonverifiees.getText().toString());
					cls_total.setText(""+total);
					
					listCEL_Clefs.get(position).setNombreClefVerifiee(val);
					listCEL_Clefs.get(position).setTotal(listCEL_Clefs.get(position).getTotalClefs());
				}
			});
			
			minus.setOnClickListener(new OnClickListener() {					
				@Override
				public void onClick(View v) {
					int val = Integer.parseInt(cles_verifiees.getText().toString());
					if(val>0) {
						ClesFrag.total_keys = ClesFrag.total_keys - 1;
						ClesFrag.total_txt.setText(String.valueOf(ClesFrag.total_keys));
						val--;
						cles_verifiees.setText(""+val);
						int total = val + Integer.parseInt(cles_nonverifiees.getText().toString());
						cls_total.setText(""+total);
						
						listCEL_Clefs.get(position).setNombreClefVerifiee(val);
						listCEL_Clefs.get(position).setTotal(listCEL_Clefs.get(position).getTotalClefs());
					}
				}
			});
			
			pluse1.setOnClickListener(new OnClickListener() {					
				@Override
				public void onClick(View v) {
					ClesFrag.total_keys = ClesFrag.total_keys + 1;
					ClesFrag.total_txt.setText(String.valueOf(ClesFrag.total_keys));
					int val = Integer.parseInt(cles_nonverifiees.getText().toString());
					val++;
					cles_nonverifiees.setText(""+val);
					int total = val + Integer.parseInt(cles_verifiees.getText().toString());
					cls_total.setText(""+total);
					
					listCEL_Clefs.get(position).setNombreClefNonVerifiee(val);
					listCEL_Clefs.get(position).setTotal(listCEL_Clefs.get(position).getTotalClefs());
				}
			});
			
			minus1.setOnClickListener(new OnClickListener() {					
				@Override
				public void onClick(View v) {
					int val = Integer.parseInt(cles_nonverifiees.getText().toString());
					if(val>0) {
						ClesFrag.total_keys = ClesFrag.total_keys - 1;
						ClesFrag.total_txt.setText(String.valueOf(ClesFrag.total_keys));
						val--;
						cles_nonverifiees.setText(""+val);
						int total = val + Integer.parseInt(cles_verifiees.getText().toString());
						cls_total.setText(""+total);
						
						listCEL_Clefs.get(position).setNombreClefNonVerifiee(val);
						listCEL_Clefs.get(position).setTotal(listCEL_Clefs.get(position).getTotalClefs());
					}
				}
			});
		}
		
		return convertView;
	}
	
	@Override
    public int getCount() {
        return super.getCount();
    }
	
}
