package com.appsolute.cel.DAO;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.ItemType;

public class ItemType_DAO extends  CEL_Database_DAO {

	public ItemType_DAO(Context pContext) {
		super(pContext);
	}	

	public static final String ITEM_TYPE_TABLE = "ItemType";
	public static final String KEY = "idItemType";
	public static final String DESCRIPTION = "descriptionItemType";
	public static final String PICTURE_ID = "pictureIDItemType";
	public static final String REPAIR_ID = "repairIDItemType";
	public static final String CLEAN_ID = "cleanIDItemType";
	public static final String DAMAGE = "damageItemType";
	public static final String ROOM_ITEM_KEY = "idRoomItem";

	public static final String TABLE_CREATE = "CREATE TABLE " + ITEM_TYPE_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ DESCRIPTION + " TEXT, "
			+ PICTURE_ID + " TEXT, " 
			+ REPAIR_ID + " TEXT, "
			+ CLEAN_ID + " TEXT, "
			+ DAMAGE + " TEXT, "
			+ ROOM_ITEM_KEY + " INTEGER, "
			+ " FOREIGN KEY ("+ROOM_ITEM_KEY+") REFERENCES "+ RoomItem_DAO.ROOM_ITEM_TABLE +" ("+RoomItem_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ITEM_TYPE_TABLE + ";";


	/**
	 * Insert new value on ItemType
	 * 
	 * @param ItemType
	 */
	public int addValue(ItemType ItemType) {
		int id=0;
		open();
		if (ItemType == null)
			return id;
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, ItemType.getDescriptionItemType());
		value.put(PICTURE_ID, ItemType.getPictureIDItemType());
		value.put(REPAIR_ID, ItemType.getRepairIDItemType());
		value.put(CLEAN_ID, ItemType.getCleanIDItemType());
		value.put(DAMAGE, ItemType.getDamageItemType());
		value.put(ROOM_ITEM_KEY, ItemType.getIdRoomItem());
		if (operaDataBase != null )
			id = (int)operaDataBase.insert(ITEM_TYPE_TABLE, null, value);
		close();
		return id;
	}

	/**
	 * Delete a ItemType value from an Id
	 * @param idItemType
	 */
	public void deleteValue(int idItemType) {
		open();
		operaDataBase.delete(ITEM_TYPE_TABLE, KEY + " = ?", new String[]{String.valueOf(idItemType)});
		close();
	}

	/**
	 * Delete a Room value
	 */
	public void deleteTable() {
		open();
		operaDataBase.delete(ITEM_TYPE_TABLE, null, null);
		close();
	}


	/**
	 * Update/Modify a ItemType
	 * @param ItemType
	 */
	public void updateValue(ItemType ItemType) {
		open();
		if (ItemType == null)
			return ;
		ContentValues value = new ContentValues(); 
		value.put(DESCRIPTION, ItemType.getDescriptionItemType());
		value.put(PICTURE_ID, ItemType.getPictureIDItemType());
		value.put(REPAIR_ID, ItemType.getRepairIDItemType());
		value.put(CLEAN_ID, ItemType.getCleanIDItemType());
		value.put(DAMAGE, ItemType.getDamageItemType());
		value.put(ROOM_ITEM_KEY, ItemType.getRepairIDItemType());
		operaDataBase.update(ITEM_TYPE_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(ItemType.getIdItemType())});
		close();
	}

	/**
	 * Select a specific ItemType
	 * @param idRoomItem
	 */
	public ArrayList<ItemType> selectItemType(int idRoomItem) {
		ArrayList<ItemType> itemTypeData = new ArrayList<ItemType>();
		open();
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + ITEM_TYPE_TABLE + " where " + ROOM_ITEM_KEY + "= ?", new String[]
				{String.valueOf(idRoomItem)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						//Build ItemType object
						ItemType ItemType = new ItemType();
						ItemType.setIdItemType(Integer.parseInt(cursor.getString(0)));
						ItemType.setDescriptionItemType(cursor.getString(1));
						ItemType.setPictureIDItemType(cursor.getString(2));
						ItemType.setRepairIDItemType(cursor.getString(3));
						ItemType.setCleanIDItemType(cursor.getString(4));
						ItemType.setIdRoomItem(cursor.getInt(6));
						itemTypeData.add(ItemType);
					}
				} while(cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return itemTypeData;
	}

	/**
	 * Select a specific ItemType
	 * @param idItemType
	 */
	public ItemType select(int idItemType) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ITEM_TYPE_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idItemType)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build ItemType object
					ItemType ItemType = new ItemType();
					ItemType.setIdItemType(Integer.parseInt(cursor.getString(0)));
					ItemType.setDescriptionItemType(cursor.getString(1));
					ItemType.setPictureIDItemType(cursor.getString(2));
					ItemType.setRepairIDItemType(cursor.getString(3));
					ItemType.setCleanIDItemType(cursor.getString(4));
					ItemType.setIdRoomItem(Integer.parseInt(cursor.getString(6)));
					return ItemType;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return null;
	}


	public int getIdItemTypeFromDescription(String description) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select " + KEY + " from " + ITEM_TYPE_TABLE + " where " + DESCRIPTION + "= ?", new String[]
				{description});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) 
				return Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY)));
		}
		if(cursor!=null)
			cursor.close();
		close();
		return 0;
	}
}
