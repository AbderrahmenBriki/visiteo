package com.appsolute.cel.DAO;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.appsolute.cel.models.CEL_InfoCompteurs;

public class CEL_InfoCompteurs_DAO extends CEL_Database_DAO{
	
	private ArrayList<CEL_InfoCompteurs> compteurList;

	public CEL_InfoCompteurs_DAO(Context pContext) {
		super(pContext);
	}

	public static final String INFO_COMPTEURS_TABLES = "CEL_InfoCompteurs";
	public static final String KEY = "id";
	public static final String NUMERO = "numero";
	public static final String COMMANDE = "commande";
	public static final String COMPTEUR = "compteur";
	public static final String CHAUFFAGE = "chauffage";
	public static final String EMPLACEMENT = "emplacement";
	public static final String MISSION = "idMission";

	public static final String TABLE_CREATE = "CREATE TABLE " + INFO_COMPTEURS_TABLES + " (" 
			+ KEY + " INTEGER PRIMARY KEY, " 
			+ NUMERO + " INTEGER, " 
			+ COMMANDE + " TEXT, "
			+ COMPTEUR + " TEXT, "				
			+ CHAUFFAGE + " TEXT, "
			+ EMPLACEMENT + " TEXT, " 
			+ MISSION + " INTEGER, " 
			+ " FOREIGN KEY("+MISSION+") REFERENCES " + CEL_Mission_DAO.MISSION_TABLE +"("+CEL_Mission_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + INFO_COMPTEURS_TABLES + ";";

	public int addValue(CEL_InfoCompteurs compteurs) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(NUMERO, compteurs.getNumero());
		value.put(COMMANDE, compteurs.getCommande());
		value.put(COMPTEUR, compteurs.getCompteur());
		value.put(CHAUFFAGE, compteurs.getChauffage());
		value.put(EMPLACEMENT, compteurs.getEmplacement());	
		value.put(MISSION, compteurs.getIdMission());	
		return (int)operaDataBase.insert(INFO_COMPTEURS_TABLES, null, value);
	}

	public void deleteValue(int idCompteurs) {
		open();
		operaDataBase.delete(INFO_COMPTEURS_TABLES, KEY + " = ?", new String[]{String.valueOf(idCompteurs)});
		close();
	}
	
	public void deleteValueAll(int idMission) {
		open();
		operaDataBase.delete(INFO_COMPTEURS_TABLES, MISSION + " = ?", new String[]{String.valueOf(idMission)});
		close();
	}

	public void updateValue(CEL_InfoCompteurs compteurs) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(NUMERO, compteurs.getNumero());
		value.put(COMMANDE, compteurs.getCommande());
		value.put(COMPTEUR, compteurs.getCompteur());
		value.put(CHAUFFAGE, compteurs.getChauffage());
		value.put(EMPLACEMENT, compteurs.getEmplacement());	
		value.put(MISSION, compteurs.getIdMission());	
		operaDataBase.update(INFO_COMPTEURS_TABLES, value, KEY + " = ?", 
				new String[] {String.valueOf(compteurs.getId())});
		close();
	}

	
	public CEL_InfoCompteurs select(int idCompteurs) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + INFO_COMPTEURS_TABLES + " where " + KEY + "= ?", new String[]
				{String.valueOf(idCompteurs)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Compteur object
					CEL_InfoCompteurs compteurs = new CEL_InfoCompteurs();
					compteurs.setId(Integer.parseInt(cursor.getString(0)));
					compteurs.setNumero(Integer.parseInt(cursor.getString(1)));
					compteurs.setCommande(cursor.getString(2));
					compteurs.setCompteur(cursor.getString(3));
					compteurs.setChauffage(cursor.getString(4));
					compteurs.setEmplacement(cursor.getString(5));
					compteurs.setIdMission(Integer.parseInt(cursor.getString(6)));
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
	public ArrayList<CEL_InfoCompteurs> selectAllCEL_InfoCompteurs(int idMission) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + INFO_COMPTEURS_TABLES + " where " + MISSION + "= ?", new String[]
				{String.valueOf(idMission)});
		compteurList = new ArrayList<CEL_InfoCompteurs>();
		
		if (cursor != null) {
			Log.d("idMission", "===="+idMission);
			if (cursor.moveToFirst()) {
				while (cursor.isAfterLast() == false) {
					CEL_InfoCompteurs compteurs = new CEL_InfoCompteurs();
					compteurs.setId(Integer.parseInt(cursor.getString(0)));
					compteurs.setNumero(Integer.parseInt(cursor.getString(1)));
					compteurs.setCommande(cursor.getString(2));
					compteurs.setCompteur(cursor.getString(3));
					compteurs.setChauffage(cursor.getString(4));
					compteurs.setEmplacement(cursor.getString(5));
					compteurs.setIdMission(Integer.parseInt(cursor.getString(6)));
					compteurList.add(compteurs);
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return compteurList; 
	}
}
