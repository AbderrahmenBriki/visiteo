package com.appsolute.cel.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.DAO.CEL_Compteurs_DAO;
import com.appsolute.cel.DAO.CEL_ECS_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.activity.EtatDesLieux;
import com.appsolute.cel.models.CEL_ECS;
import com.appsolute.cel.models.CEL_Etat;
import com.appsolute.cel.BaseFragment;
import com.appsolute.cel.DAO.CEL_Chauffage_DAO;
import com.appsolute.cel.DAO.CEL_Etat_DAO;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.activity.MissionsList;
import com.appsolute.cel.models.CEL_Chauffage;
import com.appsolute.cel.models.CEL_Compteurs;
import com.appsolute.cel.models.OPERA_Photos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CompteursFrag extends BaseFragment {

	LinearLayout img_layout, img_layout1, img_layout2, img_layout3;
	static ImageView camera_img, camera_img_eau1, camera_img_eau2, camera_img_eau3, camera_img_electric;
	View cell1, cell2, cell3, cell4;
	TextView mission_title;

	private static Spinner eau1_spinner, electricite_spinner, eau2_spinner, marqueChauffage_spinner,
			chaudiereChauffage_spinner, typeChauffage_spinner, gaz_Spinner, spinner_eau_chaude_type,
			spinner_eau_chaude_marque;

	public static Spinner spinner_detecteur;

	static EditText electricite_jour_EditText, electricite_nuit_EditText, eau1_chaud_EditText, eau1_froid_EditText,
			eau2_chaud_EditText, eau2_froid_EditText, dateEntretien_EditText, gaz_froid_EditText,
			present_froid_EditText, fournisseur_eletricite_EditText;

	private static CheckBox horsService_checkBox;
	private static int idMission;
	private static CEL_Compteurs compteurEau1 = new CEL_Compteurs();
	private static CEL_Compteurs compteurEau2 = new CEL_Compteurs();
	private static CEL_Compteurs compteurElectricite = new CEL_Compteurs();
	private static CEL_Compteurs compteurGaz = new CEL_Compteurs();
	private static CEL_Compteurs compteurDetecteur = new CEL_Compteurs();
	private static CEL_Chauffage chauffage;
	private static CEL_Chauffage_DAO chauffage_DAO;
	private static CEL_Compteurs_DAO compteurs_DAO;
	private CEL_Etat_DAO etat_DAO;
    private static CEL_ECS_DAO ecs_DAO;
    private static CEL_ECS ecs;
	private static ArrayList<CEL_Compteurs> compteursList;
	static ArrayList<CEL_Etat> etat = new ArrayList<>();
	int height, width;
	private static Boolean isFirstTime;
	String[] spinnerItemsEtatCompteur;
	List<String> spinnerArray = new ArrayList<String>();
	private static OPERA_Photos photo_compteur;
	private static OPERA_Photos_DAO photo_compteur_DAO;
	private static int typeCompteur = 0;

    private boolean spinnerHide = false;
    private boolean checkboxHide = false;

	private static Bitmap imageBitmapCompteurs;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_compteurs, container, false);
		init();
		idMission = MissionsList.missionSelected.getIdMission();
		BitmapDrawable bd = (BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.img);
		height = bd.getBitmap().getHeight() - 12;
		width = bd.getBitmap().getWidth() - 12;

		etat_DAO = new CEL_Etat_DAO(mContext);
		etat = etat_DAO.selectData();
		if(etat.size()==0){
			etat_DAO.addValue(new CEL_Etat(1,"Accessible"));
			etat_DAO.addValue(new CEL_Etat(2,"Inaccessible"));
			etat_DAO.addValue(new CEL_Etat(3,"Illisible"));
			etat_DAO.addValue(new CEL_Etat(4,"Non instalé"));
			etat_DAO.addValue(new CEL_Etat(5,"Non identifié"));
			etat_DAO.addValue(new CEL_Etat(6,"Neuf"));
			etat_DAO.addValue(new CEL_Etat(7,"Non applicable"));
			etat_DAO.addValue(new CEL_Etat(8,"Interrompu"));

			etat = etat_DAO.selectData();
		}
		for (int i = 0; i < etat.size(); i++) {
			spinnerArray.add(etat.get(i).getDescriptionEtat());
		}
		spinnerItemsEtatCompteur = mContext.getResources().getStringArray(R.array.spinnerItemsEtatCompteur);
		horsService_checkBox = (CheckBox) layout.findViewById(R.id.horsService_checkBox);
		spinner_detecteur = (Spinner) layout.findViewById(R.id.spinner_detecteur);

		cell1 = (RelativeLayout) layout.findViewById(R.id.cell1);
		cell2 = (RelativeLayout) layout.findViewById(R.id.cell2);
		cell3 = (RelativeLayout) layout.findViewById(R.id.cell3);
		cell4 = (RelativeLayout) layout.findViewById(R.id.cell4);

		compteurEau1 = new CEL_Compteurs();
		compteurEau2 = new CEL_Compteurs();
		compteurElectricite = new CEL_Compteurs();
		compteurGaz = new CEL_Compteurs();

		img_layout = (LinearLayout) layout.findViewById(R.id.img_layout);
		img_layout1 = (LinearLayout) layout.findViewById(R.id.img_layout1);
		img_layout2 = (LinearLayout) layout.findViewById(R.id.img_layout2);
		img_layout3 = (LinearLayout) layout.findViewById(R.id.img_layout3);

		camera_img_eau1 = (ImageView) layout.findViewById(R.id.camera_img_eau1);
		camera_img_eau2 = (ImageView) layout.findViewById(R.id.camera_img_eau2);
		camera_img_eau3 = (ImageView) layout.findViewById(R.id.camera_img_eau3);
		camera_img_electric = (ImageView) layout.findViewById(R.id.camera_img_electric);
		camera_img_eau1.setTag("false");
		camera_img_eau2.setTag("false");
		camera_img_eau3.setTag("false");
		camera_img_electric.setTag("false");

		electricite_jour_EditText = (EditText) layout.findViewById(R.id.electricite_jour_EditText);
		electricite_nuit_EditText = (EditText) layout.findViewById(R.id.electricite_nuit_EditText);
		eau1_chaud_EditText = (EditText) layout.findViewById(R.id.eau1_chaud_EditText);
		eau1_froid_EditText = (EditText) layout.findViewById(R.id.eau1_froid_EditText);
		eau2_chaud_EditText = (EditText) layout.findViewById(R.id.eau2_chaud_EditText);
		eau2_froid_EditText = (EditText) layout.findViewById(R.id.eau2_froid_EditText);
		gaz_froid_EditText = (EditText) layout.findViewById(R.id.gaz_froid_EditText);
		dateEntretien_EditText = (EditText) layout.findViewById(R.id.dateEntretien_EditText);
		present_froid_EditText = (EditText) layout.findViewById(R.id.present_froid_EditText);

		fournisseur_eletricite_EditText = (EditText) layout.findViewById(R.id.fournisseur_eletricite_EditText);

		electricite_spinner = (Spinner) layout.findViewById(R.id.electricite_spinner);
		eau1_spinner = (Spinner) layout.findViewById(R.id.eau1_spinner);
		eau2_spinner = (Spinner) layout.findViewById(R.id.eau2_spinner);
		gaz_Spinner = (Spinner) layout.findViewById(R.id.gaz_Spinner);
		marqueChauffage_spinner = (Spinner) layout.findViewById(R.id.marqueChauffage_spinner);
		chaudiereChauffage_spinner = (Spinner) layout.findViewById(R.id.chaudiereChauffage_spinner);
		typeChauffage_spinner = (Spinner) layout.findViewById(R.id.typeChauffage_spinner);
		spinner_detecteur = (Spinner) layout.findViewById(R.id.spinner_detecteur);
        spinner_eau_chaude_type = (Spinner) layout.findViewById(R.id.spinner_eau_chaude_type);
        spinner_eau_chaude_marque = (Spinner) layout.findViewById(R.id.spinner_eau_chaude_marque);

		ArrayAdapter<String> electricite_adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, spinnerArray);
		electricite_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		electricite_spinner.setAdapter(electricite_adapter);

		ArrayAdapter<String> eau1_adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, spinnerArray);
		eau1_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eau1_spinner.setAdapter(eau1_adapter);

		ArrayAdapter<String> eau2_adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, spinnerArray);
		eau2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eau2_spinner.setAdapter(eau2_adapter);

		ArrayAdapter<String> gaz_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				spinnerArray);
		gaz_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gaz_Spinner.setAdapter(gaz_adapter);

		initializeSpinnerListener();
		initializeViews();

		img_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				camera_img = (ImageView) v.findViewById(R.id.camera_img_eau1);
				callTakePicture();
				typeCompteur = 1;
			}
		});
		img_layout1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				camera_img = (ImageView) v.findViewById(R.id.camera_img_electric);
				callTakePicture();
				typeCompteur = 2;
			}
		});
		img_layout2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				camera_img = (ImageView) v.findViewById(R.id.camera_img_eau2);
				callTakePicture();
				typeCompteur = 3;
			}
		});

		img_layout3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				camera_img = (ImageView) v.findViewById(R.id.camera_img_eau3);
				callTakePicture();
				typeCompteur = 4;
			}
		});
		return layout;
	}

	private void initializeSpinnerListener() {

		electricite_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 1 || position == 2 || position == 3 || position == 4 || position == 6) {
					electricite_jour_EditText.setEnabled(false);
					electricite_jour_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					electricite_jour_EditText.setText("0");
					electricite_nuit_EditText.setEnabled(false);
					electricite_nuit_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					electricite_nuit_EditText.setText("0");
				} else {
					electricite_jour_EditText.setEnabled(true);
					electricite_jour_EditText.setBackgroundResource(android.R.drawable.edit_text);
					electricite_nuit_EditText.setEnabled(true);
					electricite_nuit_EditText.setBackgroundResource(android.R.drawable.edit_text);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		eau1_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 1 || position == 2 || position == 3 || position == 4 || position == 6) {
					eau1_chaud_EditText.setEnabled(false);
					eau1_chaud_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					eau1_chaud_EditText.setText("0");
					eau1_froid_EditText.setEnabled(false);
					eau1_froid_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					eau1_froid_EditText.setText("0");
				} else {
					eau1_chaud_EditText.setEnabled(true);
					eau1_chaud_EditText.setBackgroundResource(android.R.drawable.edit_text);
					eau1_froid_EditText.setEnabled(true);
					eau1_froid_EditText.setBackgroundResource(android.R.drawable.edit_text);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				return;
			}
		});
		eau2_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 1 || position == 2 || position == 3 || position == 4 || position == 6) {
					eau2_chaud_EditText.setEnabled(false);
					eau2_chaud_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					eau2_chaud_EditText.setText("0");
					eau2_froid_EditText.setEnabled(false);
					eau2_froid_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					eau2_froid_EditText.setText("0");
				} else {
					eau2_chaud_EditText.setEnabled(true);
					eau2_chaud_EditText.setBackgroundResource(android.R.drawable.edit_text);
					eau2_froid_EditText.setEnabled(true);
					eau2_froid_EditText.setBackgroundResource(android.R.drawable.edit_text);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		typeChauffage_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 6 || position == 0) {
                    spinnerHide = true;
                    hasToHideCertificat(true);

				} else {
                    spinnerHide = false;
                    hasToHideCertificat(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		horsService_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				if(b) {
                    checkboxHide = true;
                    hasToHideCertificat(true);

				} else {
                    checkboxHide = false;
                    hasToHideCertificat(false);
				}
			}
		});

		gaz_Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 1 || position == 2 || position == 3 || position == 4 || position == 6) {
					gaz_froid_EditText.setEnabled(false);
					gaz_froid_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					gaz_froid_EditText.setText("0");
				} else {
					gaz_froid_EditText.setEnabled(true);
					gaz_froid_EditText.setBackgroundResource(android.R.drawable.edit_text);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				return;
			}
		});

		spinner_detecteur.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 1) {
					present_froid_EditText.setEnabled(true);
					if (compteurDetecteur.getIndex_2Compteurs() == 0)
						present_froid_EditText.setText("0");
					else
						present_froid_EditText.setText("" + compteurDetecteur.getIndex_2Compteurs());
					present_froid_EditText.setBackgroundResource(android.R.drawable.edit_text);
				} else {
					if (position == 0) {
						present_froid_EditText.setText("");
						compteurDetecteur.setIndex_2Compteurs(0);
						if (!isFirstTime) {
							Toast.makeText(mContext, getString(R.string.detecteur_select_field), Toast.LENGTH_LONG)
									.show();
						}
					} else {
						present_froid_EditText.setFilters(new InputFilter[] {});
						present_froid_EditText.setText("0");
						compteurDetecteur.setIndex_2Compteurs(0);
					}
					present_froid_EditText.setEnabled(false);
					present_froid_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
				}
				return;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				return;
			}
		});
	}

	private static void insertData() {
		try {
			photo_compteur_DAO = new OPERA_Photos_DAO(mContext);
			if (isFirstTime) {
				int eVal = 0, eval1 = 0;
				if (electricite_jour_EditText.getText().length() > 0)
					eVal = Integer.parseInt(electricite_jour_EditText.getText().toString());
				if (electricite_nuit_EditText.getText().length() > 0)
					eval1 = Integer.parseInt(electricite_nuit_EditText.getText().toString());
				compteurElectricite.setIndex_1Compteurs(eVal);
				compteurElectricite.setIndex_2Compteurs(eval1);
				compteurElectricite.setTypeCompteurs(1);
				compteurElectricite.setIdMission(idMission);
				compteurElectricite.setIdEtat(etat.get(electricite_spinner.getSelectedItemPosition()).getIdEtat());

				compteurs_DAO.addValue(compteurElectricite);
			} else {
				compteurElectricite.setIdEtat(etat.get(electricite_spinner.getSelectedItemPosition()).getIdEtat());
				compteurElectricite
						.setIndex_1Compteurs(Integer.parseInt(electricite_jour_EditText.getText().toString()));
				compteurElectricite
						.setIndex_2Compteurs(Integer.parseInt(electricite_nuit_EditText.getText().toString()));
				compteurs_DAO.updateValue(compteurElectricite);
			}

			if (isFirstTime) {
				int eVal = 0, eval1 = 0;
				if (eau1_chaud_EditText.getText().length() > 0)
					eVal = Integer.parseInt(eau1_chaud_EditText.getText().toString());
				if (eau1_froid_EditText.getText().length() > 0)
					eval1 = Integer.parseInt(eau1_froid_EditText.getText().toString());
				compteurEau1.setIndex_1Compteurs(eVal);
				compteurEau1.setIndex_2Compteurs(eval1);
				compteurEau1.setTypeCompteurs(0);
				compteurEau1.setIdMission(idMission);
				compteurEau1.setIdEtat(etat.get(eau1_spinner.getSelectedItemPosition()).getIdEtat());

				compteurs_DAO.addValue(compteurEau1);
			} else {
				compteurEau1.setIdEtat(etat.get(eau1_spinner.getSelectedItemPosition()).getIdEtat());
				compteurEau1.setIndex_1Compteurs(Integer.parseInt(eau1_chaud_EditText.getText().toString()));
				compteurEau1.setIndex_2Compteurs(Integer.parseInt(eau1_froid_EditText.getText().toString()));
				compteurs_DAO.updateValue(compteurEau1);
			}
			if (isFirstTime) {
				int eVal = 0, eval1 = 0;
				if (eau2_froid_EditText.getText().length() > 0)
					eVal = Integer.parseInt(eau2_chaud_EditText.getText().toString());
				if (eau2_chaud_EditText.getText().length() > 0)
					eval1 = Integer.parseInt(eau2_froid_EditText.getText().toString());
				compteurEau2.setIndex_1Compteurs(eVal);
				compteurEau2.setIndex_2Compteurs(eval1);
				compteurEau2.setTypeCompteurs(4);
				compteurEau2.setIdMission(idMission);
				compteurEau2.setIdEtat(etat.get(eau2_spinner.getSelectedItemPosition()).getIdEtat());

				compteurs_DAO.addValue(compteurEau2);
			} else {
				compteurEau2.setIdEtat(etat.get(eau2_spinner.getSelectedItemPosition()).getIdEtat());
				compteurEau2.setIndex_1Compteurs(Integer.parseInt(eau2_chaud_EditText.getText().toString()));
				compteurEau2.setIndex_2Compteurs(Integer.parseInt(eau2_froid_EditText.getText().toString()));
				compteurs_DAO.updateValue(compteurEau2);
			}
			if (isFirstTime) {
				int eVal = 0;
				if (gaz_froid_EditText.getText().length() > 0)
					eVal = Integer.parseInt(gaz_froid_EditText.getText().toString());
				compteurGaz.setIndex_1Compteurs(eVal);
				compteurGaz.setIndex_2Compteurs(0);
				compteurGaz.setTypeCompteurs(2);
				compteurGaz.setIdMission(idMission);
				compteurGaz.setIdEtat(etat.get(gaz_Spinner.getSelectedItemPosition()).getIdEtat());

				compteurs_DAO.addValue(compteurGaz);
			} else {
				compteurGaz.setIdEtat(etat.get(gaz_Spinner.getSelectedItemPosition()).getIdEtat());
				compteurGaz.setIndex_1Compteurs(Integer.parseInt(gaz_froid_EditText.getText().toString()));
				compteurGaz.setIndex_2Compteurs(0);
				compteurs_DAO.updateValue(compteurGaz);
			}

			if (isFirstTime) {
				int val = 0;
				if (spinner_detecteur.getSelectedItemPosition() == 1)
					val = 1;
				int eVal = 0;
				if (present_froid_EditText.getText().length() > 0)
					eVal = Integer.parseInt(present_froid_EditText.getText().toString());
				compteurDetecteur = new CEL_Compteurs(val, eVal, 3, idMission, 4);
				compteurs_DAO.addValue(compteurDetecteur);
			} else {
				if (spinner_detecteur.getSelectedItemPosition() == 1)
					compteurDetecteur.setIndex_1Compteurs(1);
				else
					compteurDetecteur.setIndex_1Compteurs(0);
				compteurDetecteur.setTypeCompteurs(3);
				compteurDetecteur.setIndex_2Compteurs(Integer.parseInt(present_froid_EditText.getText().toString()));
				compteurs_DAO.updateValue(compteurDetecteur);
			}

			compteurs_DAO.close();

			chauffage_DAO = new CEL_Chauffage_DAO(mContext);
			// We do the same for CEL_Chauffage
			if (chauffage.getIdChauffage() == 0) {
				chauffage = new CEL_Chauffage();
				chauffage.setEntretienChauffage(dateEntretien_EditText.getText().toString());
				chauffage.setHorsService(horsService_checkBox.isSelected());
				chauffage.setChaudiereChauffage(chaudiereChauffage_spinner.getSelectedItemPosition());
				chauffage.setTypeChauffage(typeChauffage_spinner.getSelectedItemPosition());
				chauffage.setMarqueChauffage(marqueChauffage_spinner.getSelectedItemPosition());
				MissionsList.missionSelected.setIdChauffage(chauffage_DAO.addValue(chauffage));
				CEL_Mission_DAO mission_DAO = new CEL_Mission_DAO(mContext);
				mission_DAO.updateValue(MissionsList.missionSelected);
			} else {
				chauffage.setChaudiereChauffage(chaudiereChauffage_spinner.getSelectedItemPosition());
				chauffage.setTypeChauffage(typeChauffage_spinner.getSelectedItemPosition());
				chauffage.setMarqueChauffage(marqueChauffage_spinner.getSelectedItemPosition());
				chauffage.setEntretienChauffage(dateEntretien_EditText.getText().toString());
				chauffage.setHorsService(horsService_checkBox.isChecked());
				chauffage_DAO.updateValue(chauffage);
			}
			photo_compteur_DAO.close();


            ecs_DAO = new CEL_ECS_DAO(mContext);
            if(ecs == null) {
                ecs = new CEL_ECS();
                ecs.setMarqueEcs(spinner_eau_chaude_marque.getSelectedItemPosition());
                ecs.setTypeEcs(spinner_eau_chaude_type.getSelectedItemPosition());
                MissionsList.missionSelected.setIdECS(ecs_DAO.addValue(ecs));
                CEL_Mission_DAO mission_DAO = new CEL_Mission_DAO(mContext);
                mission_DAO.updateValue(MissionsList.missionSelected);
            }
            else {
                ecs.setMarqueEcs(spinner_eau_chaude_marque.getSelectedItemPosition());
                ecs.setTypeEcs(spinner_eau_chaude_type.getSelectedItemPosition());
                ecs_DAO.updateValue(ecs);
            }


		} catch (Exception e) {
			e.printStackTrace();
		}

		MissionsList.missionSelected
				.setFournisseurElectriviteMission(fournisseur_eletricite_EditText.getText().toString());
        if(!present_froid_EditText.getText().toString().isEmpty()) {
            MissionsList.missionSelected.setDetecteurFumeeMission(Integer.parseInt(present_froid_EditText.getText().toString()));
        }
        else
            MissionsList.missionSelected.setDetecteurFumeeMission(0);

		CEL_Mission_DAO mission_DAO = new CEL_Mission_DAO(mContext);
		mission_DAO.updateValue(MissionsList.missionSelected);
		mission_DAO.close();
	}

	@Override
	public void onPause() {
		super.onPause();
		chauffage_DAO = new CEL_Chauffage_DAO(mContext);
		compteurs_DAO = new CEL_Compteurs_DAO(mContext);
		chauffage_DAO.close();
		compteurs_DAO.close();
	}

	public static boolean saveCompteurData() {
		compteurs_DAO = new CEL_Compteurs_DAO(mContext);
		insertData();
		chauffage_DAO.close();
		return true;
	}

	private void initializeViews() {
		compteursList = new ArrayList<>();
		compteurs_DAO = new CEL_Compteurs_DAO(mContext);
		compteursList = compteurs_DAO.selectAllCEL_Compteurs(idMission);
		compteurs_DAO.close();

		// We initialize compteurs items
		if (compteursList.size() == 0) {
			Toast.makeText(mContext, getString(R.string.computer_error1), Toast.LENGTH_SHORT).show();
			isFirstTime = true;
		} else {
			isFirstTime = false;
			photo_compteur_DAO = new OPERA_Photos_DAO(mContext);
			for (CEL_Compteurs compteurs : compteursList) {
				try {
					photo_compteur = photo_compteur_DAO.select(compteurs.getIdPhoto());
					if (compteurs.getTypeCompteurs() == 3) {
						compteurDetecteur = new CEL_Compteurs(compteurs);
						if (compteurs.getIndex_1Compteurs() >= 0) {
							if (compteurs.getIndex_1Compteurs() == 0) {
								spinner_detecteur.setSelection(2);
							} else {
								spinner_detecteur.setSelection(1);
							}
						} else {
							spinner_detecteur.setSelection(0);
						}
						present_froid_EditText.setText(String.valueOf(compteurs.getIndex_2Compteurs()));
					} else if (compteurs.getTypeCompteurs() == 1) {
						compteurElectricite = compteurs;
						electricite_jour_EditText.setText(String.valueOf(compteurElectricite.getIndex_1Compteurs()));
						electricite_nuit_EditText.setText(String.valueOf(compteurElectricite.getIndex_2Compteurs()));
						System.out.println("===Ele=" + compteurElectricite.getIdEtat());
						int selpos = 0;
						if (etat.size() >= 4)
							selpos = 3;
						for (int k = 0; k < etat.size(); k++) {
							if (compteurElectricite.getIdEtat() == etat.get(k).getIdEtat()) {
								selpos = k;
								break;
							}
						}
						electricite_spinner.setSelection(selpos);
						try {
							if (photo_compteur != null && photo_compteur.getPhoto() != null) {
								camera_img_electric.setImageBitmap(BitmapFactory.decodeByteArray(
										photo_compteur.getPhoto(), 0, photo_compteur.getPhoto().length));
								camera_img_electric.getLayoutParams().height = height;
								camera_img_electric.getLayoutParams().width = width;
								camera_img_electric.setTag("true");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (compteurs.getTypeCompteurs() == 0) {
						compteurEau1 = compteurs;
						eau1_chaud_EditText.setText(String.valueOf(compteurEau1.getIndex_1Compteurs()));
						eau1_froid_EditText.setText(String.valueOf(compteurEau1.getIndex_2Compteurs()));
						System.out.println("===Eau1=" + compteurEau1.getIdEtat());

						int selpos = 0;
						if (etat.size() >= 4)
							selpos = 3;
						for (int k = 0; k < etat.size(); k++) {
							if (compteurEau1.getIdEtat() == etat.get(k).getIdEtat()) {
								selpos = k;
								break;
							}
						}
						eau1_spinner.setSelection(selpos);
						try {
							if (photo_compteur != null && photo_compteur.getPhoto() != null) {
								camera_img_eau1.setImageBitmap(BitmapFactory.decodeByteArray(photo_compteur.getPhoto(),
										0, photo_compteur.getPhoto().length));
								camera_img_eau1.getLayoutParams().height = height;
								camera_img_eau1.getLayoutParams().width = width;
								camera_img_eau1.setTag("true");
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (compteurs.getTypeCompteurs() == 4) {
						compteurEau2 = compteurs;
						eau2_chaud_EditText.setText(String.valueOf(compteurEau2.getIndex_1Compteurs()));
						eau2_froid_EditText.setText(String.valueOf(compteurEau2.getIndex_2Compteurs()));
						System.out.println("===Eau2=" + compteurEau2.getIdEtat());

						int selpos = 0;
						if (etat.size() >= 4)
							selpos = 3;
						for (int k = 0; k < etat.size(); k++) {
							if (compteurEau2.getIdEtat() == etat.get(k).getIdEtat()) {
								selpos = k;
								break;
							}
						}
						eau2_spinner.setSelection(selpos);
						try {
							if (photo_compteur != null && photo_compteur.getPhoto() != null) {
								camera_img_eau2.setImageBitmap(BitmapFactory.decodeByteArray(photo_compteur.getPhoto(),
										0, photo_compteur.getPhoto().length));
								camera_img_eau2.getLayoutParams().height = height;
								camera_img_eau2.getLayoutParams().width = width;
								camera_img_eau2.setTag("true");
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {

						compteurGaz = compteurs;
						System.out.println("===Gaz=" + compteurGaz.getIdEtat());

						int selpos = 0;
						if (etat.size() >= 4)
							selpos = 3;
						for (int k = 0; k < etat.size(); k++) {
							if (compteurs.getIdEtat() == etat.get(k).getIdEtat()) {
								selpos = k;
								break;
							}
						}
						gaz_Spinner.setSelection(selpos);
						gaz_froid_EditText.setText(String.valueOf(compteurGaz.getIndex_1Compteurs()));
						try {
							if (photo_compteur != null && photo_compteur.getPhoto() != null) {
								camera_img_eau3.setImageBitmap(BitmapFactory.decodeByteArray(photo_compteur.getPhoto(),
										0, photo_compteur.getPhoto().length));
								camera_img_eau3.getLayoutParams().height = height;
								camera_img_eau3.getLayoutParams().width = width;
								camera_img_eau3.setTag("true");
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			photo_compteur_DAO.close();
		}

		chauffage_DAO = new CEL_Chauffage_DAO(mContext);
		// We initialize chauffage item
		if (chauffage_DAO.select(MissionsList.missionSelected.getIdChauffage()) == null) {
			Toast.makeText(mContext, getString(R.string.computer_error1), Toast.LENGTH_SHORT).show();
		} else {
			try {
				chauffage = chauffage_DAO.select(MissionsList.missionSelected.getIdChauffage());
				typeChauffage_spinner.setSelection(chauffage.getTypeChauffage());
				marqueChauffage_spinner.setSelection(chauffage.getMarqueChauffage());
				chaudiereChauffage_spinner.setSelection(chauffage.getChaudiereChauffage());
				dateEntretien_EditText.setText(chauffage.getEntretienChauffage());
				horsService_checkBox.setChecked(chauffage.getHorsService());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		chauffage_DAO.close();

		if (MissionsList.missionSelected.getFournisseurElectriviteMission() != null
				&& !MissionsList.missionSelected.getFournisseurElectriviteMission().equalsIgnoreCase("null"))
			fournisseur_eletricite_EditText.setText(MissionsList.missionSelected.getFournisseurElectriviteMission());

		ecs_DAO = new CEL_ECS_DAO(mContext);
        ecs = ecs_DAO.select(MissionsList.missionSelected.getIdECS());
        ecs_DAO.close();
        if(ecs != null) {
            spinner_eau_chaude_marque.setSelection(ecs.getMarqueEcs());
            spinner_eau_chaude_type.setSelection(ecs.getTypeEcs());
        }
	}

    public void hasToHideCertificat(boolean isNeeded) {


        if (isNeeded && !spinnerHide && checkboxHide) {
            dateEntretien_EditText.setEnabled(false);
            dateEntretien_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));

            marqueChauffage_spinner.setEnabled(true);

            chaudiereChauffage_spinner.setEnabled(true);
        }
        else if(isNeeded || spinnerHide || checkboxHide) {
            dateEntretien_EditText.setEnabled(false);
            dateEntretien_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));

            marqueChauffage_spinner.setEnabled(false);

            chaudiereChauffage_spinner.setEnabled(false);

        } else {
            dateEntretien_EditText.setEnabled(true);
            dateEntretien_EditText.setBackgroundResource(android.R.drawable.edit_text);

            marqueChauffage_spinner.setEnabled(true);

            chaudiereChauffage_spinner.setEnabled(true);
        }

    }

	public void callTakePicture() {
		try {
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, EtatDesLieux.pictureFromCamera);
			EtatDesLieux.mImageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, EtatDesLieux.mImageUri);
			startActivityForResult(cameraIntent, EtatDesLieux.REQUEST_CAMERA);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case EtatDesLieux.REQUEST_CAMERA:
			if (resultCode == RESULT_OK) {
				photo_compteur_DAO = new OPERA_Photos_DAO(mContext);

				camera_img.getLayoutParams().height = height;
				camera_img.getLayoutParams().width = width;
                camera_img.setTag("true");

				try {
					saveToInternalStorage(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), EtatDesLieux.mImageUri));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	private void saveToInternalStorage(Bitmap bitmapImage){

		imageBitmapCompteurs = bitmapImage;

		File imageToUpload = new File(getActivity().getFilesDir(), "imageName.png");

		FileOutputStream outputStream;

		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		if(BitmapCompat.getAllocationByteCount(imageBitmapCompteurs) > 2000000) {

			do{
				imageBitmapCompteurs = Bitmap.createScaledBitmap(imageBitmapCompteurs,(int)(imageBitmapCompteurs.getWidth()*0.9), (int)(imageBitmapCompteurs.getHeight()*0.9), true);
			}
			while(BitmapCompat.getAllocationByteCount(imageBitmapCompteurs) > 2000000);
		}

		try {
			outputStream = getActivity().openFileOutput(imageToUpload.getName(), Context.MODE_PRIVATE);
			outputStream.write(stream.toByteArray());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        camera_img.setImageBitmap(imageBitmapCompteurs);
        saveCompeursPhoto();

	}

	public static void saveCompeursPhoto() {

		try {
			if(camera_img.getTag().toString().equalsIgnoreCase("true")) {

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				imageBitmapCompteurs.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();

                String photo_name = "";
                boolean toUpdate = false;

                switch (typeCompteur) {

                    case 1: // Compteur elec
                        if (compteurEau1.getIdPhoto() > 0) {
                            photo_compteur = photo_compteur_DAO.select(compteurEau1.getIdPhoto());
                            toUpdate = true;
                        } else {
                            photo_name = MissionsList.missionSelected.getNumeroRdvMission() + " - app -compteur_eau1";
                            photo_compteur = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
                            int idPhoto = photo_compteur_DAO.addValue(photo_compteur);
                            compteurEau1.setIdPhoto(idPhoto);
                        }
                        break;

                    case 2: // Compteur eau 1
                        if (compteurElectricite.getIdPhoto() > 0) {
                            photo_compteur = photo_compteur_DAO.select(compteurElectricite.getIdPhoto());
                            toUpdate = true;
                        } else {
                            photo_name = MissionsList.missionSelected.getNumeroRdvMission() + " - app -compteur_electricite";
                            photo_compteur = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
                            int idPhoto = photo_compteur_DAO.addValue(photo_compteur);
                            compteurElectricite.setIdPhoto(idPhoto);
                        }
                        break;

                    case 3: // Compteur eau 2
                        if (compteurEau2.getIdPhoto() > 0) {
                            photo_compteur = photo_compteur_DAO.select(compteurEau2.getIdPhoto());
                            toUpdate = true;
                        } else {
                            photo_name = MissionsList.missionSelected.getNumeroRdvMission() + " - app -compteur_eau_2";
                            photo_compteur = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
                            int idPhoto = photo_compteur_DAO.addValue(photo_compteur);
                            compteurEau2.setIdPhoto(idPhoto);
                        }
                        break;

                    case 4: // Compteur gaz
                        if (compteurGaz.getIdPhoto() > 0) {
                            photo_compteur = photo_compteur_DAO.select(compteurGaz.getIdPhoto());
                            toUpdate = true;
                        } else {
                            photo_name = MissionsList.missionSelected.getNumeroRdvMission() + " - app -compteur_gaz";
                            photo_compteur = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
                            int idPhoto = photo_compteur_DAO.addValue(photo_compteur);
                            compteurGaz.setIdPhoto(idPhoto);
                        }
                        break;
                }
                if (photo_compteur != null && toUpdate) {
                    photo_compteur.setPhoto(byteArray);
                    photo_compteur.setIdMission(idMission);
                    photo_compteur_DAO.updateValue(photo_compteur);
                }
                photo_compteur_DAO.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
