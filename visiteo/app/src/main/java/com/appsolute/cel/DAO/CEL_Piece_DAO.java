package com.appsolute.cel.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.appsolute.cel.models.CEL_Biens;
import com.appsolute.cel.models.CEL_Piece;
import com.appsolute.cel.utils.CommonMethods;

import java.util.ArrayList;

/**
 * 
 * @author Lucien Guimaraes
 *
 * This class refer to CEL_Piece, it's using to access to CEL_Piece on database 
 *
 */
public class CEL_Piece_DAO extends CEL_Database_DAO {

    private int idMission;
    private CEL_Biens cel_bien;
    private Context pContext;
	
	public CEL_Piece_DAO(Context pContext, int idMission, CEL_Biens cel_bien) {
		super(pContext);
        this.pContext = pContext;
        this.idMission = idMission;
        this.cel_bien = cel_bien;
	}

	public static final String PIECES_TABLE = "CEL_pieces";
	public static final String KEY = "idPiece";
	public static final String PIECE  = "piece";
	public static final String LONGUEUR = "longeur";
	public static final String LARGEUR = "largeur";
	public static final String HAUTEUR = "heuteur";
	public static final String INCLUS_DANS_PIECE = "inclusDansPiece";
	public static final String PHOTO = "idPhotoPiece";
	public static final String NOTES = "notes";
	public static final String MISSION = "idMission";
	public static final String PIECE_MAIN = "pieceMain";
	public static final String IS_FINISH = "isFinish";
	public static final String HAS_USER_STARTED_DELETION = "hasUserStartedDeletion";
	public static final String HAS_USER_STARTED_REPORT = "hasUserStartedReport";


	public static final String TABLE_CREATE = "CREATE TABLE " + PIECES_TABLE + " (" + KEY + " INTEGER PRIMARY KEY, " 
			+ PIECE + " TEXT, " 
			+ LONGUEUR + " REAL, " 
			+ LARGEUR + " REAL, " 
			+ HAUTEUR + " REAL, "
			+ INCLUS_DANS_PIECE + " TEXT, "
			+ NOTES + " TEXT, " 
			+ PHOTO + " INTEGER, "
			+ MISSION + " INTEGER, " 
			+ PIECE_MAIN + " TEXT, " 
			+ IS_FINISH + " INTEGER,"
			+ HAS_USER_STARTED_DELETION + " INTEGER,"
			+ HAS_USER_STARTED_REPORT + " INTEGER,"
			+ " FOREIGN KEY ("+PHOTO+") REFERENCES "+OPERA_Photos_DAO.PHOTOS_TABLE+" ("+OPERA_Photos_DAO.KEY+"), "
			+ " FOREIGN KEY ("+MISSION+") REFERENCES "+CEL_Mission_DAO.MISSION_TABLE+" ("+CEL_Mission_DAO.KEY+"));";

	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + PIECES_TABLE + ";";


	/**
	 * Insert new value on CEL_pieces
	 * 
	 * @param pieces
	 */
	public int addValue(CEL_Piece pieces) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(PIECE, pieces.getPiecePiece()); 
		value.put(LONGUEUR, pieces.getLongueurPiece()); 
		value.put(LARGEUR, pieces.getLargeurPiece()); 
		value.put(HAUTEUR, pieces.getHauteurPiece()); 
		value.put(INCLUS_DANS_PIECE, pieces.getInclusDansPiece()); 
		value.put(PHOTO, pieces.getIdOperaPhoto());
		value.put(NOTES, pieces.getNotesPiece());
		value.put(MISSION, pieces.getIdMission());
		value.put(PIECE_MAIN, pieces.getNomTypePiece());
		value.put(IS_FINISH, pieces.getIsFinish());
		if(pieces.isHasUserStartedDeletion())
			value.put(HAS_USER_STARTED_DELETION, 1);
		else
			value.put(HAS_USER_STARTED_DELETION, 0);
		if(pieces.isHasUserStartedReport())
			value.put(HAS_USER_STARTED_REPORT, 1);
		else
			value.put(HAS_USER_STARTED_REPORT, 0);

        int idPiece = (int)operaDataBase.insert(PIECES_TABLE, null, value);
        //updateCountPiece();

