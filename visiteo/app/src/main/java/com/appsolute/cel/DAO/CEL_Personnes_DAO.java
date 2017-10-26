package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_Personnes;

import java.util.ArrayList;

/**
 * 
 * @author Lucien Guimaraes
 *
 *This class refer to CEL_Personnes, it's using to access to CEL_Personnes on database 
 *
 */
public class CEL_Personnes_DAO extends CEL_Database_DAO{

	public CEL_Personnes_DAO(Context pContext) {
		super(pContext);
	}

	public static final String PERSONNES_TABLES = "CEL_Personnes";
	public static final String KEY = "idPersonnes";
	public static final String NOM = "Nom";
	public static final String PRENOM = "Prenom";
	public static final String ADRESSE = "Adresse";
	public static final String SUITE = "Suite";
	public static final String CODE_POSTAL = "Cp";
	public static final String VILLE = "Ville";
	public static final String REPRESENTANT = "Representant";
	public static final String EMAIL = "Email";
	public static final String TELEPHONE = "Tel";
	public static final String DATE_ENTREE = "DateSortie";

	public static final String TABLE_CREATE = "CREATE TABLE " + PERSONNES_TABLES + " (" 
			+ KEY + " INTEGER PRIMARY KEY, " 
			+ NOM + " TEXT, " 
			+ PRENOM + " TEXT, " 
			+ ADRESSE + " TEXT, " 
			+ SUITE + " TEXT, "
			+ CODE_POSTAL + " TEXT, "
			+ VILLE + " TEXT, " 
			+ REPRESENTANT + " TEXT, " 
			+ EMAIL + " TEXT, " 
			+ TELEPHONE + " TEXT, "
			+ DATE_ENTREE + " TEXT);" ;

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + PERSONNES_TABLES + ";";


	/**
	 * Insert new value on CEL_Personnes
	 * 
	 * @param personnes
	 */
	public int addValue(CEL_Personnes personnes) {
		int id=0;
		open();
		ContentValues value = new ContentValues(); 
		value.put(NOM, personnes.getNomPersonnes());
		value.put(PRENOM, personnes.getPrenomPersonnes());
		value.put(ADRESSE, personnes.getAdressePersonnes());
		value.put(SUITE, personnes.getSuitePersonnes());
		value.put(CODE_POSTAL, personnes.getCodePostalPersonnes());
		value.put(VILLE, personnes.getVillePersonnes());
		value.put(REPRESENTANT, personnes.getRepresentantPersonnes());
		value.put(EMAIL, personnes.getEmailPersonnes());
		value.put(TELEPHONE, personnes.getTelephonePersonnes());
		value.put(DATE_ENTREE, personnes.getDateEntree());
		id = (int)operaDataBase.insert(PERSONNES_TABLES, null, value);
		close();
		return id;
	}

	/**
	 * Delete a CEL_Personnes value from an Id
	 * @param idPersonnes
	 */
	public void deleteValue(int idPersonnes) {
		open();
		operaDataBase.delete(PERSONNES_TABLES, KEY + " = ?", new String[]{String.valueOf(idPersonnes)});
		close();
	}


	/**
	 * Update/Modify a CEL_Biens
	 * @param personnes
	 */

	public void updateValue(CEL_Personnes personnes) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(NOM, personnes.getNomPersonnes());
		value.put(PRENOM, personnes.getPrenomPersonnes());
		value.put(ADRESSE, personnes.getAdressePersonnes());
		value.put(SUITE, personnes.getSuitePersonnes());
		value.put(CODE_POSTAL, personnes.getCodePostalPersonnes());
		value.put(VILLE, personnes.getVillePersonnes());
		value.put(REPRESENTANT, personnes.getRepresentantPersonnes());
		value.put(EMAIL, personnes.getEmailPersonnes());
		value.put(TELEPHONE, personnes.getTelephonePersonnes());
		value.put(DATE_ENTREE, personnes.getDateEntree());
		operaDataBase.update(PERSONNES_TABLES, value, KEY + " = ?", 
				new String[] {String.valueOf(personnes.getIdPersonnes())});
		close();
	}

	/**
	 * Select a specific CEL_Personnes
	 * @param idPersonnes
	 */
	public CEL_Personnes select(int idPersonnes) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + PERSONNES_TABLES + " where " + KEY + "= ?", new String[]
				{String.valueOf(idPersonnes)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Biens object
					CEL_Personnes personnes = new CEL_Personnes();
					personnes.setIdPersonnes(Integer.parseInt(cursor.getString(0)));
					personnes.setNomPersonnes(cursor.getString(1));
					personnes.setPrenomPersonnes(cursor.getString(2));
					personnes.setAdressePersonnes(cursor.getString(3));
					personnes.setSuitePersonnes(cursor.getString(4));
					personnes.setCodePostalPersonnes(cursor.getString(5));
					personnes.setVillePersonnes(cursor.getString(6));
					personnes.setRepresentantPersonnes(cursor.getString(7));
					personnes.setEmailPersonnes(cursor.getString(8));
					personnes.setTelephonePersonnes(cursor.getString(9));
					personnes.setDateEntree(cursor.getString(10));
					return personnes;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
        return new CEL_Personnes();
	}
	
	public ArrayList<CEL_Personnes> selectPersonnes(int idMission) {
		ArrayList<CEL_Personnes> personnes = new ArrayList<>();
		open();

		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + PERSONNES_TABLES + " AS P JOIN "
				+ CEL_Mission_Personnes_DAO.MISSION_PERSONNES_TABLE + " AS M ON P."
                + KEY + " =  M." + CEL_Mission_Personnes_DAO.PERSONNE_KEY
                + " WHERE M." + CEL_Mission_Personnes_DAO.MISSION_KEY + " = ?",
                new String[]{String.valueOf(idMission)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				do {
					if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
						//Build CEL_Biens object
						CEL_Personnes personne = new CEL_Personnes();
						personne.setIdPersonnes(cursor.getInt(0));
						personne.setNomPersonnes(cursor.getString(1));
						personne.setPrenomPersonnes(cursor.getString(2));
						personne.setAdressePersonnes(cursor.getString(3));
						personne.setSuitePersonnes(cursor.getString(4));
						personne.setCodePostalPersonnes(cursor.getString(5));
						personne.setVillePersonnes(cursor.getString(6));
						personne.setRepresentantPersonnes(cursor.getString(7));
						personne.setEmailPersonnes(cursor.getString(8));
						personne.setTelephonePersonnes(cursor.getString(9));
						personne.setDateEntree(cursor.getString(10));
                        personne.setTypePersonnes(cursor.getInt(13));
						personnes.add(personne);
					}
				} while(cursor.moveToNext());
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return personnes;
	}
}
