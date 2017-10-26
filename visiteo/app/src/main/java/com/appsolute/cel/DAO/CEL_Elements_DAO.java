package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_Elements;

import java.util.ArrayList;

/**
 * 
 *This class refer to CEL_Elements, it's using to access to CEL_Elements on database 
 *
 */
public class CEL_Elements_DAO extends CEL_Database_DAO{


	public CEL_Elements_DAO(Context pContext) {
		super(pContext);
	}

	public static final String ELEMENTS_TABLE = "CEL_Elements";
	public static final String KEY = "idElement";
	public static final String ELEMENT = "Element";
	public static final String TYPE = "Type";
	public static final String QUANTITE = "Qte";
	public static final String ETAT = "Etat";
	public static final String PHOTO = "idPhoto";
	public static final String NOMBRE_TROUS_CHEVILLE = "nombreTrouscheville";
	public static final String IS_NETTOYER = "isNettoyer";
	public static final String IS_COUNTABLE = "isCountable";
	public static final String IS_ETAT_PIECE = "isEtatPiece";
	public static final String PIECES = "idPiece";
	public static final String ID_ROOMITEM = "idRoomItem";
	public static final String ID_ITEMTYPE = "idItemType";
	public static final String MANDATORY = "mandatory";
	public static final String ORDRE = "ordre";

	public static final String TABLE_CREATE = "CREATE TABLE " + ELEMENTS_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, "
			+ ELEMENT + " TEXT, " 
			+ TYPE + " TEXT, "
			+ QUANTITE + " INTEGER, "
			+ ETAT + " TEXT, " 
			+ NOMBRE_TROUS_CHEVILLE + " INTEGER, "
			+ PHOTO + " INTEGER, "
			+ IS_NETTOYER + " INTEGER, "
			+ IS_COUNTABLE + " INTEGER, "
			+ IS_ETAT_PIECE + " INTEGER, "
			+ PIECES + " INTEGER, "
			+ ID_ROOMITEM + " INTEGER, "
			+ ID_ITEMTYPE + " INTEGER, "
			+ MANDATORY + " INTEGER, "
			+ ORDRE + " INTEGER, "
			+ " FOREIGN KEY ("+PHOTO+") REFERENCES "+OPERA_Photos_DAO.PHOTOS_TABLE+" ("+OPERA_Photos_DAO.KEY+"), "
			+ " FOREIGN KEY ("+PIECES+") REFERENCES "+CEL_Piece_DAO.PIECES_TABLE+" ("+CEL_Piece_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + ELEMENTS_TABLE + ";";


	/**
	 * Insert new value on CEL_Elements
	 * 
	 * @param elements
	 */
	public int addValue(CEL_Elements elements) {
		int id=0;
		try {
			open();
			ContentValues value = new ContentValues();
			value.put(ELEMENT, elements.getElementElements());
			value.put(TYPE, elements.getTypeElements());
			value.put(QUANTITE, elements.getQuantiteElements());
			value.put(ETAT, elements.getEtatElements());
			value.put(PHOTO, elements.getIdOperaPhoto());
			value.put(NOMBRE_TROUS_CHEVILLE, elements.getNombreTrousChevilleElements());
			value.put(IS_NETTOYER, elements.getIsNettoyer());
			value.put(IS_COUNTABLE, elements.getIsCountable());
			value.put(IS_ETAT_PIECE, elements.getItemGroupDescription());
			value.put(PIECES, elements.getIdPiece());
			value.put(ID_ROOMITEM, elements.getIdRoomItem());
			value.put(ID_ITEMTYPE, elements.getIdItemType());
			value.put(MANDATORY, elements.getMandatory());
			value.put(ORDRE, elements.getOrdre());
			id = (int) operaDataBase.insert(ELEMENTS_TABLE, null, value);
			close();
		} catch (Exception e) { e.printStackTrace(); }
		return id;
	}

	/**
	 * Delete a CEL_Elements value from an Id
	 * @param idElements
	 */
	public void deleteValue(int idElements) {
		open();
		operaDataBase.delete(ELEMENTS_TABLE, KEY + " = ?", new String[]{String.valueOf(idElements)});
		close();
	}
	
	public void deleteAllElementsForPiece(int idPiece) {
		open();
		operaDataBase.delete(ELEMENTS_TABLE, PIECES + " = ?", new String[]{String.valueOf(idPiece)});
		close();
	}


	/**
	 * Update/Modify a CEL_Elements
	 * @param elements
	 */

