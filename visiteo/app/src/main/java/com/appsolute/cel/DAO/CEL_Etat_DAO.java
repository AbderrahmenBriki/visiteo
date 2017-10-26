package com.appsolute.cel.DAO;

import java.util.ArrayList;

import com.appsolute.cel.models.CEL_Etat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CEL_Etat_DAO extends CEL_Database_DAO {

	public CEL_Etat_DAO(Context pContext) {
		super(pContext);
	}

	public static final String ETAT_TABLE = "CEL_Etat";
	public static final String KEY = "idEtat";
	public static final String DESCRIPTION = "descriptionEtat";

	public static final String TABLE_CREATE = "CREATE TABLE " + ETAT_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ DESCRIPTION + " TEXT);";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ETAT_TABLE + ";";

	/**
	 * Insert new value on CEL_Etat
	 * 
	 * @param etat
	 */
	public void addValue(CEL_Etat etat) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(KEY, etat.getIdEtat());
		value.put(DESCRIPTION, etat.getDescriptionEtat());
		operaDataBase.insert(ETAT_TABLE, null, value);
		close();
	}

	/**
	 * Delete a CEL_Etat value from an Id
	 * @param etat
	 */
	public void deleteValue(int idEtat) {
		open();
		operaDataBase.delete(ETAT_TABLE, KEY + " = ?", new String[]{String.valueOf(idEtat)});
		close();
	}
	
	
	/**
	 * Delete all value on CEL_Etat 
	 * 
	 */
	public void deleteAllValue() {
		open();
		operaDataBase.delete(ETAT_TABLE, null, new String[]{});
		close();
	}
	

	/**
	 * Update value on existing CEL_Etat
	 * 
	 * @param etat
	 */
	public void updateValue(CEL_Etat etat){
		open();
		ContentValues value = new ContentValues(); 
		value.put(KEY, etat.getIdEtat());
		value.put(DESCRIPTION, etat.getDescriptionEtat());
		operaDataBase.insert(ETAT_TABLE, null, value);
		close();
	}

	/**
	 * Select a specific CEL_Etat
	 * @param idEtat
	 */
	public CEL_Etat select(int idEtat) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ETAT_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idEtat)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Etat object
					CEL_Etat etat = new CEL_Etat();
					etat.setIdEtat(Integer.parseInt(cursor.getString(0)));
					etat.setDescriptionEtat(cursor.getString(1));
					return etat;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return null;
	}
	
	public ArrayList<CEL_Etat> selectData() {
		ArrayList<CEL_Etat> data = new ArrayList<CEL_Etat>();
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ETAT_TABLE, null);

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						CEL_Etat etat = new CEL_Etat();
						etat.setIdEtat(Integer.parseInt(cursor.getString(0)));
						etat.setDescriptionEtat(cursor.getString(1));
						data.add(etat);
					}
				} while(cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return data;
	}
}
