package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.appsolute.cel.models.CEL_Chauffage;

/**
 * 
 * @author Lucien Guimaraes
 *
 *	This class refer to CEL_Chauffage, it's using to access to CEL_Chauffage on database 
 *
 */
public class CEL_Chauffage_DAO extends CEL_Database_DAO{

	public CEL_Chauffage_DAO(Context pContext) {
		super(pContext);

	}

	public static final String CHAUFFAGE_TABLE = "CEL_Chauffage";
	public static final String KEY = "idChauffage";
	public static final String TYPE = "type";
	public static final String CHAUDIERE = "chaudiere";
	public static final String MARQUE = "marque";
	public static final String ENTRETIEN = "entretien";
	public static final String HORS_SERVICE = "horsService";

	public static final String TABLE_CREATE = "CREATE TABLE " + CHAUFFAGE_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ TYPE + " TEXT, " 
			+ CHAUDIERE + " TEXT, " 
			+ MARQUE + " TEXT, "  
			+ ENTRETIEN + " TEXT, "
			+ HORS_SERVICE + " BOOL );";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + CHAUFFAGE_TABLE + ";";

	/**
	 * Insert new value on CEL_Chauffage
	 * 
	 * @param chauffage
	 */
	public int addValue(CEL_Chauffage chauffage) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(TYPE, chauffage.getTypeChauffage());
		value.put(CHAUDIERE, chauffage.getChaudiereChauffage());
		value.put(MARQUE, chauffage.getMarqueChauffage());
		value.put(ENTRETIEN, chauffage.getEntretienChauffage().toString());
		boolean isseleted = false;
		if(chauffage.getHorsService())
			isseleted = true;
		value.put(HORS_SERVICE, isseleted);
		return (int) operaDataBase.insert(CHAUFFAGE_TABLE, null, value);
	}

	/**
	 * Delete a Chauffage value from an Id
	 * @param idChauffage
	 */
	public void deleteValue(int idChauffage) {
		open();
		operaDataBase.delete(CHAUFFAGE_TABLE, KEY + " = ?", new String[]{String.valueOf(idChauffage)});
		close();
	}


	/**
	 * Update/Modify a Chauffage
	 * @param chauffage
	 */
	public void updateValue(CEL_Chauffage chauffage) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(TYPE, chauffage.getTypeChauffage());
		value.put(CHAUDIERE, chauffage.getChaudiereChauffage());
		value.put(MARQUE, chauffage.getMarqueChauffage());
		value.put(ENTRETIEN, chauffage.getEntretienChauffage().toString());
		boolean isseleted = false;
		if(chauffage.getHorsService())
			isseleted = true;
		Log.d("isseleted", "===="+isseleted);
		value.put(HORS_SERVICE, isseleted);
		operaDataBase.update(CHAUFFAGE_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(chauffage.getIdChauffage())});
		close();
	}

	/**
	 * Select a specific Chauffage
	 * @param idChauffage
	 */
	public CEL_Chauffage select(int idChauffage) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + CHAUFFAGE_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idChauffage)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build chauffage object
					CEL_Chauffage chauffage = new CEL_Chauffage();
					chauffage.setIdChauffage(Integer.parseInt(cursor.getString(0)));
					chauffage.setTypeChauffage(Integer.parseInt(cursor.getString(1)));
					chauffage.setChaudiereChauffage(Integer.parseInt(cursor.getString(2)));
					chauffage.setMarqueChauffage(Integer.parseInt(cursor.getString(3)));
					chauffage.setEntretienChauffage(cursor.getString(4));
					if(cursor.getString(5).equals("1"))
						chauffage.setHorsService(true);
					else
						chauffage.setHorsService(false);		
					return chauffage;
				}
			}
		}
		
		if(cursor!=null)
			cursor.close();
		close();
		CEL_Chauffage chauffage = new CEL_Chauffage();
		//Return chauffage
		return chauffage;
	}
}
