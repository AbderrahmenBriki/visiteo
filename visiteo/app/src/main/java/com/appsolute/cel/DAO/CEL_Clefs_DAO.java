package com.appsolute.cel.DAO;

import java.util.ArrayList;

import com.appsolute.cel.models.CEL_Clefs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * 
  * @author Lucien Guimaraes
 *
 * This class refer to CEL_Clefs, it's using to access to CEL_Clefs on database 
 *
 */
public class CEL_Clefs_DAO extends CEL_Database_DAO{

	public CEL_Clefs_DAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	private ArrayList<CEL_Clefs> clefsList;
	
	public static final String CLEFS_TABLES = "CEL_Clefs_DAO";
	public static final String KEY = "idClefs";
	public static final String TYPE = "typeClefs";
	public static final String NOMBRE_VERIFIEE = "nombreClefVerifiee";
	public static final String HS = "hsClefs";
	public static final String NOMBRE_NON_VERIFIEE = "nombreClefNonVerifiee";
	public static final String BIEN_KEY = "idMission";
	public static final String TOTAL = "total";
	public static final String COMMENT = "commentaire";

	public static final String TABLE_CREATE = "CREATE TABLE " + CLEFS_TABLES + " (" 
			+ KEY + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, " 
			+ TYPE + " INTEGER, " 
			+ NOMBRE_VERIFIEE + " INTEGER, " 
			+ HS + " BOOL, " 
			+ NOMBRE_NON_VERIFIEE + " INTEGER, "
			+ BIEN_KEY + " INTEGER, "
			+ TOTAL + " INTEGER, " 
			+ COMMENT + " TEXT, " 
			+ " FOREIGN KEY ("+CEL_Mission_DAO.KEY+") REFERENCES "+CEL_Mission_DAO.MISSION_TABLE+" ("+CEL_Mission_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + CLEFS_TABLES + ";";


	/**
	 * Insert new value on CEL_Clefs
	 * 
	 * @param clefs
	 */
	public void addValue(CEL_Clefs clefs) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(TYPE, clefs.getTypeClefs());
		value.put(NOMBRE_VERIFIEE, clefs.getNombreClefVerifiee());
		value.put(HS, clefs.isHsClefs());
		value.put(NOMBRE_NON_VERIFIEE, clefs.getNombreClefNonVerifiee());
		value.put(BIEN_KEY, clefs.getIdMission());
		value.put(TOTAL, clefs.getTotal());
		value.put(COMMENT, clefs.getComment());
		operaDataBase.insert(CLEFS_TABLES, null, value);
		close();
	}

	/**
	 * Delete a CEL_Clefs value from an idClefs
	 * 
	 * @param idClefs
	 */
	public void deleteValue(int idClefs) {
		open();
		operaDataBase.delete(CLEFS_TABLES, KEY + " = ?", new String[]{String.valueOf(idClefs)});
		close();
	}
	
	/**
	 * Delete a CEL_Clefs value from an idClefs
	 * 
	 * @param idClefs
	 */
	public void deleteAllFromMission(int idMission) {
		open();
		operaDataBase.delete(CLEFS_TABLES, BIEN_KEY + " = ?", new String[]{String.valueOf(idMission)});
		close();
	}


	/**
	 * Update/Modify a CEL_Clefs
	 * 
	 * @param clefs
	 */

	public void updateValue(CEL_Clefs clefs) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(TYPE, clefs.getTypeClefs());
		value.put(NOMBRE_VERIFIEE, clefs.getNombreClefVerifiee());
		value.put(HS, clefs.isHsClefs());
		value.put(NOMBRE_NON_VERIFIEE, clefs.getNombreClefNonVerifiee());
		value.put(BIEN_KEY, clefs.getIdMission());
		value.put(TOTAL, clefs.getTotal());
		value.put(COMMENT, clefs.getComment());
		int hs=0;
		if(clefs.isHsClefs())
			hs=1;
		String query ="UPDATE CEL_Clefs_DAO SET typeClefs ="+clefs.getTypeClefs()+", nombreClefVerifiee ="+clefs.getNombreClefVerifiee()
				+", hsClefs ="+hs+", nombreClefNonVerifiee ="+clefs.getNombreClefNonVerifiee()
				+", total ="+clefs.getTotal()+", commentaire ='"+clefs.getComment()
				+"' WHERE idClefs ="+clefs.getIdClefs();
		Log.d("Query ", "===="+query);
		//operaDataBase.rawQuery(query, null);
		operaDataBase.update(CLEFS_TABLES, value, KEY + " = ?", 
				new String[] {String.valueOf(clefs.getIdClefs())});
		close();
	}

	/**
	 * Select a specific CEL_Clefs
	 * 
	 * @param idClefs
	 */
	public CEL_Clefs select(int idClefs) {
		open();		
		Cursor cursor = operaDataBase.rawQuery("select * from " + CLEFS_TABLES + " where " + KEY + "= ?", new String[]
				{String.valueOf(idClefs)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Clefs object
					CEL_Clefs clefs = new CEL_Clefs();
					clefs.setIdClefs(Integer.parseInt(cursor.getString(0)));
					clefs.setTypeClefs(Integer.parseInt(cursor.getString(1)));
					clefs.setNombreClefVerifiee(Integer.parseInt(cursor.getString(2)));
					Log.d("DB HR", "===="+cursor.getString(3));
					if(cursor.getString(3).equals("1"))
						clefs.setHsClefs(true);
					else
						clefs.setHsClefs(false);	
					clefs.setNombreClefNonVerifiee(Integer.parseInt(cursor.getString(4)));
					clefs.setIdMission(Integer.parseInt(cursor.getString(5)));
					clefs.setTotal(Integer.parseInt(cursor.getString(6)));
					clefs.setComment(cursor.getString(7));
					return clefs;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return null;
	}
	
	/**
	 * Select all CEL_Clefs for an idMission
	 * 
	 * @param idMission
	 */
	public ArrayList<CEL_Clefs> selectAllFromBiens(int idMission) {
		open();
		clefsList = new ArrayList<CEL_Clefs>();
		
		Cursor cursor = operaDataBase.rawQuery("select * from " + CLEFS_TABLES + " where " + BIEN_KEY + " = ?", new String[]
				{String.valueOf(idMission)});

		//If we got results get the first one
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (cursor.isAfterLast() == false) {
					//Build CEL_Clefs object
					CEL_Clefs clefs = new CEL_Clefs();
					clefs.setIdClefs(Integer.parseInt(cursor.getString(0)));
					clefs.setTypeClefs(Integer.parseInt(cursor.getString(1)));
					clefs.setNombreClefVerifiee(Integer.parseInt(cursor.getString(2)));
					Log.d("DB HR", "===="+cursor.getString(3));
					if(cursor.getString(3).equals("1"))
						clefs.setHsClefs(true);
					else
						clefs.setHsClefs(false);						
					clefs.setNombreClefNonVerifiee(Integer.parseInt(cursor.getString(4)));
					clefs.setIdMission(Integer.parseInt(cursor.getString(5)));
					clefs.setTotal(Integer.parseInt(cursor.getString(6)));
					clefs.setComment(cursor.getString(7));
					clefsList.add(clefs);
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return clefsList;
	}
}