		return idPiece;
	}

	/**
	 * Delete a CEL_pieces value from an Id
	 * 
	 * @param idPiece
	 */
	public void deleteValue(int idPiece) {
		open();
		operaDataBase.delete(PIECES_TABLE, KEY + " = ?", new String[]{String.valueOf(idPiece)});
        //updateCountPiece();
		close();
	}
	
	public void deleteAllFromBean(int idBean) {
		open();
		operaDataBase.delete(PIECES_TABLE, MISSION + " = ?", new String[]{String.valueOf(idBean)});
        //updateCountPiece();
		close();
	}

	/**
	 * Update value on existing CEL_pieces
	 * 
	 * @param pieces
	 */
	public void updateValue(CEL_Piece pieces) {
		open();
		ContentValues value = new ContentValues(); 
		value.put(PIECE, pieces.getPiecePiece()); 
		value.put(LONGUEUR, pieces.getLongueurPiece()); 
		value.put(LARGEUR, pieces.getLargeurPiece()); 
		value.put(HAUTEUR, pieces.getHauteurPiece()); 
		value.put(INCLUS_DANS_PIECE, pieces.getInclusDansPiece()); 
		value.put(PHOTO, pieces.getIdOperaPhoto());
		value.put(NOTES, pieces.getNotesPiece());
		value.put(MISSION, pieces.getIdMission());
		value.put(PIECE_MAIN, pieces.getNomTypePiece());
		value.put(IS_FINISH, pieces.getIsFinish());
		if(pieces.isHasUserStartedDeletion())
			value.put(HAS_USER_STARTED_DELETION, 1);
		else
			value.put(HAS_USER_STARTED_DELETION, 0);
		if(pieces.isHasUserStartedReport())
			value.put(HAS_USER_STARTED_REPORT, 1);
		else
			value.put(HAS_USER_STARTED_REPORT, 0);
		operaDataBase.update(PIECES_TABLE, value, KEY + " = ?", 
				new String[] {String.valueOf(pieces.getIdPiece())});
        //updateCountPiece();
		close();
	}


	/**
	 * Select a specific CEL_pieces
	 * @param idPiece
	 */
	public CEL_Piece select(int idPiece, int idMission) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + PIECES_TABLE + " where " + KEY + "= ? AND "+MISSION+" =? ", new String[]
				{String.valueOf(idPiece), String.valueOf(idMission)});

		//If we got results get the first one
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.getCount() > 0) {
				if (!cursor.isNull(cursor.getColumnIndex(KEY))) {
					//Build CEL_Piece object
					CEL_Piece piece = new CEL_Piece();
					piece.setIdPiece(Integer.parseInt(cursor.getString(0)));
					piece.setPiecePiece(cursor.getString(1));
					piece.setLongueurPiece(Float.parseFloat(cursor.getString(2)));
					piece.setLargeurPiece(Float.parseFloat(cursor.getString(3)));
					piece.setHauteurPiece(Float.parseFloat(cursor.getString(4)));
					piece.setInclusDansPiece((cursor.getString(5)));
					piece.setNotesPiece(cursor.getString(6));
					piece.setIdOperaPhoto(cursor.getInt(7));
					piece.setIdMission(Integer.parseInt(cursor.getString(8)));
					piece.setNomTypePiece(cursor.getString(9));
					piece.setIsFinish(Integer.parseInt(cursor.getString(10)));
					piece.setHasUserStartedDeletion(CommonMethods.getBoolean(cursor.getInt(11)));
					piece.setHasUserStartedReport(CommonMethods.getBoolean(cursor.getInt(12)));
					return piece;
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		CEL_Piece piece = new CEL_Piece();
		return piece;
	}
	
	
	/**
	 * Select all CEL_pieces with a specific idMission
	 * 
	 * @param idMission
	 */
	public ArrayList<CEL_Piece> selectAllPieceWithIdMission(int idMission) {
		open();
		Cursor cursor = operaDataBase.rawQuery("select * from " + PIECES_TABLE + " where " + MISSION + "= ?", new String[]
				{String.valueOf(idMission)});

		ArrayList<CEL_Piece> piecesList = new ArrayList<>();
		//If we got results get the first one
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					//Build CEL_Piece object
					CEL_Piece piece = new CEL_Piece();
					piece.setIdPiece(Integer.parseInt(cursor.getString(0)));
					piece.setPiecePiece(cursor.getString(1));
					piece.setLongueurPiece(Float.parseFloat(cursor.getString(2)));
					piece.setLargeurPiece(Float.parseFloat(cursor.getString(3)));
					piece.setHauteurPiece(Float.parseFloat(cursor.getString(4)));
					piece.setInclusDansPiece((cursor.getString(5)));
					piece.setNotesPiece(cursor.getString(6));
					piece.setIdOperaPhoto(cursor.getInt(7));
					piece.setIdMission(Integer.parseInt(cursor.getString(8)));
					piece.setNomTypePiece(cursor.getString(9));
					piece.setIsFinish(Integer.parseInt(cursor.getString(10)));
					piece.setHasUserStartedDeletion(CommonMethods.getBoolean(cursor.getInt(11)));
					piece.setHasUserStartedReport(CommonMethods.getBoolean(cursor.getInt(12)));
					piecesList.add(piece);
					cursor.moveToNext();
				}
			}
		}
		if(cursor!=null)
			cursor.close();
		close();
		//Return elements
		return piecesList;
	}


    private void updateCountPiece() {

		ArrayList<CEL_Piece> piecesList = selectAllPieceWithIdMission(idMission);
		int countPiece = 0;
		if(!piecesList.isEmpty()) {
			for (CEL_Piece piece: piecesList) {
                if(piece.getInclusDansPiece() != null) {
                    if (piece.getInclusDansPiece().isEmpty())
                        countPiece++;
                }
                else
                    countPiece++;
			}
		}

        cel_bien.setPiecesBiens(countPiece);
        CEL_Biens_DAO cel_biens_dao = new CEL_Biens_DAO(pContext);
        cel_biens_dao.updateValue(cel_bien);
        open();
	}

}
