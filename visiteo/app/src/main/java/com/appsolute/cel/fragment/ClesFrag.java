package com.appsolute.cel.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.activity.EtatDesLieux;
import com.appsolute.cel.models.CEL_Mission;
import com.appsolute.cel.BaseFragment;
import com.appsolute.cel.DAO.CEL_Clefs_DAO;
import com.appsolute.cel.DAO.CEL_Mission_DAO;
import com.appsolute.cel.R;
import com.appsolute.cel.activity.MissionsList;
import com.appsolute.cel.adapter.CEL_ClefsArrayAdapter;
import com.appsolute.cel.models.CEL_Clefs;
import com.appsolute.cel.models.OPERA_Photos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ClesFrag extends BaseFragment {
	static LinearLayout dynamiclistitems;
	static LayoutInflater inflater1;	
	Button addClefs_Button;
	public static TextView total_txt;
	static ImageView key_img;

	private static ArrayList<CEL_Clefs> clefsList;
	private static CEL_Clefs_DAO clefs_DAO;
	static int width, height;
	public static int total_keys;
	private ListView clef_listView;
	private static OPERA_Photos_DAO photo_clef_DAO;
	private static OPERA_Photos photo_clef;
	private static CEL_Mission mission;
	private static Bitmap imageBitmapClefs;



	public interface OnValiderTextChange {
		public void validerTextChange();
	} 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View layout = inflater.inflate(R.layout.fragment_cles, container, false);

		BitmapDrawable bd=(BitmapDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.img);
		height = bd.getBitmap().getHeight();
		width = bd.getBitmap().getWidth();
		init();
		total_txt = (TextView) layout.findViewById(R.id.total_txt);
		clef_listView = (ListView)layout.findViewById(R.id.clefs_list_view);
		clef_listView.setPadding(20, 10, 20, 10);
		inflater1 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		addClefs_Button = (Button)layout.findViewById(R.id.addClefs_Button); 
		key_img = (ImageView) layout.findViewById(R.id.key_img);
		key_img.setTag("false");		
		key_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callCamera();
			}
		});

		addClefs_Button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				clefsList.add(new CEL_Clefs(MissionsList.missionSelected.getIdMission()));
				clef_listView.setAdapter(new CEL_ClefsArrayAdapter(mContext, R.layout.cles_item, clefsList));
			}
		});

		initializeClefs();
		
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.cles_header, clef_listView, false);
		clef_listView.addHeaderView(header, null, false);
		clef_listView.setAdapter(new CEL_ClefsArrayAdapter(mContext, R.layout.cles_item, clefsList));
		return layout;
	}

	public void callCamera() {
		try {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, EtatDesLieux.pictureFromCamera);
            EtatDesLieux.mImageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, EtatDesLieux.mImageUri);
            startActivityForResult(cameraIntent, EtatDesLieux.REQUEST_CAMERA);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		
	}

	public static void initializeClefs() {
		clefsList = new ArrayList<>();
		clefs_DAO = new CEL_Clefs_DAO(mContext);
		photo_clef_DAO = new OPERA_Photos_DAO(mContext);
		photo_clef = photo_clef_DAO.select(MissionsList.missionSelected.getIdMissionPhotoClef());

		clefsList = clefs_DAO.selectAllFromBiens(MissionsList.missionSelected.getIdMission());

		if(clefsList.size() == 0)
			Toast.makeText(mContext, mContext.getString(R.string.clef_error), Toast.LENGTH_LONG).show();
		if(photo_clef != null && photo_clef.getPhoto() != null) {
			key_img.setImageBitmap(BitmapFactory.decodeByteArray(photo_clef.getPhoto(), 0, photo_clef.getPhoto().length));
			key_img.getLayoutParams().height = height;
			key_img.getLayoutParams().width = width;
			key_img.setTag("true");		
		}
		photo_clef_DAO.close();
		
		total_keys = 0;
		for (CEL_Clefs cel_Clefs : clefsList) {
			total_keys = total_keys + cel_Clefs.getTotal();
		}
		total_txt.setText(String.valueOf(total_keys));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case EtatDesLieux.REQUEST_CAMERA :
			if (resultCode == RESULT_OK) {
				key_img.getLayoutParams().height = height;
				key_img.getLayoutParams().width = width;

                try {
                    saveToInternalStorage(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), EtatDesLieux.mImageUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
				key_img.setTag("true");
			}
			break;
		}
	}

    private void saveToInternalStorage(Bitmap bitmapImage){

        imageBitmapClefs = bitmapImage;

        File imageToUpload = new File(getActivity().getFilesDir(), "imageName.png");

        FileOutputStream outputStream;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        if(BitmapCompat.getAllocationByteCount(imageBitmapClefs) > 2000000) {

            do{
                imageBitmapClefs = Bitmap.createScaledBitmap(imageBitmapClefs,(int)(imageBitmapClefs.getWidth()*0.9), (int)(imageBitmapClefs.getHeight()*0.9), true);
            }
            while(BitmapCompat.getAllocationByteCount(imageBitmapClefs) > 2000000);
        }
        key_img.setImageBitmap(imageBitmapClefs);

        try {
            outputStream = getActivity().openFileOutput(imageToUpload.getName(), Context.MODE_PRIVATE);
            outputStream.write(stream.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	public static void saveClefsPhoto(Fragment fragment) {
		
		try {
			if(key_img.getTag().toString().equalsIgnoreCase("true")) {

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				imageBitmapClefs.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				
				CEL_Mission_DAO mission_DAO = new CEL_Mission_DAO(mContext);
				photo_clef_DAO = new OPERA_Photos_DAO(mContext);
				mission = mission_DAO.select(MissionsList.missionSelected.getIdMission());
				
				String photo_name = MissionsList.missionSelected.getNumeroRdvMission() + " - app -photo_clefs";
				
				if(MissionsList.missionSelected.getIdMissionPhotoClef() >= 0) {
					photo_clef_DAO.deleteValue(MissionsList.missionSelected.getIdMissionPhotoClef());
				}
				
				photo_clef = new OPERA_Photos(MissionsList.missionSelected.getIdMission(), byteArray, photo_name);
				int idPhotoClef = photo_clef_DAO.addValue(photo_clef);
				mission.setIdMissionPhotoClef(idPhotoClef);
				
				mission_DAO.updateValue(mission);
				MissionsList.missionSelected.setIdMissionPhotoClef(idPhotoClef);
				
				mission_DAO.close();
				photo_clef_DAO.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void saveCles() {
		clefs_DAO = new CEL_Clefs_DAO(mContext);
		clefs_DAO.deleteAllFromMission(MissionsList.missionSelected.getIdMission());
		for(CEL_Clefs clef : clefsList) {
			if(clef.getNombreClefNonVerifiee() > 0 || clef.getNombreClefVerifiee() > 0) {
				clefs_DAO.addValue(clef);
			}
		}
	}
	
}
