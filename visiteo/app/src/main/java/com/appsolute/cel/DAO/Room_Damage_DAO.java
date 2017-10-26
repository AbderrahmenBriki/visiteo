package com.appsolute.cel.DAO;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.RoomDamage;

/**
 * 
 * This class refer to Room, it's using to access to Room on database 
 * 
 * @author Lucien Guimaraes
 *
 */
public class Room_Damage_DAO extends CEL_Database_DAO {

	public Room_Damage_DAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}
	
	public static final String ROOM_DAMAGE_TABLE = "Room_Damage";
	public static final String KEY = "idDamage";
	public static final String DESCRIPTION = "descriptionDamage";
	public static final String MANDATORY = "throwAlert";
	public static final String ID_ITEM_TYPE = "idItemType";
	
	public static final String TABLE_CREATE = "CREATE TABLE " + ROOM_DAMAGE_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ DESCRIPTION + " TEXT, " 
			+ MANDATORY + " TEXT, "
			+ ID_ITEM_TYPE + " INTEGER , "
			+ " FOREIGN KEY ("+ID_ITEM_TYPE+") REFERENCES "+ ItemType_DAO.ITEM_TYPE_TABLE +" ("+ItemType_DAO.KEY+"));";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ROOM_DAMAGE_TABLE + ";";
	
	
	/**
	 * Insert new value on Room
	 * 
	 * @param room
	 */
	public int addValue(RoomDamage room) {
		int id =0;
		open();
		if (room == null)
			return id;
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, room.getDescriptionRoom());
		value.put(MANDATORY, room.getMandatoryRoom());
		value.put(ID_ITEM_TYPE, room.getIdItemType());
		if (operaDataBase != null )
			id = (int)operaDataBase.insert(ROOM_DAMAGE_TABLE, null, value);
		close();
		return id;
	}

	/**
	 * Delete a Room value from an Id
	 * @param idChauffage
	 */
	public void deleteValue(int idChauffage) {
		open();
		operaDataBase.delete(ROOM_DAMAGE_TABLE, KEY + " = ?", new String[]{String.valueOf(idChauffage)});
		close();
	}
	
	/**
	 * Delete a Room value
	 */
	public void deleteTable() {
		open();
		operaDataBase.delete(ROOM_DAMAGE_TABLE, null, null);
		close();
	}


	/**
	 * Update/Modify a Room
	 * @param chauffage
	 */
	public void updateValue(RoomDamage room) {
		open();
		if (room == null)
			return ;
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, room.getDescriptionRoom());
		value.put(MANDATORY, room.getMandatoryRoom());
		value.put(ID_ITEM_TYPE, room.getIdItemType());
		operaDataBase.update(ROOM_DAMAGE_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(room.getIdRoomDamage())});
		close();
	}

	/**
	 * Select a specific Room
	 * @param idRoom
	 */
	public RoomDamage select(int idRoom) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ROOM_DAMAGE_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idRoom)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build room object
					RoomDamage room = new RoomDamage();
					room.setIdRoomDamage(Integer.parseInt(cursor.getString(0)));
					room.setDescriptionRoom(cursor.getString(1));
					room.setMandatoryRoom(cursor.getString(2));
					room.setIdItemType(Integer.parseInt(cursor.getString(3)));
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
	 * Select all damage for a specific ItemType.
	 * Return an ArrayList of RoomDamage
	 * 
	 * @param idItemType
	 */
	public ArrayList<RoomDamage> selectData(int idItemType) {
		ArrayList<RoomDamage> roomDamage = new ArrayList<RoomDamage>();
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ROOM_DAMAGE_TABLE + " where " + ID_ITEM_TYPE + "= ?"
				+" ORDER BY "+DESCRIPTION+" COLLATE NOCASE ASC;", new String[]
				{String.valueOf(idItemType)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						RoomDamage room = new RoomDamage();
						room.setIdRoomDamage(Integer.parseInt(cursor.getString(0)));
						room.setDescriptionRoom(cursor.getString(1));
						room.setMandatoryRoom(cursor.getString(2));
						room.setIdItemType(Integer.parseInt(cursor.getString(3)));
						roomDamage.add(room);
					}
				} while (cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return roomDamage;
	}
}
