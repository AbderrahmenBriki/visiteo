package com.appsolute.cel.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class MySQLiteHelper extends SQLiteOpenHelper {
	/**
	 * Constructor of MySQLiteHelper
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	
	/**
	 * Create tables with String from DAO classes
	 */
	@Override
	public void onCreate(SQLiteDatabase operaDatabase) {

		try {
			operaDatabase.execSQL(CEL_Personnes_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Chauffage_DAO.TABLE_CREATE);
			operaDatabase.execSQL(OPERA_Photos_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Etat_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Compteurs_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Piece_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Biens_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Mission_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Elements_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Mission_Personnes_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Actions_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Clefs_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_ECS_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_Elements_Obs_DAO.TABLE_CREATE);
			operaDatabase.execSQL(Room_DAO.TABLE_CREATE);
			operaDatabase.execSQL(RoomItem_DAO.TABLE_CREATE);
			operaDatabase.execSQL(ItemType_DAO.TABLE_CREATE);
			operaDatabase.execSQL(Room_Damage_DAO.TABLE_CREATE);
			operaDatabase.execSQL(CEL_InfoCompteurs_DAO.TABLE_CREATE);
			operaDatabase.execSQL(Signature_DAO.TABLE_CREATE);
			operaDatabase.execSQL(ItemsPack_DAO.TABLE_CREATE);
			operaDatabase.execSQL(PackElement_DAO.TABLE_CREATE);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Drop and create new tables
	 */
	@Override
    public void onUpgrade(SQLiteDatabase operaDatabase, int oldVersion, int newVersion) {
        // Drop older tables if existed
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Chauffage_DAO.CHAUFFAGE_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Biens_DAO.BIENS_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Personnes_DAO.PERSONNES_TABLES+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Elements_DAO.ELEMENTS_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Compteurs_DAO.COMPTEURS_TABLES+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Actions_DAO.ACTIONS_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Piece_DAO.PIECES_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Mission_DAO.MISSION_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Elements_Obs_DAO.ELEMENTS_OBS_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Mission_Personnes_DAO.MISSION_PERSONNES_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_Clefs_DAO.CLEFS_TABLES+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_ECS_DAO.ECS_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+CEL_InfoCompteurs_DAO.INFO_COMPTEURS_TABLES+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+Signature_DAO.SIGNATURE_TABLE+";");
		operaDatabase.execSQL("DROP TABLE IF EXISTS "+OPERA_Photos_DAO.PHOTOS_TABLE+";");
 
        // create all tables
        this.onCreate(operaDatabase);
    }
}
