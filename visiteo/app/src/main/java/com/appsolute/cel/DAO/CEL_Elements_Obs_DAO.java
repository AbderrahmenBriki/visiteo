package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_Elements_Obs;

import java.util.ArrayList;
import java.util.List;

public class CEL_Elements_Obs_DAO extends CEL_Database_DAO {

	public CEL_Elements_Obs_DAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	public static final String ELEMENTS_OBS_TABLE = "CEL_Elements_Obs";
	public static final String KEY = "idElements_Obs";
	public static final String OBS = "obs";
	public static final String ELEMENT = "idElement";


	public static final String TABLE_CREATE = "CREATE TABLE " + ELEMENTS_OBS_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, "
			+ OBS + " TEXT, "
			+ ELEMENT + " INTEGER, "
			+ " FOREIGN KEY ("+ELEMENT+") REFERENCES "+CEL_Elements_DAO.ELEMENTS_TABLE+" ("+CEL_Elements_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ELEMENTS_OBS_TABLE + ";";

	/**+
	 * Insert new value on CEL_Elements_Obs
	 * 
	 * @param elements_Obs
	 */
	public int addValue(CEL_Elements_Obs elements_Obs) {
		if(elements_Obs.getObsElementsObs().isEmpty()){
			return -1;
		}
		open();
		ContentValues value = new ContentValues();
		value.put(OBS, elements_Obs.getObsElementsObs());
		value.put(ELEMENT, elements_Obs.getIdElements());
		return (int) operaDataBase.insert(ELEMENTS_OBS_TABLE, null, value);
	}

	/**
	 * Delete a CEL_Elements_Obs value from an Id test
	 * @param idElements_Obs
	 */
	public void deleteValue(int idElements_Obs) {
		open();
		operaDataBase.delete(ELEMENTS_OBS_TABLE, KEY + " = ?", new String[]{String.valueOf(idElements_Obs)});
		close();
	}

	/**
	 * Delete all CEL_Elements_Obs value from an idElements
	 * @param idElements
	 */
	public void deleteValueData(int idElements) {
		open();
		operaDataBase.delete(ELEMENTS_OBS_TABLE, ELEMENT + " = ?",new String[]{String.valueOf(idElements)});
		close();
	}
	
	/**
	 * Delete all CEL_Elements_Obs value from an idElements
	 * @param obs
	 */
	public int deleteValueFromString(String obs, int idElements) {
		return operaDataBase.delete(ELEMENTS_OBS_TABLE, OBS + " = ? and "
                + ELEMENT + " = ?",new String[]{obs, String.valueOf(idElements)});
	}

	/**
	 * Update value on existing CEL_Elements_Obs
	 * 
	 * @param elements_Obs
	 */
	public void updateValue(CEL_Elements_Obs elements_Obs){
		open();
		ContentValues value = new ContentValues();
		value.put(OBS, elements_Obs.getObsElementsObs());
		value.put(ELEMENT, elements_Obs.getIdElements());
		operaDataBase.insert(ELEMENTS_OBS_TABLE, null, value);
		close();
	}

	/**
	 * Select list of CEL_Elements_Obs corresponding to idElement
	 * @param idElements
	 */
	public List<CEL_Elements_Obs> selectAllCel_Elements_Obs(int idElements) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ELEMENTS_OBS_TABLE + " where " + ELEMENT + "= ?", new String[]
				{String.valueOf(idElements)});

		List<CEL_Elements_Obs> cel_Elements_Obs_List = new ArrayList<CEL_Elements_Obs>();
		//If we got results get the first one
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					//Build CEL_Elements_Obs object
					CEL_Elements_Obs elements_Obs = new CEL_Elements_Obs();
					elements_Obs.setIdElementsObs(cursor.getInt(0));
					elements_Obs.setObsElementsObs(cursor.getString(1));
					elements_Obs.setIdElements(cursor.getInt(2));
					cel_Elements_Obs_List.add(elements_Obs);
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return cel_Elements_Obs_List;
	}

	/**
	 * Return all observations of a CEL_Elements on a String
	 * 
	 * @param idElements
	 */
	public String getObservationsString(int idElements) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ELEMENTS_OBS_TABLE + " where " + ELEMENT + "= ?", new String[]
				{String.valueOf(idElements)});

		String observationsString = "";
		//If we got results get the first one
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					if(observationsString.equals(""))
						observationsString = cursor.getString(1);
					else
						observationsString = observationsString + "," + cursor.getString(1);
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return observationsString;
	}
	
	/**
	 * Return true if CEL_Element_Obs with description already exist on database
	 * 
	 * @param idElements 
	 * @param description
	 */
	public Boolean isOnDatabase(int idElements, String description) {
		Cursor cursor = operaDataBase.rawQuery("select * from " + ELEMENTS_OBS_TABLE + " where " + OBS + "= ? and " + ELEMENT + "= ?", new String[]
				{description, String.valueOf(idElements)});
		
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				cursor.close();
				return true;
			}
			else {
				cursor.close();
				return false;
			}
		}
		return false;
	}
	
	
	/**
	 * Return all observations of a CEL_Elements on a List<String>
	 * 
	 * @param idElements
	 */
	public List<String> getObservationsList(int idElements) {
		open();
		List<String> observationsList = new ArrayList<>();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ELEMENTS_OBS_TABLE + " where " + ELEMENT + "= ?", new String[]
				{String.valueOf(idElements)});

		//If we got results get the first one
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					observationsList.add(cursor.getString(1));
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return observationsList;
	}

}