	public void updateValue(CEL_Elements elements) {
		try {
			open();
			ContentValues value = new ContentValues();
			value.put(ELEMENT, elements.getElementElements());
			value.put(TYPE, elements.getTypeElements());
			value.put(QUANTITE, elements.getQuantiteElements());
			value.put(ETAT, elements.getEtatElements());
			value.put(PHOTO, elements.getIdOperaPhoto());
			value.put(NOMBRE_TROUS_CHEVILLE, elements.getNombreTrousChevilleElements());
			value.put(IS_NETTOYER, elements.getIsNettoyer());
			value.put(IS_COUNTABLE, elements.getIsCountable());
			value.put(IS_ETAT_PIECE, elements.getItemGroupDescription());
			value.put(PIECES, elements.getIdPiece());
			value.put(ID_ROOMITEM, elements.getIdRoomItem());
			value.put(ID_ITEMTYPE, elements.getIdItemType());
			value.put(MANDATORY, elements.getMandatory());
			value.put(ORDRE, elements.getOrdre());
			operaDataBase.update(ELEMENTS_TABLE, value, KEY + " = ?", 
					new String[] {String.valueOf(elements.getIdElements())});
			close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Select a specific CEL_Elements
	 * @param idElement
	 */
	public CEL_Elements select(int idElement) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ELEMENTS_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idElement)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Elements object
					CEL_Elements elements = new CEL_Elements();
					elements.setIdElements(Integer.parseInt(cursor.getString(0)));
					elements.setElementElements(cursor.getString(1));
					elements.setTypeElements(cursor.getString(2));
					elements.setQuantiteElements(Integer.parseInt(cursor.getString(3)));
					elements.setEtatElements(cursor.getString(4));
					elements.setNombreTrousChevilleElements(Integer.parseInt(cursor.getString(5)));
					elements.setIdOperaPhoto(cursor.getInt(6));
					elements.setIsNettoyer(Integer.parseInt(cursor.getString(7)));
					elements.setIsCountable(cursor.getInt(8));
					elements.setItemGroupDescription(Integer.parseInt(cursor.getString(9)));
					elements.setIdPiece(Integer.parseInt(cursor.getString(10)));
					elements.setIdRoomItem(Integer.parseInt(cursor.getString(11)));
					elements.setIdItemType(Integer.parseInt(cursor.getString(12)));
					elements.setMandatory(cursor.getInt(13));
					elements.setOrdre(cursor.getInt(14));
					return elements;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		CEL_Elements elements = new CEL_Elements();
		//Return elements
		return elements;
	}

	public boolean checkExistingDuplicationElement(String element, int idElement) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + ELEMENTS_TABLE +
						" where " + ELEMENT + "= ? " +
						"AND " + PIECES + "= ?",
				new String[]{element, String.valueOf(idElement)});

		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 1)
					return true;
				else
					return false;
		}
		close();
		return false;
	}


	/**
	 * Select all CEL_Elements for a specific CEL_Piece 
	 * @param idPiece
	 */
	public ArrayList<CEL_Elements> selectAllElementsFromPiece(int idPiece, int isEtatPiece) {
		ArrayList<CEL_Elements> elementsList = new ArrayList<>();
		Cursor cursor;
		if(isEtatPiece != 4) {
			cursor = operaDataBase.rawQuery(
					"SELECT * FROM " + ELEMENTS_TABLE
					+ " WHERE "+ IS_ETAT_PIECE + " = " + isEtatPiece
					+ " AND " + PIECES + "= ?"
					+ " ORDER BY " + ORDRE + " ASC"
					, new String[] {String.valueOf(idPiece)});
		}
		else {
			cursor = operaDataBase.rawQuery(
			        "SELECT * FROM " + ELEMENTS_TABLE
                    + " WHERE " + PIECES + "= ?"
                    + " ORDER BY " + ORDRE + " ASC", new String[] {String.valueOf(idPiece)});
		}

		//If we got results get the first one
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					//Build CEL_Elements object
					CEL_Elements elements = new CEL_Elements();
					elements.setIdElements(Integer.parseInt(cursor.getString(0)));
					elements.setElementElements(cursor.getString(1));
					elements.setTypeElements(cursor.getString(2));
					elements.setQuantiteElements(Integer.parseInt(cursor.getString(3)));
					elements.setEtatElements(cursor.getString(4));
					elements.setNombreTrousChevilleElements(Integer.parseInt(cursor.getString(5)));
					elements.setIdOperaPhoto(cursor.getInt(6));
					elements.setIsNettoyer(Integer.parseInt(cursor.getString(7)));
					elements.setIsCountable(Integer.parseInt(cursor.getString(8)));
					elements.setItemGroupDescription(Integer.parseInt(cursor.getString(9)));
					elements.setIdPiece(Integer.parseInt(cursor.getString(10)));
					elements.setIdRoomItem(Integer.parseInt(cursor.getString(11)));
					elements.setIdItemType(Integer.parseInt(cursor.getString(12)));
					elements.setMandatory(cursor.getInt(13));
					elements.setOrdre(cursor.getInt(14));
					elementsList.add(elements);
					cursor.moveToNext();
				}
				cursor.close();
				return elementsList;
			}
		}
		
		return elementsList;
	}
}