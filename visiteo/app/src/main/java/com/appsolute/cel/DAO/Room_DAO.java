package com.appsolute.cel.DAO;

import com.appsolute.cel.models.Room;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * 
 * This class refer to Room, it's using to access to Room on database 
 * 
 * @author Lucien Guimaraes
 *
 */
public class Room_DAO extends CEL_Database_DAO {

	public Room_DAO(Context pContext) {
		super(pContext);
	}
	
	public static final String ROOM_TABLE = "Room";
	public static final String KEY = "idRoom";
	public static final String DESCRIPTION = "descriptionRoom";
	public static final String MANDATORY = "mandatoryRoom";
	
	public static final String TABLE_CREATE = "CREATE TABLE " + ROOM_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ DESCRIPTION + " TEXT, " 
			+ MANDATORY + " TEXT);";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ROOM_TABLE + ";";
	
	
	/**
	 * Insert new value on Room
	 * 
	 * @param room
	 */
	public int addValue(Room room) {
		int id =0;
		open();
		if (room == null)
			return id;
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, room.getDescriptionRoom());
		value.put(MANDATORY, room.getMandatoryRoom());
		if (operaDataBase != null )
			id = (int)operaDataBase.insert(ROOM_TABLE, null, value);
		close();
		return id;
	}

	/**
	 * Delete a Room value from an Id
	 * @param idChauffage
	 */
	public void deleteValue(int idChauffage) {
		open();
		operaDataBase.delete(ROOM_TABLE, KEY + " = ?", new String[]{String.valueOf(idChauffage)});
		close();
	}
	
	/**
	 * Delete a Room value
	 */
	public void deleteTable() {
		open();
		operaDataBase.delete(ROOM_TABLE, null, null);
		close();
	}


	/**
	 * Update/Modify a Room
	 * @param chauffage
	 */
	public void updateValue(Room room) {
		open();
		if (room == null)
			return ;
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, room.getDescriptionRoom());
		value.put(MANDATORY, room.getMandatoryRoom());
		operaDataBase.update(ROOM_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(room.getIdRoom())});
		close();
	}

	/**
	 * Select a specific Room
	 * @param idRoom
	 */
	public Room select(int idRoom) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ROOM_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idRoom)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build room object
					Room room = new Room();
					room.setIdRoom(Integer.parseInt(cursor.getString(0)));
					room.setDescriptionRoom(cursor.getString(1));
					room.setMandatoryRoom(cursor.getString(2));
					return room;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return null;
	}
	
	/**
	 * Select a specific Room
	 * @param idRoom
	 */
	public int selectId(String room) {
		int id = 0;
		open();
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + ROOM_TABLE + " WHERE LOWER(" + DESCRIPTION + ") LIKE ?", new String[]
				{room.toLowerCase()});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {					
					id = Integer.parseInt(cursor.getString(0));
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return id;
	}
}
