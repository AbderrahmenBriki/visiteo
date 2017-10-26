package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_ECS;

public class CEL_ECS_DAO extends CEL_Database_DAO {

	public CEL_ECS_DAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}
	
	public static final String ECS_TABLE = "CEL_ECS";
	public static final String KEY = "idECS";
	public static final String TYPE = "typeECS";
	public static final String MARQUE = "marqueECS";

	public static final String TABLE_CREATE = "CREATE TABLE " + ECS_TABLE + " (" 
			+ KEY + " INTEGER PRIMARY KEY NOT NULL, " 
			+ TYPE + " INTEGER, "
			+ MARQUE + " INTEGER);";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ECS_TABLE + ";";

	/**
	 * Insert new value on CEL_ECS
	 * 
	 * @param ecs
	 */
	public int addValue(CEL_ECS ecs) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(TYPE, ecs.getTypeEcs());
		value.put(MARQUE, ecs.getMarqueEcs());
		return (int)operaDataBase.insert(ECS_TABLE, null, value);

	}

	/**
	 * Delete a CEL_ECS value from an Id
	 * @param idECS
	 */
	public void deleteValue(int idECS) {
		operaDataBase.delete(ECS_TABLE, KEY + " = ?", new String[]{String.valueOf(idECS)});
	}

	/**
	 * Update value on existing CEL_ECS
	 * 
	 * @param ecs
	 */
	public void updateValue(CEL_ECS ecs){
		open();
		ContentValues value = new ContentValues(); 
		value.put(TYPE, ecs.getTypeEcs());
		value.put(MARQUE, ecs.getMarqueEcs());
		operaDataBase.update(ECS_TABLE, value, KEY + " = ?", new String[]{String.valueOf(ecs.getIdECS())});
		close();
	}

	/**
	 * Select a specific CEL_ECS
	 * @param idECS
	 */
	public CEL_ECS select(int idECS) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ECS_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idECS)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_ECS object
					CEL_ECS ecs = new CEL_ECS();
					ecs.setIdECS(Integer.parseInt(cursor.getString(0)));
					ecs.setTypeEcs(Integer.parseInt(cursor.getString(1)));
					ecs.setMarqueEcs(Integer.parseInt(cursor.getString(2)));
					return ecs;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return null;
	}
}
