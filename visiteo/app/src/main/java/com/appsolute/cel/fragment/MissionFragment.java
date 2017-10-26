package com.appsolute.cel.fragment;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.BaseFragment;
import com.appsolute.cel.DAO.CEL_Biens_DAO;
import com.appsolute.cel.DAO.CEL_Chauffage_DAO;
import com.appsolute.cel.DAO.CEL_Clefs_DAO;
import com.appsolute.cel.DAO.CEL_Compteurs_DAO;
import com.appsolute.cel.DAO.CEL_ECS_DAO;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.DAO.CEL_Mission_Personnes_DAO;
import com.appsolute.cel.DAO.CEL_Personnes_DAO;
import com.appsolute.cel.DAO.CEL_Piece_DAO;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.activity.EtatDesLieux;
import com.appsolute.cel.activity.MissionsList;
import com.appsolute.cel.activity.ModifyMissionActivity;
import com.appsolute.cel.activity.VoirMissions;
import com.appsolute.cel.models.CEL_Biens;
import com.appsolute.cel.models.CEL_Clefs;
import com.appsolute.cel.models.CEL_Compteurs;
import com.appsolute.cel.models.CEL_Mission;
import com.appsolute.cel.models.CEL_Personnes;
import com.appsolute.cel.models.CEL_Piece;
import com.appsolute.cel.models.OPERA_Photos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MissionFragment extends BaseFragment {

	static final int SIGNATURE_ACTIVITY = 111, REQUEST_VOIR=114;
	private ImageView mission_img;
	public TextView voirmission, modifiermission, locataire_sortant, terminerMission_TextView, 
	typeBienMission_TextView, numeroRDV_TextView, adresseBienMission_TextView,
	villeBienMission_TextView, locataire_entrant, type_edl_TextView;
	private EditText prenomLocataire_EditText, nomLocataire_EditText, 
	addresseLocataire_EditText, cpLocataire_EditText,
	villeLocataire_EditText, emailLocataire_EditText,
	representant_EditText, telephoneLocataire_EditText, dateEntree_EditText;
	private Button modifier_sortant, modifier_entrant;
	private CEL_Biens_DAO biens_DAO;
	private CEL_Mission_Personnes_DAO mission_Personnes_DAO;
	private CEL_Personnes_DAO personnes_DAO;
	private CEL_Mission_DAO mission_DAO;
	private CEL_Biens biens;
	private CEL_Personnes personnes;
	public static CEL_Mission mission;
	private LinearLayout main_cell_locataire_sortant, main_cell_locataire_entrant;
	private OPERA_Photos_DAO photo_mission_DAO;
	private OPERA_Photos photo_mission;
    private static Bitmap imageBitmapFacade;
    private CheckBox checkBoxEmail, isAddressInconnue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View layout = inflater.inflate(R.layout.fragment_mission, container, false);

		init();        
		mission_img = (ImageView)layout.findViewById(R.id.mission_img);
		voirmission = (TextView)layout.findViewById(R.id.voirmission);
		modifiermission = (TextView)layout.findViewById(R.id.modifiermission);
		modifier_sortant = (Button)layout.findViewById(R.id.modifier_sortant);
		modifier_entrant = (Button)layout.findViewById(R.id.modifier_entrant);
		terminerMission_TextView = (TextView)layout.findViewById(R.id.terminerMission_TextView);
		locataire_entrant = (TextView)layout.findViewById(R.id.locataire_entrant);
		locataire_sortant = (TextView)layout.findViewById(R.id.locataire_sortant);
		typeBienMission_TextView = (TextView)layout.findViewById(R.id.typeBienMission_TextView);
		numeroRDV_TextView = (TextView)layout.findViewById(R.id.numeroRDV_TextView); 
		adresseBienMission_TextView = (TextView)layout.findViewById(R.id.adresseBienMission_TextView);
		villeBienMission_TextView = (TextView)layout.findViewById(R.id.villeBienMission_TextView);
		type_edl_TextView =  (TextView)layout.findViewById(R.id.type_edl_TextView);
		main_cell_locataire_sortant = (LinearLayout)layout.findViewById(R.id.main_cell_locataire_sortant);
		main_cell_locataire_entrant = (LinearLayout)layout.findViewById(R.id.main_cell_locataire_entrant);
		mission_img.setTag("false");

		initializeTextView();

		terminerMission_TextView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finishMission();
			}
		}); 
		mission_img.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				callCameraAction();
			}
		});
		voirmission.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				callVoirMission();
			}
		});
		modifiermission.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				callModifierMission();
			}
		});
		modifier_entrant.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				callModifier(true);
			}
		});
		modifier_sortant.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				callModifier(false);
			}
		});
		return layout;
	}

	public void initializeTextView() {		
		try {

			switch (MissionsList.missionSelected.getType_edl()) {
			case 0:
				main_cell_locataire_sortant.setVisibility(View.GONE);
				main_cell_locataire_entrant.setVisibility(View.VISIBLE);
				type_edl_TextView.setText(getString(R.string.edl_entree));
				break;
			case 1:
				main_cell_locataire_sortant.setVisibility(View.VISIBLE);
				main_cell_locataire_entrant.setVisibility(View.GONE);
				type_edl_TextView.setText(getString(R.string.edl_sortie));
				break;
			case 2:
				main_cell_locataire_sortant.setVisibility(View.VISIBLE);
				main_cell_locataire_entrant.setVisibility(View.VISIBLE);
				type_edl_TextView.setText(getString(R.string.edl_sortie_entree));
				break;
			case 3:
				main_cell_locataire_sortant.setVisibility(View.VISIBLE);
				main_cell_locataire_entrant.setVisibility(View.VISIBLE);
				type_edl_TextView.setText(getString(R.string.om_chk3));
				break;
			default:
				main_cell_locataire_sortant.setVisibility(View.VISIBLE);
				main_cell_locataire_entrant.setVisibility(View.VISIBLE);
				type_edl_TextView.setText(getString(R.string.edl_entree));
				break;
			}

			biens_DAO = new CEL_Biens_DAO(mContext);
			biens = biens_DAO.select(MissionsList.missionSelected.getIdBien());
			biens_DAO.close();

			String[] type = getResources().getStringArray(R.array.spinnerItemsTypeBien); 
			typeBienMission_TextView.setText(type[biens.getTypeBiens()]);
			EtatDesLieux.typeBien = biens.getTypeBiens();

			numeroRDV_TextView.setText("Mission "+Integer.toString(MissionsList.missionSelected.getNumeroRdvMission())); 
			adresseBienMission_TextView.setText(biens.getAdresseBiens());
			villeBienMission_TextView.setText(biens.getCodePostalBiens()+" "+biens.getVilleBiens());
			
			photo_mission_DAO = new OPERA_Photos_DAO(mContext);
			photo_mission = photo_mission_DAO.select(MissionsList.missionSelected.getIdMissionPhotoFacade());
			photo_mission_DAO.close();

			if(MissionsList.missionSelected.getEntrant() != null)
				locataire_entrant.setText(MissionsList.missionSelected.getEntrant());
			if(MissionsList.missionSelected.getSortant() != null)
				locataire_sortant.setText(MissionsList.missionSelected.getSortant());
			if(photo_mission != null && photo_mission.getPhoto() != null) {
				mission_img.setImageBitmap((BitmapFactory.decodeByteArray(photo_mission.getPhoto(), 0, photo_mission.getPhoto().length)));
				BitmapDrawable bd=(BitmapDrawable) ContextCompat.getDrawable(mContext, R.drawable.entree_img);
				mission_img.getLayoutParams().height = bd.getBitmap().getHeight()+10;
				mission_img.getLayoutParams().width = bd.getBitmap().getWidth()+10;
				mission_img.setTag("true");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void initializePersonne(Boolean isEntrant) {
		mission_Personnes_DAO = new CEL_Mission_Personnes_DAO(mContext);
		personnes = mission_Personnes_DAO.selectTypePersonne(MissionsList.missionSelected.getIdMission(), isEntrant);
		mission_Personnes_DAO.close();
	}

	private void initializePersonneDialog(Boolean isEntrant) {
		if(personnes != null) {
			prenomLocataire_EditText.setText(personnes.getPrenomPersonnes());
			nomLocataire_EditText.setText(personnes.getNomPersonnes());
			addresseLocataire_EditText.setText(personnes.getAdressePersonnes());
			cpLocataire_EditText.setText(personnes.getCodePostalPersonnes());
			villeLocataire_EditText.setText(personnes.getVillePersonnes());
			emailLocataire_EditText.setText(personnes.getEmailPersonnes());
			representant_EditText.setText(personnes.getRepresentantPersonnes());
            telephoneLocataire_EditText.setText(personnes.getTelephonePersonnes());
            dateEntree_EditText.setText(personnes.getDateEntree());
		}
		else {
			if(!isEntrant) {
				if(MissionsList.missionSelected.getSortant() != null && 
						!MissionsList.missionSelected.getSortant().equals("")) {
					nomLocataire_EditText.setText(MissionsList.missionSelected.getSortant());
					telephoneLocataire_EditText.setText(MissionsList.missionSelected.getTel_Sortant());
                    dateEntree_EditText.setText(MissionsList.missionSelected.getDateEntreeMission());

					if(MissionsList.missionSelected.getMail_Sortant() != null) {
                        emailLocataire_EditText.setText(MissionsList.missionSelected.getMail_Sortant());
                        checkBoxEmail.setChecked(false);
                    }
				}
			}
			else {
				if(MissionsList.missionSelected.getEntrant() != null && 
						!MissionsList.missionSelected.getEntrant().equals("")) {
					nomLocataire_EditText.setText(MissionsList.missionSelected.getEntrant());
					telephoneLocataire_EditText.setText(MissionsList.missionSelected.getTel_Entrant());

					if(MissionsList.missionSelected.getMail_Entrant() != null) {
                        emailLocataire_EditText.setText(MissionsList.missionSelected.getMail_Entrant());
                        checkBoxEmail.setChecked(false);
                    }
				}
			}
		}
	}

	public void callVoirMission() {
		Intent i = new Intent(getActivity(), VoirMissions.class);
		startActivityForResult(i, REQUEST_VOIR);
	}

	public void callModifierMission() {
		getActivity().finish();
		Intent i = new Intent(getActivity(), ModifyMissionActivity.class);
		i.putExtra("addMission", false);
		ModifyMissionActivity.isFromMissionList = false;
		startActivityForResult(i, REQUEST_VOIR);
	}

	public void callCameraAction() {
		try {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, EtatDesLieux.pictureFromCamera);
            EtatDesLieux.mImageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, EtatDesLieux.mImageUri);
            startActivityForResult(cameraIntent, EtatDesLieux.REQUEST_CAMERA);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Call an alert dialog to update all informations 
	 * about a CEL_Personne
	 * 
	 * @param isEntrant If the CEL_Personne to update is "entrant", if not it will be a "sortant"
	 */
	public void callModifier(final Boolean isEntrant) {
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.dialog_nouvelles);
		dialog.setTitle(getString(R.string.nouvelles_coordinates));
		dialog.setCancelable(false);


		prenomLocataire_EditText = (EditText)dialog.findViewById(R.id.prenomLocataire_EditText);
		nomLocataire_EditText = (EditText)dialog.findViewById(R.id.nomLocataire_EditText);
		addresseLocataire_EditText = (EditText)dialog.findViewById(R.id.addresseLocataire_EditText);
		cpLocataire_EditText = (EditText)dialog.findViewById(R.id.cpLocataire_EditText);
		villeLocataire_EditText = (EditText)dialog.findViewById(R.id.villeLocataire_EditText);
		emailLocataire_EditText = (EditText)dialog.findViewById(R.id.emailLocataire_EditText);
		representant_EditText = (EditText)dialog.findViewById(R.id.representant_EditText);
		telephoneLocataire_EditText = (EditText)dialog.findViewById(R.id.telephoneLocataire_EditText);
		dateEntree_EditText = (EditText)dialog.findViewById(R.id.dateEntree_EditText);
		isAddressInconnue = (CheckBox)dialog.findViewById(R.id.inconnue);

		if(isEntrant)
			dateEntree_EditText.setVisibility(View.GONE);
		else
			dateEntree_EditText.setVisibility(View.VISIBLE);

		TextView cancelbt = (TextView)dialog.findViewById(R.id.cancelbt);
		TextView okbt = (TextView)dialog.findViewById(R.id.okbt);
		CheckBox representeCheckBox = (CheckBox)dialog.findViewById(R.id.representeCheckBox);
		representeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					representant_EditText.setEnabled(true);
					representant_EditText.setBackgroundResource(android.R.drawable.edit_text);
				}
				else {
					representant_EditText.setEnabled(false);
					representant_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					representant_EditText.setText("");
				}
			}
		});
        checkBoxEmail = (CheckBox)dialog.findViewById(R.id.checkBoxEmail);
		checkBoxEmail.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked) {
					emailLocataire_EditText.setEnabled(true);
					emailLocataire_EditText.setBackgroundResource(android.R.drawable.edit_text);
				}
				else {
					emailLocataire_EditText.setEnabled(false);
					emailLocataire_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
					emailLocataire_EditText.getText().clear();
				}
			}
		});

		initializePersonne(isEntrant);
		initializePersonneDialog(isEntrant);
		if(!representant_EditText.getText().toString().equals("")) {
			representeCheckBox.setChecked(true);
			representant_EditText.setEnabled(true);
			representant_EditText.setBackgroundResource(android.R.drawable.edit_text);
		}
		else {
			representeCheckBox.setChecked(false);
			representant_EditText.setEnabled(false);
			representant_EditText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_gray));
		}

		cancelbt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		okbt.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View view) {
				String pname = prenomLocataire_EditText.getText().toString();
				String name = nomLocataire_EditText.getText().toString();
				String adresse = addresseLocataire_EditText.getText().toString();
				String cp = cpLocataire_EditText.getText().toString();
				String ville = villeLocataire_EditText.getText().toString();
				String email = emailLocataire_EditText.getText().toString();
				String representant = representant_EditText.getText().toString();
				String telephone = telephoneLocataire_EditText.getText().toString();
                String dateEntree = dateEntree_EditText.getText().toString();

				/*if(!cp.isEmpty() && cp.length() != 5) {
					Toast.makeText(mContext, mContext.getString(R.string.error_cp), Toast.LENGTH_SHORT).show();
					return;
				}

                if(!isAddressInconnue.isChecked() && !isEntrant && adresse.isEmpty()) {
                    Toast.makeText(mContext, mContext.getString(R.string.error_address), Toast.LENGTH_SHORT).show();
                    return;
                }*/

				if (personnes == null)
					personnes = new CEL_Personnes();

				personnes.setPrenomPersonnes(pname);
				personnes.setNomPersonnes(name);
				personnes.setAdressePersonnes(adresse);
				personnes.setCodePostalPersonnes(cp);
				personnes.setVillePersonnes(ville);
				personnes.setEmailPersonnes(email);
				personnes.setRepresentantPersonnes(representant);
				personnes.setTelephonePersonnes(telephone);
				if(!isEntrant)
					personnes.setDateEntree(dateEntree);

				personnes_DAO = new CEL_Personnes_DAO(mContext);
				if (personnes.getIdPersonnes() == 0) {
					int id_personnes = personnes_DAO.addValue(personnes);
					personnes.setIdPersonnes(id_personnes);
					int typeLocataire = 1;
					if(isEntrant) {
						typeLocataire = 1;
						MissionsList.missionSelected.setTel_Entrant(telephone);
					}
					else {
						typeLocataire = 2;
						MissionsList.missionSelected.setTel_Sortant(telephone);
					}

					mission_Personnes_DAO.addValue(MissionsList.missionSelected.getIdMission(), id_personnes, typeLocataire);
				} else {
					personnes_DAO.updateValue(personnes);
					if(isEntrant)
						MissionsList.missionSelected.setTel_Entrant(telephone);
					else
						MissionsList.missionSelected.setTel_Sortant(telephone);


				}
                mission_DAO = new CEL_Mission_DAO(mContext);
                mission_DAO.updateValue(MissionsList.missionSelected);
                mission_DAO.close();

				personnes_DAO.close();
				dialog.dismiss();
			}
		});
		if(!getActivity().isFinishing())
			dialog.show();
		callHiddenKeyboardDialog(dialog);
	}

	public void finishMission() {
		mission_DAO = new CEL_Mission_DAO(mContext);
		mission = mission_DAO.select(MissionsList.missionSelected.getIdMission());
		mission_DAO.close();

		/*if(MissionsList.missionSelected.getRelocationMission() == null) {
			Toast.makeText(mContext, R.string.relocation_error, Toast.LENGTH_LONG).show();
			return;
		}

		if(MissionsList.missionSelected.getIdMissionPhotoFacade() == 0) {
			Toast.makeText(mContext, R.string.facade_picture_missing, Toast.LENGTH_LONG).show();
			return;
		}*/
		
		CEL_Clefs_DAO cel_Clefs_DAO = new CEL_Clefs_DAO(mContext);
		List<CEL_Clefs> clefsList = cel_Clefs_DAO.selectAllFromBiens(mission.getIdMission());
		/*if(clefsList.size() == 0) {
			Toast.makeText(mContext, R.string.clef_error, Toast.LENGTH_LONG).show();
			return;
		}*/
		cel_Clefs_DAO.close();
		CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(mContext);
		CEL_Piece_DAO cel_Piece_DAO = new CEL_Piece_DAO(mContext, MissionsList.missionSelected.getIdMission(),
				cel_biens_dao.select(MissionsList.missionSelected.getIdBien()));
		List<CEL_Piece> cel_PiecesList = cel_Piece_DAO.selectAllPieceWithIdMission(mission.getIdMission());
		for(CEL_Piece cel_Piece : cel_PiecesList) {
			if(cel_Piece.getIsFinish() != 1) {
				Toast.makeText(mContext, R.string.piece_not_finish, Toast.LENGTH_LONG).show();
				return;
			}
		}
		
		CEL_Compteurs_DAO cel_Compteurs_DAO = new CEL_Compteurs_DAO(mContext);
		List<CEL_Compteurs> cel_Compteurs_List = cel_Compteurs_DAO.selectAllCEL_Compteurs(mission.getIdMission());
		/*if(cel_Compteurs_List.size() == 0) {
			Toast.makeText(mContext, R.string.compteur_empty, Toast.LENGTH_LONG).show();
			return;
		}*/
		/*for (CEL_Compteurs cel_Compteurs : cel_Compteurs_List) {
			if((cel_Compteurs.getIdEtat() == 1 || cel_Compteurs.getIdEtat() == 6 || cel_Compteurs.getIdEtat() == 8)
					&& (cel_Compteurs.getIndex_1Compteurs() == 0 && cel_Compteurs.getIndex_2Compteurs() == 0)) {
				Toast.makeText(mContext, R.string.compteur_error, Toast.LENGTH_LONG).show();
				return;
			}
			if(cel_Compteurs.getTypeCompteurs() == 3 && cel_Compteurs.getIndex_2Compteurs() == 0 && cel_Compteurs.getIndex_1Compteurs() == 1) {
				Toast.makeText(mContext, R.string.detecteur_empty, Toast.LENGTH_LONG).show();
				return;
			}
		}*/
		
		CEL_Chauffage_DAO cel_Chauffage_DAO = new CEL_Chauffage_DAO(mContext);
		/*if (cel_Chauffage_DAO.select(mission.getIdChauffage()) == null ) {
			Toast.makeText(mContext, getString(R.string.chauffage_missing), Toast.LENGTH_SHORT).show();
            return;
        }
		else if((cel_Chauffage_DAO.select(mission.getIdChauffage()).getTypeChauffage() != 6
                && cel_Chauffage_DAO.select(mission.getIdChauffage()).getTypeChauffage() != 0)
				&&
                ((cel_Chauffage_DAO.select(mission.getIdChauffage()).getEntretienChauffage() == null
				|| cel_Chauffage_DAO.select(mission.getIdChauffage()).getEntretienChauffage().equals(""))
				&& !cel_Chauffage_DAO.select(mission.getIdChauffage()).getHorsService())) {
			Toast.makeText(mContext, getString(R.string.chauffage_date_missing), Toast.LENGTH_SHORT).show();
			return;
		}*/

		CEL_ECS_DAO cel_ecs_dao = new CEL_ECS_DAO(mContext);
		/*if (cel_ecs_dao.select(mission.getIdECS()) == null) {
            Toast.makeText(mContext, getString(R.string.ecs_missing), Toast.LENGTH_SHORT).show();
            return;
        }
        else if(cel_ecs_dao.select(mission.getIdECS()).getMarqueEcs() == 0 ||
                cel_ecs_dao.select(mission.getIdECS()).getTypeEcs() == 0) {
            Toast.makeText(mContext, getString(R.string.ecs_empty), Toast.LENGTH_SHORT).show();
            return;
        }*/

        CEL_Personnes_DAO cel_personnes_dao = new CEL_Personnes_DAO(mContext);
        /*if(cel_personnes_dao.selectPersonnes(mission.getIdMission()).isEmpty()) {
            Toast.makeText(mContext, getString(R.string.personnes_missing), Toast.LENGTH_SHORT).show();
            return;
        }*/

		Intent i = new Intent();
		if(mission!=null) {

			mission.setEtatMission(2);
			mission.setEDL_isFinished(1);
			mission_DAO = new CEL_Mission_DAO(mContext);
			mission_DAO.updateValue(mission);
			mission_DAO.close();

			getActivity().setResult(RESULT_OK, i);
		} else
			getActivity().setResult(RESULT_CANCELED, i);
		getActivity().finish();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case SIGNATURE_ACTIVITY: 
			if (resultCode == RESULT_OK) {

				Bundle bundle = data.getExtras();
				String status  = bundle.getString("status");
				if(status.equalsIgnoreCase("done")){
					Toast toast = Toast.makeText(getActivity(), getString(R.string.sig_success), Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP, 105, 50);
					toast.show();
				}
			}
			break;
		case EtatDesLieux.REQUEST_CAMERA:
			if (resultCode == RESULT_OK) {

				BitmapDrawable bd=(BitmapDrawable) ContextCompat.getDrawable(mContext, R.drawable.entree_img);
				mission_img.getLayoutParams().height = bd.getBitmap().getHeight();
				mission_img.getLayoutParams().width = bd.getBitmap().getWidth();
				mission_img.setTag("true");

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

        imageBitmapFacade = bitmapImage;

        File imageToUpload = new File(getActivity().getFilesDir(), "imageName.png");

        FileOutputStream outputStream;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if(BitmapCompat.getAllocationByteCount(imageBitmapFacade) > 2000000) {

            do{
                imageBitmapFacade = Bitmap.createScaledBitmap(imageBitmapFacade,(int)(imageBitmapFacade.getWidth()*0.9), (int)(imageBitmapFacade.getHeight()*0.9), true);
            }
            while(BitmapCompat.getAllocationByteCount(imageBitmapFacade) > 2000000);
        }

        try {
            outputStream = getActivity().openFileOutput(imageToUpload.getName(), Context.MODE_PRIVATE);
            outputStream.write(stream.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mission_img.setImageBitmap(imageBitmapFacade);

        photo_mission_DAO = new OPERA_Photos_DAO(mContext);

        mission_DAO = new CEL_Mission_DAO(mContext);
        mission = mission_DAO.select(MissionsList.missionSelected.getIdMission());

        if(mission!=null && mission_img.getTag().equals("true")) {

            stream = new ByteArrayOutputStream();
            imageBitmapFacade.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String photo_name = MissionsList.missionSelected.getNumeroRdvMission() + " - app -photo_facade";

            if(MissionsList.missionSelected.getIdMissionPhotoFacade() >= 0) {
                photo_mission_DAO.deleteValue(MissionsList.missionSelected.getIdMissionPhotoFacade());
            }
            photo_mission = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
            int idPhotoFacade = photo_mission_DAO.addValue(photo_mission);
            mission.setIdMissionPhotoFacade(idPhotoFacade);

            mission_DAO.updateValue(mission);
            mission_DAO.close();

            MissionsList.missionSelected.setIdMissionPhotoFacade(idPhotoFacade);
            photo_mission_DAO.close();
        }

    }

	@Override
	public void onResume() {
		super.onResume();
		mission_DAO = new CEL_Mission_DAO(mContext); 
	}

	@Override
	public void onPause() {
		super.onPause();
		mission_DAO.close();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
}
