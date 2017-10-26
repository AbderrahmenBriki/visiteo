package com.appsolute.cel.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.appsolute.cel.activity.MissionsList;
import com.appsolute.cel.models.CEL_Mission;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.models.CEL_Biens;

import java.util.List;

public class CEL_MissionArrayAdapter extends ArrayAdapter<CEL_Mission>{

	private Context context;
	private int resourceView;
	private List<CEL_Mission> listCEL_Missions;
	private CEL_Biens_DAO biens_DAO;
	private CEL_Biens biens;
	public static int radio_select=-1;
	TextView envoyer;

	public CEL_MissionArrayAdapter(Context context, int resource, List<CEL_Mission> listCEL_Missions, TextView envoyer) {
		super(context, resource, listCEL_Missions);
		this.context = context;
		resourceView = resource;
		this.listCEL_Missions = listCEL_Missions;
		this.envoyer = envoyer;
	} 

	@Override
	public int getCount() {
		return this.listCEL_Missions.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		LayoutInflater viewInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (view == null) {
			view = viewInflater.inflate(resourceView, parent, false);
		}

		view =  viewInflater.inflate(R.layout.missions_item, parent, false);
		view.setClickable(true);	

		TextView date_textview = (TextView) view.findViewById(R.id.mis1_val_textview);
		TextView numeroRdv_textview = (TextView) view.findViewById(R.id.mis2_val_textview);
		TextView nombrePieces_textview = (TextView) view.findViewById(R.id.mis3_val_textview);
		TextView adresse_textview = (TextView) view.findViewById(R.id.mis4_val_textview);
		TextView adresseSuite_textview = (TextView) view.findViewById(R.id.mis5_val_textview);
		TextView codePostal_textview = (TextView) view.findViewById(R.id.mis6_val_textview);
		TextView ville_textview = (TextView) view.findViewById(R.id.mis7_val_textview);
		TextView etage_textview = (TextView) view.findViewById(R.id.mis8_val_textview);
		TextView digicode_textview = (TextView) view.findViewById(R.id.mis9_val_textview);
		TextView codeClient_textview = (TextView) view.findViewById(R.id.mis10_val_textview);
		RadioButton radioButtonMission = (RadioButton) view.findViewById(R.id.mis1_radio);
		CheckBox checkBoxMission = (CheckBox) view.findViewById(R.id.mis11_checkbox);
		LinearLayout mis2_layout = (LinearLayout) view.findViewById(R.id.mis2_layout);

		final CEL_Mission cel_Mission = listCEL_Missions.get(position);

		if (cel_Mission != null) {

			biens_DAO = new CEL_Biens_DAO(context);
			biens = biens_DAO.select(cel_Mission.getIdBien());
			biens_DAO.close();

			//Insert dynamic value on TextView if not null
			if(date_textview!=null)
				date_textview.setText(cel_Mission.getHeureMission());

			if(numeroRdv_textview!=null)
				numeroRdv_textview.setText(Integer.toString(cel_Mission.getNumeroRdvMission()));

			if(nombrePieces_textview!=null)
				nombrePieces_textview.setText(Integer.toString(biens.getPiecesBiens()));

			if(adresse_textview!=null)
				adresse_textview.setText(biens.getAdresseBiens());

			if(adresseSuite_textview!=null)
				adresseSuite_textview.setText(biens.getSuiteBiens());

			if(codePostal_textview!=null)
				codePostal_textview.setText(biens.getCodePostalBiens());

			if(ville_textview!=null)
				ville_textview.setText(biens.getVilleBiens());

			if(etage_textview!=null)
				etage_textview.setText(biens.getEtageBiens());
//entrant
			if(digicode_textview!=null) {
				digicode_textview.setText(cel_Mission.getEntrant());
			}
//sortant
			if(codeClient_textview!=null){
				codeClient_textview.setText(cel_Mission.getSortant());
			}

			if(cel_Mission.isExported())
				mis2_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_green));

			try {	

				final int val = position;				
				if(radio_select==position && radio_select != -1) {
					radioButtonMission.setChecked(true);
				} else {
					radioButtonMission.setChecked(false);
				}

				if(cel_Mission.getEDL_isFinished() == 1) {
					checkBoxMission.setChecked(true);
				} else {
					checkBoxMission.setChecked(false);
				}

				radioButtonMission.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						radio_select = val;
						MissionsList.missionSelected = listCEL_Missions.get(val);
						MissionsList.isMissionselected = true;
						notifyDataSetChanged();
						if(listCEL_Missions.get(val).getEtatMission()==2)
							envoyer.setVisibility(View.VISIBLE);
						else
							envoyer.setVisibility(View.INVISIBLE);
					}
				});		

				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						radio_select = val;
						MissionsList.missionSelected = listCEL_Missions.get(val);
						MissionsList.isMissionselected = true;
						notifyDataSetChanged();
						if(listCEL_Missions.get(val).getEtatMission()==2)
							envoyer.setVisibility(View.VISIBLE);
						else
							envoyer.setVisibility(View.INVISIBLE);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		return view;
	}
}

