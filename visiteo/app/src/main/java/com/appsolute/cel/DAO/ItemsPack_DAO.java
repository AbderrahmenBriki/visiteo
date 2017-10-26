package com.appsolute.cel.DAO;

import java.util.ArrayList;

import com.appsolute.cel.models.ItemsPack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ItemsPack_DAO extends  CEL_Database_DAO {

	public ItemsPack_DAO(Context pContext) {
		super(pContext);
	}	

	public static final String ITEMS_PACK_TABLE = "ItemsPack";
	public static final String KEY = "idItemsPack";
	public static final String DESCRIPTION = "description";
	public static final String ROOM_KEY = "idRoom";

	public static final String TABLE_CREATE = "CREATE TABLE " + ITEMS_PACK_TABLE 
			+ " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ DESCRIPTION + " TEXT, "
			+ ROOM_KEY + " INTEGER, "
			+ " FOREIGN KEY ("+ROOM_KEY+") REFERENCES "+ Room_DAO.ROOM_TABLE +" ("+Room_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ITEMS_PACK_TABLE + ";";


	/**
	 * Insert new value on ItemsPack
	 * 
	 * @param itemsPack
	 */
	public int addValue(ItemsPack itemsPack) {
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, itemsPack.getDescription());
		value.put(ROOM_KEY, itemsPack.getIdRoom());
		return (int)operaDataBase.insert(ITEMS_PACK_TABLE, null, value);
	}

	/**
	 * Delete an ItemsPack value from an Id
	 * @param itemsPack
	 */
	public void deleteValue(int itemsPack) {
		operaDataBase.delete(ITEMS_PACK_TABLE, KEY + " = ?", new String[]{String.valueOf(itemsPack)});
	}


	/**
	 * Update/Modify an ItemsPack
	 * @param itemsPack
	 */
	public void updateValue(ItemsPack itemsPack) {
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, itemsPack.getDescription());
		value.put(ROOM_KEY, itemsPack.getIdRoom());
		operaDataBase.update(ITEMS_PACK_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(itemsPack.getIdItemsPack())});
	}

	/**
	 * Get a specific ItemsPack
	 * @param itemsPack
	 */
	public ItemsPack getItemsPack(int idItemsPack) {
		Cursor cursor = operaDataBase.rawQuery("select * from " + ITEMS_PACK_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idItemsPack)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build itemsPack object
					ItemsPack itemsPack = new ItemsPack();
					itemsPack.setIdItemsPack(Integer.parseInt(cursor.getString(0)));
					itemsPack.setDescription(cursor.getString(1));
					itemsPack.setIdRoom(Integer.parseInt(cursor.getString(2)));
					return itemsPack;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		return null;
	}


	/**
	 * Get a specific ItemsPack
	 */
	public ArrayList<ItemsPack> getListItemsPack(int idRoom) {

		ArrayList<ItemsPack> itemsPacksArrayList = new ArrayList<ItemsPack>();
		Cursor cursor;
		cursor = operaDataBase.rawQuery("SELECT * FROM " + ITEMS_PACK_TABLE + " WHERE " + ROOM_KEY + "= ?"
				+ " ORDER BY "+DESCRIPTION+" COLLATE NOCASE ASC;", 
				new String[]{String.valueOf(idRoom)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						//Build itemsPack object
						ItemsPack itemsPack = new ItemsPack();
						itemsPack.setIdItemsPack(Integer.parseInt(cursor.getString(0)));
						itemsPack.setDescription(cursor.getString(1));
						itemsPack.setIdRoom(Integer.parseInt(cursor.getString(2)));
						itemsPacksArrayList.add(itemsPack);
					}
				} while(cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		return itemsPacksArrayList;
	}


}
