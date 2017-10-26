package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.OPERA_Photos;

import java.util.ArrayList;

public class OPERA_Photos_DAO extends CEL_Database_DAO {

	public OPERA_Photos_DAO(Context pContext) {
		super(pContext);
	}


    static final String PHOTOS_TABLE = "OPERA_Photos";
    public static final String KEY = "idPhotos";
    static final String NUMERO_PHOTOS = "numeroPhotos";
    static final String EMPLACEMENT_PHOTOS = "emplacementPhotos";
    static final String DEGRADATION_PHOTOS = "degradationPhotos";
    static final String ORDRE_PHOTOS = "ordrePhotos";
    static final String DATE_RATTACHEMENT_PHOTOS = "dateRattachementPhotos";
    static final String PHOTO = "photo";
    static final String MISSION = "idMission";
    static final String NAME = "photoName";

	public static final String TABLE_CREATE = "CREATE TABLE " + PHOTOS_TABLE + " (" 
			+ KEY + " INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, " 
			+ NUMERO_PHOTOS + " INTEGER, " 
			+ EMPLACEMENT_PHOTOS + " TEXT, " 
			+ DEGRADATION_PHOTOS + " BOOLEAN, " 
			+ ORDRE_PHOTOS + " INTEGER, "
			+ DATE_RATTACHEMENT_PHOTOS + " TEXT, "
			+ PHOTO + " BLOB, "
			+ MISSION + " INTEGER, "
			+ NAME + " TEXT, "
			+ " FOREIGN KEY ("+MISSION+") REFERENCES " + CEL_Mission_DAO.MISSION_TABLE + " ("+CEL_Mission_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + PHOTOS_TABLE + ";";
	//public static final String ALTER_TABLE = "ALTER TABLE " + PHOTOS_TABLE + " RENAME TO oldPhotosTable";


	/**
	 * Insert new value on OPERA_Photos
	 * 
	 * @param photos
	 */
	public int addValue(OPERA_Photos photos) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(NUMERO_PHOTOS, photos.getNumeroPhotos()); 
		value.put(EMPLACEMENT_PHOTOS, photos.getEmplacementPhotos()); 
		value.put(DEGRADATION_PHOTOS, photos.getDegradationPhotos()); 
		value.put(ORDRE_PHOTOS, photos.getOrdrePhotos()); 
		value.put(DATE_RATTACHEMENT_PHOTOS, photos.getDateRattachementPhotos());
		value.put(PHOTO, photos.getPhoto());
		value.put(MISSION, photos.getIdMission());
		value.put(NAME, photos.getPhotoName());
		return (int)operaDataBase.insert(PHOTOS_TABLE, null, value);
	}


	/**
	 * Delete an OPERA_Photos value from an Id
	 * @param idPhoto
	 */
	public void deleteValue(int idPhoto) {
		open();
		operaDataBase.delete(PHOTOS_TABLE, KEY + " = ?", new String[]{String.valueOf(idPhoto)});
		close();
	}



	/**
	 * Update/Modify a OPERA_Photos
	 * @param photos
	 */
	public void updateValue(OPERA_Photos photos) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(NUMERO_PHOTOS, photos.getNumeroPhotos()); 
		value.put(EMPLACEMENT_PHOTOS, photos.getEmplacementPhotos()); 
		value.put(DEGRADATION_PHOTOS, photos.getDegradationPhotos()); 
		value.put(ORDRE_PHOTOS, photos.getOrdrePhotos()); 
		value.put(DATE_RATTACHEMENT_PHOTOS, photos.getDateRattachementPhotos()); 
		value.put(PHOTO, photos.getPhoto());
		value.put(MISSION, photos.getIdMission());
		value.put(NAME, photos.getPhotoName());
		operaDataBase.update(PHOTOS_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(photos.getIdPhotos())});
		close();
	}


	/**
	 * Select a specific OPERA_Photos
	 * @param idPhoto
	 */
	public OPERA_Photos select(int idPhoto) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + PHOTOS_TABLE + " where " + KEY + "= ?", new String[]
				{String.valueOf(idPhoto)});

        OPERA_Photos photos = new OPERA_Photos();
		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build photo object
					photos.setIdPhotos(Integer.parseInt(cursor.getString(0)));
					photos.setNumeroPhotos(Integer.parseInt(cursor.getString(1)));
					photos.setEmplacementPhotos(cursor.getString(2));
					photos.setDegradationPhotos(Boolean.valueOf(cursor.getString(3)));
					photos.setOrdrePhotos(Integer.parseInt(cursor.getString(4)));
					photos.setDateRattachementPhotos(cursor.getString(5));
					photos.setPhoto(cursor.getBlob(6));
					photos.setIdMission(Integer.parseInt(cursor.getString(7)));
					photos.setPhotoName(cursor.getString(8));
					return photos;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		return photos;
	}

	
	public ArrayList<OPERA_Photos> selectAllPhotosFromMission(int idMission) {
		open();
        ArrayList<OPERA_Photos> photosList = new ArrayList<>();
		Cursor cursor = operaDataBase.rawQuery("select * from " + PHOTOS_TABLE + " where " + MISSION + " = ?", new String[]
				{String.valueOf(idMission)});
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					//Build OPERA_Photos object
					OPERA_Photos photo = new OPERA_Photos();
					photo.setIdPhotos(Integer.parseInt(cursor.getString(0)));
					photo.setNumeroPhotos(Integer.parseInt(cursor.getString(1)));
					photo.setEmplacementPhotos(cursor.getString(2));
					photo.setDegradationPhotos(Boolean.valueOf(cursor.getString(3)));
					photo.setOrdrePhotos(Integer.parseInt(cursor.getString(4)));
					photo.setDateRattachementPhotos(cursor.getString(5));
					photo.setPhoto(cursor.getBlob(6));
					photo.setIdMission(Integer.parseInt(cursor.getString(7)));
					photo.setPhotoName(cursor.getString(8));
					
					photosList.add(photo);
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		
		return photosList;
	}

}
