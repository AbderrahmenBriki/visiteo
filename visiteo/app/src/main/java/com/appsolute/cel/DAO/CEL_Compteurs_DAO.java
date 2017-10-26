package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.appsolute.cel.models.CEL_Compteurs;

import java.util.ArrayList;

/**
 * 
 * @author Lucien Guimaraes
 *
 * This class refer to CEL_Compteurs it's using to access to CEL_Compteurs on database 
 *
 */
public class CEL_Compteurs_DAO extends CEL_Database_DAO{
	
	private ArrayList<CEL_Compteurs> compteurList;

	public CEL_Compteurs_DAO(Context pContext) {
		super(pContext);
	}

	public static final String COMPTEURS_TABLES = "CEL_Compteurs";
	public static final String KEY = "idCompteur";
	public static final String INDEX_1 = "Index_1";
	public static final String INDEX_2 = "Index_2";
	public static final String TYPE = "Type";
	public static final String MISSION = "idMission";
	public static final String ETAT = "idEtat";
	public static final String ID_PHOTO = "idPhoto";

	public static final String TABLE_CREATE = "CREATE TABLE " + COMPTEURS_TABLES + " (" 
			+ KEY + " INTEGER PRIMARY KEY, " 
			+ INDEX_1 + " INTEGER, " 
			+ INDEX_2 + " INTEGER, "
			+ TYPE + " INTEGER, "				
			+ MISSION + " INTEGER, "
			+ ETAT + " INTEGER, " 
			+ ID_PHOTO + " INTEGER, "
			+ " FOREIGN KEY("+ETAT+") REFERENCES " + CEL_Etat_DAO.ETAT_TABLE +"("+CEL_Etat_DAO.KEY+"), "
			+ " FOREIGN KEY("+MISSION+") REFERENCES " + CEL_Mission_DAO.MISSION_TABLE +"("+CEL_Mission_DAO.KEY+"), "
			+ " FOREIGN KEY("+ID_PHOTO+") REFERENCES " + OPERA_Photos_DAO.PHOTOS_TABLE + "(" + OPERA_Photos_DAO.KEY + "));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + COMPTEURS_TABLES + ";";

