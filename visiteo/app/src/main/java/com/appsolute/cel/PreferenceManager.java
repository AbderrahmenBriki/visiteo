package com.appsolute.cel;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {	
		
	public static void setRoomTimestamp(int roomDate, Context context) {
		SharedPreferences preferences;
		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);		
		preferences.edit().putInt("RoomDate", roomDate).commit();
	}
	
	public static int getRoomTimestamp(Context context) {
		SharedPreferences preferences;
		preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
		return preferences.getInt("RoomDate", 0);
	}	
}
