package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.OPERA_Photos;

import java.util.ArrayList;
import java.util.List;

public class Photo_Element_Lier_DAO extends CEL_Database_DAO {

	public Photo_Element_Lier_DAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	public static final String PHOTO_ELEMENT_LIER_TABLE = "Photo_Element_Lier";
	public static final String OPERA_PHOTO_KEY = "idOpera_Photos";
	public static final String ELEMENT_KEY = "idElement";

	public static final String TABLE_CREATE = "CREATE TABLE " + PHOTO_ELEMENT_LIER_TABLE + " (" 
			+ OPERA_PHOTO_KEY + " INTEGER, "
			+ ELEMENT_KEY + " INTEGER, "
			+ "PRIMARY KEY ("+OPERA_PHOTO_KEY+", "+ELEMENT_KEY+"), "
			+ " FOREIGN KEY ("+OPERA_PHOTO_KEY+") REFERENCES "+OPERA_Photos_DAO.PHOTOS_TABLE+" ("+OPERA_Photos_DAO.KEY+"),"
			+ " FOREIGN KEY ("+ELEMENT_KEY+") REFERENCES "+CEL_Elements_DAO.ELEMENTS_TABLE+" ("+CEL_Elements_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + PHOTO_ELEMENT_LIER_TABLE + ";";


	/**
	 * Insert new value on Photo_Element_Lier
	 * 	
	 * @param idOpera_Photos
	 * @param idElement
	 */
	public void addValue(int idOpera_Photos, int idElement) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(OPERA_PHOTO_KEY, idOpera_Photos);
		value.put(ELEMENT_KEY, idElement);
		operaDataBase.insert(PHOTO_ELEMENT_LIER_TABLE, null, value);
		close();
	}

	/**
	 * Return all Photos from an specific Element
	 * 
	 * @param idElement
	 * @return
	 */
	public List<OPERA_Photos> selectPhotosFromElement(int idElement) {
		
		List<OPERA_Photos> listPhotos = new ArrayList<OPERA_Photos>();

		Cursor cursor = operaDataBase.rawQuery(
				"SELECT O."+ OPERA_Photos_DAO.KEY  
				+ ", O."+ OPERA_Photos_DAO.NOM
				+ ", O."+ OPERA_Photos_DAO.NUMERO_PHOTOS
				+ ", O."+ OPERA_Photos_DAO.EMPLACEMENT_PHOTOS 
				+ ", O."+ OPERA_Photos_DAO.DEGRADATION_PHOTOS
				+ ", O."+ OPERA_Photos_DAO.ORDRE_PHOTOS
				+ ", O."+ OPERA_Photos_DAO.DATE_RATTACHEMENT_PHOTOS
				+ ", O."+ OPERA_Photos_DAO.PHOTO
				+ ", O."+ OPERA_Photos_DAO.MISSION 
				+ ", O."+ OPERA_Photos_DAO.NAME
				+ " FROM "+ OPERA_Photos_DAO.PHOTOS_TABLE + " AS P JOIN " 
				+ PHOTO_ELEMENT_LIER_TABLE + " AS A ON" 
				+" O." + OPERA_Photos_DAO.KEY + " = A."+ OPERA_PHOTO_KEY
				+" WHERE A." + ELEMENT_KEY
				+ " = ? ",  
				new String[]{String.valueOf(idElement)});

		//If we got result
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					
					OPERA_Photos photo = new OPERA_Photos();
					photo.setIdPhotos(Integer.parseInt(cursor.getString(0)));
					photo.setIdPhotos(Integer.parseInt(cursor.getString(1)));
					photo.setNumeroPhotos(Integer.parseInt(cursor.getString(2)));
					photo.setEmplacementPhotos(cursor.getString(3));
					photo.setDegradationPhotos(Boolean.valueOf(cursor.getString(4)));
					photo.setOrdrePhotos(Integer.parseInt(cursor.getString(5)));
					photo.setDateRattachementPhotos(cursor.getString(6));
					photo.setPhoto(cursor.getBlob(7));
					photo.setIdMission(Integer.parseInt(cursor.getString(8)));
					photo.setPhotoName(cursor.getString(9));
					
					listPhotos.add(photo);
					cursor.moveToNext();
				}
			}
		}
		return listPhotos;

	}

}
