package com.appsolute.cel.DAO;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.PackElement;

public class PackElement_DAO extends  CEL_Database_DAO {

	public PackElement_DAO(Context pContext) {
		super(pContext);
	}	

	public static final String PACK_ELEMENT_TABLE = "PackElement";
	public static final String KEY = "idPackElement";
	public static final String ITEM = "item";
	public static final String TYPE = "type";
	public static final String ID_ITEM_TYPE = "idItemType";
	public static final String ID_ITEM_PACK = "idItemsPack";

	public static final String TABLE_CREATE = "CREATE TABLE " + PACK_ELEMENT_TABLE 
			+ " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ ITEM + " TEXT, "		
			+ TYPE + " TEXT, "		
			+ ID_ITEM_TYPE + " INTEGER , "
			+ ID_ITEM_PACK + " INTEGER , "
			+ " FOREIGN KEY ("+ID_ITEM_TYPE+") REFERENCES "+ ItemType_DAO.ITEM_TYPE_TABLE +" ("+ItemType_DAO.KEY+") "
			+ " FOREIGN KEY ("+ID_ITEM_PACK+") REFERENCES "+ ItemsPack_DAO.ITEMS_PACK_TABLE +" ("+ItemsPack_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + PACK_ELEMENT_TABLE + ";";


	/**
	 * Insert new value on PackElement
	 * 
	 * @param packElement
	 */
	public int addValue(PackElement packElement) {
		ContentValues value = new ContentValues(); 
		value.put(ITEM, packElement.getItem());
		value.put(TYPE, packElement.getType());
		value.put(ID_ITEM_TYPE, packElement.getIdItemType());
		value.put(ID_ITEM_PACK, packElement.getIdItemPack());
		return (int)operaDataBase.insert(PACK_ELEMENT_TABLE, null, value);
	}

	/**
	 * Delete an PackElement value from an Id
	 * @param packElement
	 */
	public void deleteValue(int packElement) {
		operaDataBase.delete(PACK_ELEMENT_TABLE, KEY + " = ?", new String[]{String.valueOf(packElement)});
	}


	/**
	 * Update/Modify an PackElement
	 * @param packElement
	 */
	public void updateValue(PackElement packElement) {
		ContentValues value = new ContentValues(); 
		value.put(ITEM, packElement.getItem());
		value.put(TYPE, packElement.getType());
		value.put(ID_ITEM_TYPE, packElement.getIdItemType());
		value.put(ID_ITEM_PACK, packElement.getIdItemPack());
		operaDataBase.update(PACK_ELEMENT_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(packElement.getIdPackElement())});
	}

	/**
	 * Select a specific PackElement
	 * @param packElement
	 */
	public PackElement select(int idPackElement) {
		Cursor cursor = operaDataBase.rawQuery("select * from " + PACK_ELEMENT_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idPackElement)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build packElement object
					PackElement packElement = new PackElement();
					packElement.setIdPackElement(Integer.parseInt(cursor.getString(0)));
					packElement.setItem(cursor.getString(1));
					packElement.setType(cursor.getString(2));
					packElement.setIdItemType(Integer.parseInt(cursor.getString(3)));
					packElement.setIdItemPack(Integer.parseInt(cursor.getString(4)));
					return packElement;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		return null;
	}

	/**
	 * Get all PackElement from a specific itemsPack
	 * 
	 * @param idItemsPack
	 */
	public ArrayList<PackElement> getAllPackElementFromItemPack(int idItemsPack) {
		ArrayList<PackElement> packElementArrayList = new ArrayList<PackElement>();
		Cursor cursor = operaDataBase.rawQuery("select * from " + PACK_ELEMENT_TABLE + " where " + ID_ITEM_PACK + "= ?", new String[]
				{String.valueOf(idItemsPack)});
		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						//Build packElement object
						PackElement packElement = new PackElement();
						packElement.setIdPackElement(Integer.parseInt(cursor.getString(0)));
						packElement.setItem(cursor.getString(1));
						packElement.setType(cursor.getString(2));
						packElement.setIdItemType(Integer.parseInt(cursor.getString(3)));
						packElement.setIdItemPack(Integer.parseInt(cursor.getString(4)));
						packElementArrayList.add(packElement);
					}
				} while(cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		return packElementArrayList;
	}

}
