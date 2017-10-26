package com.appsolute.cel.DAO;

import java.util.ArrayList;
import java.util.List;

import com.appsolute.cel.models.CEL_Actions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CEL_Actions_DAO extends CEL_Database_DAO {

	public CEL_Actions_DAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	public static final String ACTIONS_TABLE = "CEL_Actions";
	public static final String KEY = "idActions";
	public static final String ACTION_ACTIONS = "actionActions";
	public static final String QUANTITE_ACTIONS = "quantiteActions";
	public static final String UNITE_ACTIONS = "uniteActions";
	public static final String NOTE_ACTIONS = "noteActions";
	public static final String ID_ELEMENT = "idElement";

	public static final String TABLE_CREATE = "CREATE TABLE " + ACTIONS_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ ACTION_ACTIONS + " TEXT, " 
			+ QUANTITE_ACTIONS + " REAL, " 
			+ UNITE_ACTIONS + " TEXT, " 
			+ NOTE_ACTIONS + " TEXT, "
			+ ID_ELEMENT + " INTEGER, "
			+ " FOREIGN KEY ("+ID_ELEMENT+") REFERENCES "+CEL_Elements_DAO.ELEMENTS_TABLE+" ("+CEL_Elements_DAO.KEY+"));" ;

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ACTIONS_TABLE + ";";

	/**
	 * Insert new value on CEL_Actions
	 * 
	 * @param action
	 */
	public void addValue(CEL_Actions action) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(ACTION_ACTIONS, action.getActionActions());
		value.put(QUANTITE_ACTIONS, action.getQuantiteActions());
		value.put(UNITE_ACTIONS, action.getUniteActions());
		value.put(NOTE_ACTIONS, action.getNoteActions());
		value.put(ID_ELEMENT, action.getIdElement());
		operaDataBase.insert(ACTIONS_TABLE, null, value);
		close();
	}


	/**
	 * Delete an CEL_Actions value from an Id
	 * @param idAction
	 */
	public void deleteValue(int idAction) {
		open();
		operaDataBase.delete(ACTIONS_TABLE, KEY + " = ?", new String[]{String.valueOf(idAction)});
		close();
	}

	public void deleteValueFromIdElement(int idElement) {
		open();
		operaDataBase.delete(ACTIONS_TABLE, ID_ELEMENT + " = ?", new String[]{String.valueOf(idElement)});
		close();
	}



	/**
	 * Update/Modify a CEL_Actions
	 * @param action
	 */
	public void updateValue(CEL_Actions action) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(ACTION_ACTIONS, action.getActionActions());
		value.put(QUANTITE_ACTIONS, action.getQuantiteActions());
		value.put(UNITE_ACTIONS, action.getUniteActions());
		value.put(NOTE_ACTIONS, action.getNoteActions());
		value.put(ID_ELEMENT, action.getIdElement());
		//operaDataBase.insert(ACTIONS_TABLE, null, value);
		operaDataBase.update(ACTIONS_TABLE, value, KEY + " = ?", new String[]{String.valueOf(action.getIdActions())});
		close();
	}


	/**
	 * Select a specific CEL_Actions
	 * @param idAction
	 */
	public CEL_Actions select(int idAction) {
		open();
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + ACTIONS_TABLE + " WHERE " + KEY + "= ?", new String[]
				{String.valueOf(idAction)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build action object
					CEL_Actions action = new CEL_Actions();
					action.setIdActions(Integer.parseInt(cursor.getString(0)));
					action.setActionActions(cursor.getString(1));
					action.setQuantiteActions(Float.parseFloat(cursor.getString(2)));
					action.setUniteActions(cursor.getString(3));
					action.setNoteActions(cursor.getString(4));
					action.setIdElement(Integer.parseInt(cursor.getString(5)));
					return action;
				}
			}
		}

		if(cursor!=null)
			cursor.close();
		close();
		CEL_Actions action = new CEL_Actions();
		return action;
	}

	public CEL_Actions selectActions(int idElement) {
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + ACTIONS_TABLE + " WHERE " + ID_ELEMENT + "= ?", new String[]
				{String.valueOf(idElement)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build action object
					CEL_Actions action = new CEL_Actions();
					action.setIdActions(Integer.parseInt(cursor.getString(0)));
					action.setActionActions(cursor.getString(1));
					action.setQuantiteActions(Float.parseFloat(cursor.getString(2)));
					action.setUniteActions(cursor.getString(3));
					action.setNoteActions(cursor.getString(4));
					action.setIdElement(Integer.parseInt(cursor.getString(5)));
					return action;
				}
			}
		}

		if(cursor!=null)
			cursor.close();
		return null;
	}

	/**
	 * Select all CEL_Actions with same ideEement
	 * 
	 * @param idElement
	 */
	public List<CEL_Actions> selectAllActionFromElement(int idElement) {
		open();
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + ACTIONS_TABLE + " WHERE " + ID_ELEMENT + "= ?", new String[]
				{String.valueOf(idElement)});

		List<CEL_Actions> cel_Actions_List = new ArrayList<CEL_Actions>();
		//If we got results get the first one
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (cursor.isAfterLast() == false) {
					//Build CEL_Actions object
					CEL_Actions action = new CEL_Actions();
					action.setIdActions(Integer.parseInt(cursor.getString(0)));
					action.setActionActions(cursor.getString(1));
					action.setQuantiteActions(Float.parseFloat(cursor.getString(2)));
					action.setUniteActions(cursor.getString(3));
					action.setNoteActions(cursor.getString(4));
					action.setIdElement(Integer.parseInt(cursor.getString(5)));
					cel_Actions_List.add(action);
				}
			}
		}

		if(cursor!=null)
			cursor.close();
		close();
		if(cel_Actions_List.size() > 0)
			return cel_Actions_List;
		else
			return null;
	}

	/**
	 * Return actions of a CEL_Elements on a String
	 * 
	 * @param idElements
	 */
	public String stringCel_Actions(int idElements) {
		open();
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + ACTIONS_TABLE + " WHERE " + ID_ELEMENT + "= ?", new String[]
				{String.valueOf(idElements)});

		String actionsString = "";
		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					actionsString = cursor.getString(1);
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return actionsString;
	}
}