	/**
	 * Insert new value on CEL_Compteurs
	 * 
	 * @param compteurs
	 * 
	 * @return idCompteur
	 */
	public int addValue(CEL_Compteurs compteurs) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(INDEX_1, compteurs.getIndex_1Compteurs());
		value.put(INDEX_2, compteurs.getIndex_2Compteurs());
		value.put(TYPE, compteurs.getTypeCompteurs());
		value.put(MISSION, compteurs.getIdMission());	
		value.put(ETAT, compteurs.getIdEtat());
		value.put(ID_PHOTO, compteurs.getIdPhoto());
		return (int)operaDataBase.insert(COMPTEURS_TABLES, null, value);
	}

	/**
	 * Delete a CEL_Compteurs value from an idCompteurs
	 * 
	 * @param idCompteurs
	 */
	public void deleteValue(int idCompteurs) {
		open();
		operaDataBase.delete(COMPTEURS_TABLES, KEY + " = ?", new String[]{String.valueOf(idCompteurs)});
		close();
	}


	/**
	 * Update/Modify a CEL_Compteurs
	 * 
	 * @param compteurs
	 */
	public void updateValue(CEL_Compteurs compteurs) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(INDEX_1, compteurs.getIndex_1Compteurs());
		value.put(INDEX_2, compteurs.getIndex_2Compteurs());
		value.put(TYPE, compteurs.getTypeCompteurs());
		value.put(MISSION, compteurs.getIdMission());
		value.put(ETAT, compteurs.getIdEtat());
		value.put(ID_PHOTO, compteurs.getIdPhoto());
		operaDataBase.update(COMPTEURS_TABLES, value, KEY + " = ?", 
				new String[] {String.valueOf(compteurs.getIdCompteurs())});
		close();
	}

	
	/**
	 * Select a specific CEL_Compteurs
	 * 
	 * @param idCompteurs
	 */
	public CEL_Compteurs select(int idCompteurs) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + COMPTEURS_TABLES + " where " + KEY + "= ?", new String[]
				{String.valueOf(idCompteurs)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Compteur object
					CEL_Compteurs compteurs = new CEL_Compteurs();
					compteurs.setIdCompteurs(Integer.parseInt(cursor.getString(0)));
					compteurs.setIndex_1Compteurs(Integer.parseInt(cursor.getString(1)));
					compteurs.setIndex_2Compteurs(Integer.parseInt(cursor.getString(2)));
					compteurs.setTypeCompteurs(Integer.parseInt(cursor.getString(3)));
					compteurs.setIdMission(Integer.parseInt(cursor.getString(4)));
					compteurs.setIdEtat(Integer.parseInt(cursor.getString(5)));
					compteurs.setIdPhoto(Integer.parseInt(cursor.getString(6)));
					return compteurs;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return null;
	}
	
	/**
	 * Select all CEL_Compteurs for specific idMission 
	 * 
	 * @param idMission
	 */
	public ArrayList<CEL_Compteurs> selectAllCEL_Compteurs(int idMission) {
		open();
		Log.d("Query", "==="+"SELECT * FROM " + COMPTEURS_TABLES + " WHERE " + MISSION + "= "+idMission);
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + COMPTEURS_TABLES + " WHERE " + MISSION + "= ?", new String[]
				{String.valueOf(idMission)});
		compteurList = new ArrayList<>();
		
		if (cursor != null) {
			Log.d("idMission", "===="+idMission);
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					CEL_Compteurs compteur = new CEL_Compteurs();
					compteur.setIdCompteurs(Integer.parseInt(cursor.getString(0)));
					compteur.setIndex_1Compteurs(Integer.parseInt(cursor.getString(1)));
					compteur.setIndex_2Compteurs(Integer.parseInt(cursor.getString(2)));
					compteur.setTypeCompteurs(Integer.parseInt(cursor.getString(3)));
					compteur.setIdMission(Integer.parseInt(cursor.getString(4)));
					compteur.setIdEtat(Integer.parseInt(cursor.getString(5)));
					compteur.setIdPhoto(Integer.parseInt(cursor.getString(6)));
					compteurList.add(compteur);
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return compteurList; 
	}
	
	public ArrayList<CEL_Compteurs> selectData(int idCompteurs) {
		ArrayList<CEL_Compteurs> compteurs = new ArrayList<>();

		String ETATTAB = CEL_Etat_DAO.ETAT_TABLE;
		String KEY1 = CEL_Etat_DAO.KEY;
		String DESCRIPTION = CEL_Etat_DAO.DESCRIPTION;
		
		open();
		Cursor cursor = operaDataBase.rawQuery("SELECT "+COMPTEURS_TABLES+".*, "+ETATTAB+"."+DESCRIPTION+" FROM " + 
				COMPTEURS_TABLES+" JOIN "+ETATTAB+" ON "+COMPTEURS_TABLES+"."+ETAT+" = "+ETATTAB+"."+KEY1+
				" WHERE " + KEY + "= ?", new String[]
				{String.valueOf(idCompteurs)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						//Build CEL_Compteur object
						CEL_Compteurs compteur = new CEL_Compteurs();
						compteur.setIdCompteurs(Integer.parseInt(cursor.getString(0)));
						compteur.setIndex_1Compteurs(Integer.parseInt(cursor.getString(1)));
						compteur.setIndex_2Compteurs(Integer.parseInt(cursor.getString(2)));
						compteur.setTypeCompteurs(Integer.parseInt(cursor.getString(3)));
						compteur.setIdMission(Integer.parseInt(cursor.getString(4)));
						compteur.setIdEtat(Integer.parseInt(cursor.getString(5)));
						compteur.setIdPhoto(Integer.parseInt(cursor.getString(6)));
						compteurs.add(compteur);
					}
				} while (cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return compteurs;
	}
}
