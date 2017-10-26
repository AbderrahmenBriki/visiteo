package com.appsolute.cel.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.BuildConfig;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.adapter.CEL_MissionArrayAdapter;
import com.appsolute.cel.fragment.DatePickerFragment;
import com.appsolute.cel.models.CEL_Mission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.R.id.message;
import static com.appsolute.cel.activity.PhotosActivity.context;

public class MissionsList extends BaseActivity implements DatePickerFragment.DatePickerListener {
	//Used to communicate state changes in DownloaderThread
	public static final int MESSAGE_DOWNLOAD_COMPLETE = 1001;
	public static final int MESSAGE_UPDATE_PROGRESS_BAR = 1002;
	public static final int MISSION_LIST = 121;

	static TextView date_txt, edlPrecedent_textview;
	static ListView listview;
	Calendar calendar;
	static int year, month, day;
	static TextView nodata, deleteMission, demarrerMission, quitter, 
	envoyer, lastnameExpert_TextView, nameExpert_TextView;
	static Button button_creer_mission;
	String currentdate = "";
	public static CEL_Mission missionSelected;
	public static CEL_Mission_DAO mission_DAO;
	public static ArrayList<CEL_Mission> missionsList;
	public static CEL_MissionArrayAdapter cel_MissionArrayAdapter;
	private static String date_for_request;
	public static Boolean isMissionselected = false;
	private static int IS_INDEPENDANT = 0;

