package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.RoomItem;

import java.util.ArrayList;

public class RoomItem_DAO extends CEL_Database_DAO{

	public RoomItem_DAO(Context pContext) {
		super(pContext);
	}


	public static final String ROOM_ITEM_TABLE = "RoomItem";
	public static final String KEY = "idRoomItem";
	public static final String DESCRIPTION = "descriptionRoomItem";
	public static final String DISPLAY = "displayRoomItem";
	public static final String COUNTABLE = "countableRoomItem";
	public static final String ITEM_GROUP_DESCRIPTION = "isEtatPiece";
	public static final String ROOM_KEY = "idRoom";

	public static final String TABLE_CREATE = "CREATE TABLE " + ROOM_ITEM_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ DESCRIPTION + " TEXT, "
			+ DISPLAY + " TEXT, " 
			+ COUNTABLE + " TEXT, "
			+ ITEM_GROUP_DESCRIPTION + " INTEGER, "
			+ ROOM_KEY + " INTEGER, "
			+ " FOREIGN KEY ("+ROOM_KEY+") REFERENCES "+ Room_DAO.ROOM_TABLE +" ("+Room_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ROOM_ITEM_TABLE + ";";


	/**
	 * Insert new value on RoomItem
	 * 
	 * @param roomItem
	 */
	public int addValue(RoomItem roomItem) {
		int id=0;
		if (roomItem == null)
			return id;
		ContentValues value = new ContentValues();
		value.put(DESCRIPTION, roomItem.getDescriptionString()); 
		value.put(DISPLAY, roomItem.getDisplayRoomItem());
		value.put(COUNTABLE, roomItem.getCountableRoomItem());
		value.put(ITEM_GROUP_DESCRIPTION, roomItem.getItemGroupDescription());
		value.put(ROOM_KEY, roomItem.getIdRoom());
		if (operaDataBase != null )
			id = (int)operaDataBase.insert(ROOM_ITEM_TABLE, null, value);
		return id;
	}

	/**
	 * Delete a RoomItem value from an Id
	 * @param idRoomItem
	 */
	public void deleteValue(int idRoomItem) {
		operaDataBase.delete(ROOM_ITEM_TABLE, KEY + " = ?", new String[]{String.valueOf(idRoomItem)});
	}

	/**
	 * Delete a Room value
	 */
	public void deleteTable() {
		operaDataBase.delete(ROOM_ITEM_TABLE, null, null);
	}


	/**
	 * Update/Modify a RoomItem
	 * @param roomItem
	 */
	public void updateValue(RoomItem roomItem) {
		if (roomItem == null)
			return ;
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, roomItem.getDescriptionString()); 
		value.put(DISPLAY, roomItem.getDisplayRoomItem());
		value.put(COUNTABLE, roomItem.getCountableRoomItem());
		value.put(ITEM_GROUP_DESCRIPTION, roomItem.getItemGroupDescription());
		value.put(ROOM_KEY, roomItem.getIdRoom());
		operaDataBase.update(ROOM_ITEM_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(roomItem.getIdRoomItem())});
	}

	/**
	 * Select a specific RoomItem
	 * @param idRoomItem
	 */
	public RoomItem select(int idRoomItem) {
		Cursor cursor = operaDataBase.rawQuery("select * from " + ROOM_ITEM_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idRoomItem)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build roomItem object
					RoomItem roomItem = new RoomItem();
					roomItem.setIdRoomItem(cursor.getInt(0));
					roomItem.setDescriptionString(cursor.getString(1));
					roomItem.setDisplayRoomItem(cursor.getString(2));
					roomItem.setCountableRoomItem(cursor.getString(3));
					roomItem.setItemGroupDescription(cursor.getInt(4));
					roomItem.setIdRoom(cursor.getInt(5));
					return roomItem;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		return null;
	}
	
	/**
	 * Get a specific RoomItem where idRoom and description match
	 * @param idRoom
	 * @param description
	 */
	public RoomItem getRoomItemFromParameters(int idRoom, String description) {
		Cursor cursor = operaDataBase.rawQuery("select * from " + ROOM_ITEM_TABLE 
				+ " where " + ROOM_KEY + "= ?"
				+ " AND "+ DESCRIPTION + " = '" + description +"';", new String[]
				{String.valueOf(idRoom)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build roomItem object
					RoomItem roomItem = new RoomItem();
					roomItem.setIdRoomItem(cursor.getInt(0));
					roomItem.setDescriptionString(cursor.getString(1));
					roomItem.setDisplayRoomItem(cursor.getString(2));
					roomItem.setCountableRoomItem(cursor.getString(3));
					roomItem.setItemGroupDescription(cursor.getInt(4));
					roomItem.setIdRoom(cursor.getInt(5));
					return roomItem;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		return null;
	}

	/**
	 * Select all RoomItem corresponding to idRoom and itemGroupDescription
	 * @param idRoom ID of the room
	 * @param isOrderBy order by ASC or not
	 * @param itemGroupDescription
	 */
	public ArrayList<RoomItem> selectAllRoomItems(int idRoom, Boolean isOrderBy, int itemGroupDescription) {
		ArrayList<RoomItem> roomItemData = new ArrayList<RoomItem>();
		Cursor cursor;
		if(isOrderBy) {
			cursor = operaDataBase.rawQuery("SELECT * FROM " + ROOM_ITEM_TABLE + " WHERE " + ROOM_KEY + "= ?"
					+ " AND "+ ITEM_GROUP_DESCRIPTION + " = " + itemGroupDescription
					+ " ORDER BY "+DESCRIPTION+" COLLATE NOCASE ASC;", 
					new String[]{String.valueOf(idRoom)});
		}
		else {
			cursor = operaDataBase.rawQuery("SELECT * FROM " + ROOM_ITEM_TABLE + " WHERE " + ROOM_KEY + "= ?"
					+ " AND "+ ITEM_GROUP_DESCRIPTION + " = " + itemGroupDescription + ";",
					new String[]{String.valueOf(idRoom)});
		}

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						//Build roomItem object
						RoomItem roomItem = new RoomItem();
						roomItem.setIdRoomItem(cursor.getInt(0));
						roomItem.setDescriptionString(cursor.getString(1));
						roomItem.setDisplayRoomItem(cursor.getString(2));
						roomItem.setCountableRoomItem(cursor.getString(3));
						roomItem.setItemGroupDescription(cursor.getInt(4));
						roomItem.setIdRoom(cursor.getInt(5));
						roomItemData.add(roomItem);
					}
				} while(cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		return roomItemData;
	}



	/**
	 * Get a specific RoomItem where idRoom and description match
	 * @param idRoom
	 * @param description
	 */
	public int getRoomItemIDFromParameters(int idRoom, String description) {
		Cursor cursor = operaDataBase.rawQuery("select * from " + ROOM_ITEM_TABLE
				+ " where " + ROOM_KEY + "= ?"
				+ " AND "+ DESCRIPTION + " = '" + description +"';", new String[]
				{String.valueOf(idRoom)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {

					return cursor.getInt(0);
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		return 0;
	}
}
