package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.SignatureItem;

import java.util.ArrayList;
import java.util.HashMap;

public class Signature_DAO extends CEL_Database_DAO {

	public Signature_DAO(Context pContext) {
		super(pContext);
	}
	
	public static final String SIGNATURE_TABLE = "Signature";
	public static final String KEY = "idSig";
	public static final String TYPE = "type";
	public static final String IMAGE = "idOperaPhoto";
	public static final String ID_MISSION = "idMission";
	
	public static final String TABLE_CREATE = "CREATE TABLE " + SIGNATURE_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ TYPE + " TEXT, " 
			+ IMAGE + " INTEGER, "
			+ ID_MISSION + " INTEGER ,"
            + " FOREIGN KEY ("+IMAGE+") REFERENCES "+OPERA_Photos_DAO.PHOTOS_TABLE+" ("+OPERA_Photos_DAO.KEY+"), "
            + " FOREIGN KEY ("+ID_MISSION+") REFERENCES "+CEL_Mission_DAO.MISSION_TABLE+" ("+CEL_Mission_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + SIGNATURE_TABLE + ";";
	
	
	/**
	 * Insert new value on Room
	 * 
	 * @param data
	 */
	public int addValue(SignatureItem data) {
		int id =0;
		open();
		if (data == null)
			return id;
		ContentValues value = new ContentValues(); 
		value.put(TYPE, data.getType());
		value.put(IMAGE, data.getIdOperaPhoto());
		value.put(ID_MISSION, data.getIdMission());
		if (operaDataBase != null )
			id = (int)operaDataBase.insert(SIGNATURE_TABLE, null, value);
		close();
		return id;
	}

	/**
	 * Delete a Signature value from an Id
	 * @param idType
	 */
	public void deleteValue(String idType) {
		open();
		operaDataBase.delete(SIGNATURE_TABLE, TYPE + " = ?", new String[]{idType});
		close();
	}
	
	/**
	 * Delete a Signature value
	 */
	public void deleteTable() {
		open();
		operaDataBase.delete(SIGNATURE_TABLE, null, null);
		close();
	}

	/**
	 * Update/Modify a Signature
	 * @param dataSig
	 */
	public void updateValue(SignatureItem dataSig) {
		open();
		if (dataSig == null)
			return ;
		ContentValues value = new ContentValues(); 
		value.put(TYPE, dataSig.getType());
		value.put(IMAGE, dataSig.getIdOperaPhoto());
		value.put(ID_MISSION, dataSig.getIdMission());
		operaDataBase.update(SIGNATURE_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(dataSig.getIdSignature())});
		close();
	}

	/**
	 * Select a specific Signature
	 * @param idMission
	 */
	public ArrayList<SignatureItem> select(int idMission) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " +  SIGNATURE_TABLE + " where " + ID_MISSION + " = ?", new String[]
				{String.valueOf(idMission)});

		ArrayList<SignatureItem> dataSig = new ArrayList<>();
		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						SignatureItem data = new SignatureItem();
						data.setIdSignature(Integer.parseInt(cursor.getString(0)));
						data.setType(cursor.getString(1));
						data.setIdOperaPhoto(cursor.getInt(2));
						data.setIdMission(cursor.getInt(3));
						dataSig.add(data);
					}
				} while(cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return dataSig;
	}


	public HashMap<String, String> getSignaturePhotoName(int idMission) {
        open();
        Cursor cursor = operaDataBase.rawQuery("select S." + TYPE
                +  ", P." + OPERA_Photos_DAO.NAME
				+ " FROM " + OPERA_Photos_DAO.PHOTOS_TABLE + " AS P JOIN "
                + SIGNATURE_TABLE + " AS S ON S."
                + ID_MISSION + " = P." + OPERA_Photos_DAO.MISSION
                + " WHERE P." + OPERA_Photos_DAO.NAME + " LIKE '%Signature%' AND P."
				+ ID_MISSION + " =?",
                new String[]{String.valueOf(idMission)});

		HashMap<String, String> signatureNameMap = new HashMap<>();
        //If we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    if(cursor.getString(1).contains(cursor.getString(0)))
					    signatureNameMap.put(cursor.getString(0), cursor.getString(1));
                } while(cursor.moveToNext());
            }
        }
        if(cursor!=null)
            cursor.close();
        close();
        return signatureNameMap;
    }
}
