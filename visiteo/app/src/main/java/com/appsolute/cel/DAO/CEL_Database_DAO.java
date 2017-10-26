package com.appsolute.cel.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Lucien Guimaraes
 *
 * CEL_Database_DAO is using to open/close database
 *
 */
public abstract class CEL_Database_DAO {

	private final static int VERSION = 6;

	public final static String NOM = "opera_database.db";
	protected SQLiteDatabase operaDataBase = null; 
	private MySQLiteHelper sqlHelper = null;


	public CEL_Database_DAO(Context pContext) {
		this.sqlHelper = new MySQLiteHelper(pContext, NOM, null, VERSION) {
		};
		open();
	}

	public SQLiteDatabase open() {
		if(sqlHelper!=null)
			sqlHelper.close();
		if(operaDataBase!=null)
			operaDataBase.close();
		operaDataBase = sqlHelper.getWritableDatabase();
		return operaDataBase; 
	}

	public void close() {
		if(sqlHelper!=null) {
			sqlHelper.close();
			operaDataBase.close();
		}
	}

	public SQLiteDatabase getDb() {
		return operaDataBase; 
	}
	
	public void removeAllDatas() {
		
		operaDataBase.execSQL(CEL_Personnes_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Chauffage_DAO.TABLE_DROP);
		operaDataBase.execSQL(OPERA_Photos_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Compteurs_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Piece_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Biens_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Mission_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Elements_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Mission_Personnes_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Actions_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Clefs_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_ECS_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_Elements_Obs_DAO.TABLE_DROP);
		operaDataBase.execSQL(CEL_InfoCompteurs_DAO.TABLE_DROP);
		operaDataBase.execSQL(Signature_DAO.TABLE_DROP);
		
		
		operaDataBase.execSQL(CEL_Personnes_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Chauffage_DAO.TABLE_CREATE);
		operaDataBase.execSQL(OPERA_Photos_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Compteurs_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Piece_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Biens_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Mission_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Elements_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Mission_Personnes_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Actions_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Clefs_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_ECS_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_Elements_Obs_DAO.TABLE_CREATE);
		operaDataBase.execSQL(CEL_InfoCompteurs_DAO.TABLE_CREATE);
		operaDataBase.execSQL(Signature_DAO.TABLE_CREATE);
		
	}
}

