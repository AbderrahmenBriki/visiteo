package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_Biens;

/**
 * 
 * @author Lucien Guimaraes
 *
 * This class refer to CEL_BIEN, it's using to access to CEL_BIEN on database 
 *
 */
public class CEL_Biens_DAO extends CEL_Database_DAO{

	public CEL_Biens_DAO(Context pContext) {
		super(pContext);
	}

	public static final String BIENS_TABLE = "CEL_Biens";
	public static final String KEY = "idBien";
	public static final String ADRESSE = "Adresse";
	public static final String SUITE = "Suite";
	public static final String CODE_POSTAL = "Cp";
	public static final String VILLE = "Ville";
	public static final String TYPE = "Type";
	public static final String PIECES = "Pieces";
	public static final String SURFACE = "Surface";
	public static final String ETAGE = "Etage";
	public static final String DIGICODE = "Digicode";
	public static final String IMMEUBLE = "Immeuble";
	public static final String LOT = "Lot";
	public static final String MANDAT = "Mandat";
	public static final String INTERIEUR = "interieur";

	public static final String TABLE_CREATE = "CREATE TABLE " + BIENS_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ ADRESSE + " TEXT, " 
			+ SUITE + " TEXT, " 
			+ CODE_POSTAL + " TEXT, " 
			+ VILLE + " TEXT, "
			+ TYPE + " INTEGER, " 
			+ PIECES + " INTEGER, " 
			+ SURFACE + " REAL, " 
			+ ETAGE + " TEXT, " 
			+ DIGICODE + " TEXT, " 
			+ IMMEUBLE + " TEXT, " 
			+ LOT + " TEXT, " 
			+ MANDAT + " TEXT, "
			+ INTERIEUR + " TEXT);";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + BIENS_TABLE + ";";

	/**
	 * Insert new value on CEL_Biens
	 * 
	 * @param biens
	 */
	public int addValue(CEL_Biens biens) {
		open();
		ContentValues value = new ContentValues();
		value.put(ADRESSE, biens.getAdresseBiens());
		value.put(SUITE, biens.getSuiteBiens());
		value.put(CODE_POSTAL, biens.getCodePostalBiens());
		value.put(VILLE, biens.getVilleBiens());
		value.put(TYPE, biens.getTypeBiens());
		value.put(PIECES, biens.getPiecesBiens());
		value.put(SURFACE, biens.getSurfaceBiens());
		value.put(ETAGE, biens.getEtageBiens());
		value.put(DIGICODE, biens.getDigicodeBiens());
		value.put(IMMEUBLE, biens.getImmeubleBiens());
		value.put(LOT, biens.getLotBiens());
        value.put(MANDAT, biens.getMandatBiens());
        value.put(INTERIEUR, biens.getInterieur());
		return (int)operaDataBase.insert(BIENS_TABLE, null, value);
	}
	
	public int addValueWID(CEL_Biens biens) {
		open();
		ContentValues value = new ContentValues();
		value.put(KEY, biens.getIdBiens());
		value.put(ADRESSE, biens.getAdresseBiens());
		value.put(SUITE, biens.getSuiteBiens());
		value.put(CODE_POSTAL, biens.getCodePostalBiens());
		value.put(VILLE, biens.getVilleBiens());
		value.put(TYPE, biens.getTypeBiens());
		value.put(PIECES, biens.getPiecesBiens());
		value.put(SURFACE, biens.getSurfaceBiens());
		value.put(ETAGE, biens.getEtageBiens());
		value.put(DIGICODE, biens.getDigicodeBiens());
		value.put(IMMEUBLE, biens.getImmeubleBiens());
		value.put(LOT, biens.getLotBiens());
		value.put(MANDAT, biens.getMandatBiens());
        value.put(INTERIEUR, biens.getInterieur());
		return (int)operaDataBase.insert(BIENS_TABLE, null, value);
	}

	/**
	 * Delete a CEL_Biens value from an Id
	 * @param idBiens
	 */
	public void deleteValue(int idBiens) {
		open();
		operaDataBase.delete(BIENS_TABLE, KEY + " = ?", new String[]{String.valueOf(idBiens)});
		close();
	}


	/**
	 * Update/Modify a CEL_Biens
	 * @param biens
	 */

	public void updateValue(CEL_Biens biens) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(ADRESSE, biens.getAdresseBiens());
		value.put(SUITE, biens.getSuiteBiens());
		value.put(CODE_POSTAL, biens.getCodePostalBiens());
		value.put(VILLE, biens.getVilleBiens());
		value.put(TYPE, biens.getTypeBiens());
		value.put(PIECES, biens.getPiecesBiens());
		value.put(SURFACE, biens.getSurfaceBiens());
		value.put(ETAGE, biens.getEtageBiens());
		value.put(DIGICODE, biens.getDigicodeBiens());
		value.put(IMMEUBLE, biens.getImmeubleBiens());
		value.put(LOT, biens.getLotBiens());
		value.put(MANDAT, biens.getMandatBiens());
        value.put(INTERIEUR, biens.getInterieur());
		operaDataBase.update(BIENS_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(biens.getIdBiens())});
		close();
	}
	
	public boolean checkId(int idBiens) {
		open();
		Cursor cursor = operaDataBase.rawQuery("SELECT * FROM " + BIENS_TABLE + " WHERE " + KEY + "= ?", new String[]
				{String.valueOf(idBiens)});
		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Select a specific CEL_Biens
	 * @param idBiens
	 */
	public CEL_Biens select(int idBiens) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + BIENS_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idBiens)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Biens object
					CEL_Biens biens = new CEL_Biens();
					biens.setIdBiens(Integer.parseInt(cursor.getString(0)));
					biens.setAdresseBiens(cursor.getString(1));
					biens.setSuiteBiens(cursor.getString(2));
					biens.setCodePostalBiens(cursor.getString(3));
					biens.setVilleBiens(cursor.getString(4));
					biens.setTypeBiens(Integer.parseInt(cursor.getString(5)));
					biens.setPiecesBiens(Integer.parseInt(cursor.getString(6)));
					biens.setSurfaceBiens(Float.parseFloat(cursor.getString(7)));
					biens.setEtageBiens(cursor.getString(8));
					biens.setDigicodeBiens(cursor.getString(9));
					biens.setImmeubleBiens(cursor.getString(10));
					biens.setLotBiens(cursor.getString(11));
					biens.setMandatBiens(cursor.getString(12));
                    biens.setInterieur(cursor.getString(13));
					return biens;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		//Return biens
		CEL_Biens biens = new CEL_Biens();
		return biens;
	}


}