    private String nomExpert;
	private String prenomExpert;

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.missions);
		baseMethod();


		// Use the current date as the default date in the picker
		calendar = Calendar.getInstance(Locale.FRANCE);
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setTitle(getString(R.string.tit_planning));
		date_txt = (TextView)findViewById(R.id.date_txt);
		listview = (ListView)findViewById(R.id.listview);
		nodata = (TextView)findViewById(R.id.nodata);
		deleteMission = (TextView)findViewById(R.id.deleteMission);
		demarrerMission = (TextView)findViewById(R.id.demarrerMission);
		quitter = (TextView)findViewById(R.id.quitter);
		envoyer = (TextView)findViewById(R.id.envoyer);
		edlPrecedent_textview = (TextView)findViewById(R.id.mis11_header_textview);
		lastnameExpert_TextView = (TextView)findViewById(R.id.lastnameExpert_TextView);
		nameExpert_TextView = (TextView)findViewById(R.id.nameExpert_TextView);
		button_creer_mission = (Button)findViewById(R.id.button_creer_mission);
		button_creer_mission.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				displayAlertDialogCreateMission();
			}
		});

		nomExpert = Login.settings.getString("nomExpert", "");
		prenomExpert = Login.settings.getString("prenomExpert", "");


		date_txt.setText(new SimpleDateFormat("EEEE dd MMMM yyyy").format(calendar.getTime()));
		currentdate = date_txt.getText().toString();

		Intent intent = getIntent();
		String date_new_mission = intent.getStringExtra(ModifyMissionActivity.EXTRA_DATE);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		if(date_new_mission != null) {
			date_for_request = date_new_mission;
			try {
				Date date = formatter.parse(date_for_request);
				date_txt.setText(new SimpleDateFormat("EEEE dd MMMM yyyy").format(date));
			}
			catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		else {
			date_for_request = new String(formatter.format(calendar.getTime()));
		}

		loadArrayListMissionFromDatabase();
		cel_MissionArrayAdapter = new CEL_MissionArrayAdapter(MissionsList.this, R.layout.missions_item, missionsList, envoyer);
		View header = getLayoutInflater().inflate(R.layout.missions_header, (ViewGroup)getWindow().getDecorView().findViewById(R.layout.missions), false);
		header.setClickable(false);
		listview.addHeaderView(header);
		listview.setAdapter(cel_MissionArrayAdapter);
		listview.setBackgroundColor(Color.TRANSPARENT);
		listview.setCacheColorHint(Color.TRANSPARENT);
		listview.setDividerHeight(0);
		if(missionsList.size() == 0) {
			nodata.setVisibility(View.INVISIBLE);
			listview.setVisibility(View.INVISIBLE);
			deleteMission.setVisibility(View.INVISIBLE);
			demarrerMission.setVisibility(View.INVISIBLE);
			isMissionselected = false;
		}
		setMode();
	}

	/**
	 * Set using of CEL, if user is Independant or Opera Expert
	 */
	private void setMode() {
		Login.settings = getSharedPreferences(Login.PREFS_NAME, 0);
		if(Login.settings.getBoolean("isIndependant", false)) {
			IS_INDEPENDANT = 1;
			lastnameExpert_TextView.setText(Login.settings.getString("independant_login", ""));
			button_creer_mission.setVisibility(View.VISIBLE);
		}
		else {
			IS_INDEPENDANT = 0;
            lastnameExpert_TextView.setText(nomExpert);
            nameExpert_TextView.setText(prenomExpert);
			button_creer_mission.setVisibility(View.INVISIBLE);
		}
		button_creer_mission.setVisibility(View.VISIBLE);
		reloadListView();
	}

	public void callDatePicker(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	@Override
	public void returnDate(String dateString, Date date) {

		date_txt.setText(dateString);
		date_for_request = dateString;
		if(!Login.settings.getBoolean("noInternet", false)) {
			if (!Login.settings.getBoolean("isIndependant", false)) {
				//Login.loginUser(Login.identifiant, Login.password, date_for_request, "2");
				if (!BuildConfig.DEBUG)
					showProgress(mContext, getString(R.string.loading));

			} else {
				setMode();
			}
		}
		else {
			reloadListView();
		}
	}

	public void callDeleteMission(View v) {
		if(isMissionselected) {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(getString(R.string.mis_confirm_delete));
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    CEL_Mission_DAO mission_dao = new CEL_Mission_DAO(mContext);
                    mission_dao.deleteValue(missionSelected.getIdMission());
                    reloadListView();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


		}
		else
			Toast.makeText(mContext, R.string.select_mission, Toast.LENGTH_LONG).show();
	}

	public void callDemarrerMission(View v) {
		if(isMissionselected) {
			missionSelected.setEtatMission(1);
			mission_DAO = new CEL_Mission_DAO(this);
			mission_DAO.updateValue(missionSelected);
			Intent i=new Intent(mActivity, EtatDesLieux.class);
			startActivityForResult(i, MISSION_LIST);
		} else
			Toast.makeText(mContext, R.string.select_mission, Toast.LENGTH_LONG).show();
	}

	public void callQuitterMission(View v) {
		//Intent intent = new Intent(mActivity, Login.class);
		//startActivity(intent);
		finish();
	}

	public void callEnvoyerMission(View v) {
		if(isMissionselected) {
			Intent intent = new Intent(mActivity, PostDetails.class);
			intent.putExtra("idMission", missionSelected.getIdMission());
			intent.putExtra("date_for_request", date_for_request);
			startActivity(intent);
		}
		else
			Toast.makeText(mContext, R.string.select_mission, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MISSION_LIST) {
			if (resultCode == RESULT_OK) {
				loadArrayListMissionFromDatabase();
				CEL_MissionArrayAdapter.radio_select=-1;
				isMissionselected = false;
				reloadListView();
			}
		}		
	}

	public static void loadArrayListMissionFromDatabase() {
		mission_DAO = new CEL_Mission_DAO(mContext); 
		missionsList = new ArrayList<>();
		String userLogin = Login.settings.getString("ExpertLogin", "");
		missionsList.addAll(mission_DAO.selectAllDateCEL_Mission(date_for_request, IS_INDEPENDANT, userLogin));
		mission_DAO.close();
	}

	/**
	 * This is the Handler for the fragment. It will receive messages from the
	 * DownloaderThread and make the necessary updates to the UI.
	 */
	public static Handler fragmentHandler = new Handler() {
		public void handleMessage(Message msg)	{
			switch(msg.what) {

				/**
				 * Handling MESSAGE_DOWNLOAD_COMPLETE:
				 * Reload ListView when download is complete
				 */
			case MESSAGE_DOWNLOAD_COMPLETE:
				CEL_MissionArrayAdapter.radio_select=-1;
				isMissionselected = false;
				reloadListView();
				break;
			}
		}
	};

	public static void reloadListView() {
		isMissionselected = false;
		loadArrayListMissionFromDatabase();
		if(missionsList.size() == 0) {
			nodata.setVisibility(View.VISIBLE);
			listview.setVisibility(View.INVISIBLE);
			deleteMission.setVisibility(View.INVISIBLE);
			demarrerMission.setVisibility(View.INVISIBLE);
			isMissionselected = false;
		} else {
			nodata.setVisibility(View.GONE);
			listview.setVisibility(View.VISIBLE);
			deleteMission.setVisibility(View.VISIBLE);
			demarrerMission.setVisibility(View.VISIBLE);

			cel_MissionArrayAdapter.clear();
			cel_MissionArrayAdapter.addAll(missionsList);	
			cel_MissionArrayAdapter.notifyDataSetChanged();

			listview.setAdapter(cel_MissionArrayAdapter);
			listview.setBackgroundColor(Color.TRANSPARENT);
			listview.setCacheColorHint(Color.TRANSPARENT);
			listview.setDividerHeight(0);
		}

		if(!BuildConfig.DEBUG)
			closeProgress();
	}

	private void displayAlertDialogCreateMission() {

		//Initialize custom alert dialog
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.alert_dialog_create_mission);
		dialog.setTitle(R.string.app_name);

		//On click on cancel button we dismiss alert dialog
		TextView cancel_TextView = (TextView) dialog.findViewById(R.id.cancel_TextView);
		cancel_TextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		//On click on validate button we go to ModifyMissionActivity
		TextView validate_TextView = (TextView) dialog.findViewById(R.id.validate_TextView);
		validate_TextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent modifyMissionActivityIntent = new Intent(mActivity, ModifyMissionActivity.class);
				modifyMissionActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				modifyMissionActivityIntent.putExtra("addMission", true);
				mContext.startActivity(modifyMissionActivityIntent);
				((MissionsList)mContext).finish();
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}
