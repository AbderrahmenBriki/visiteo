package com.appsolute.cel.models;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.androidquery.callback.BitmapAjaxCallback;

public class ApplicationData extends Application {
	private double latitude =0;
	private double longitude = 0;
	private int idMission = 0;
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public int getMissionId() {
		return idMission;
	}

	public void setMissionId(int idMission) {
		this.idMission = idMission;
	}
	
	@Override
    public void onLowMemory(){  
        //clear all memory cached images when system is in low memory
        //note that you can configure the max image cache count, see CONFIGURATION
        BitmapAjaxCallback.clearCache();
        System.gc();
    }

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

}
