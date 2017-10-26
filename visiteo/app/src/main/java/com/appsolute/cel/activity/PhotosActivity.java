package com.appsolute.cel.activity;


import com.appsolute.cel.BaseActivity;
import com.appsolute.cel.DAO.OPERA_Photos_DAO;
import com.appsolute.cel.adapter.PhotoGridAdapter;
import com.appsolute.cel.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

public class PhotosActivity extends BaseActivity {
	
	private GridView photosGridView;
	
	public static Context context;
	private OPERA_Photos_DAO opera_photos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_view);
		baseMethod();
		
		context = this.getBaseContext();
		
		opera_photos = new OPERA_Photos_DAO(context);
		
		photosGridView = (GridView)findViewById(R.id.grid_view_post_photos);
		photosGridView.setAdapter(new PhotoGridAdapter(
				opera_photos.selectAllPhotosFromMission(MissionsList.missionSelected.getIdMission()), context));
		
	}
	
	public void callCancel(View v) {
		this.finish();
	}
}
