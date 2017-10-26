package com.appsolute.cel.models;

public class CEL_Piece {
	
	private int idPiece;
	private int idMission;
	private String nomPiece;
	private float longueurPiece;
	private float largeurPiece;
	private float hauteurPiece;
	private String inclusDansPiece;
	private int idOperaPhoto;
	private String notesPiece;
	private String nom_type_Piece;
	private int isFinish;
	private boolean hasUserStartedDeletion;
	private boolean hasUserStartedReport;
	
	//Constructor without field
	public CEL_Piece() {
		super();
	}
	
	/**
	 * Create a complete CEL_Piece
	 * 
	 * @param idPiece
	 * @param idMission
	 * @param piecePiece
	 * @param longueurPiece
	 * @param largeurPiece
	 * @param hauteurPiece
	 * @param inclusDansPiece
	 * @param idOperaPhoto
	 * @param notesPiece
	 * @param nom_type_Piece
	 */
	public CEL_Piece(int idPiece, int idMission, String piecePiece,
					 float longueurPiece, float largeurPiece, float hauteurPiece,
					 String inclusDansPiece, int idOperaPhoto, String notesPiece,
					 String nom_type_Piece, int isFinish) {
		super();
		this.idPiece = idPiece;
		this.idMission = idMission;
		this.nomPiece = piecePiece;
		this.longueurPiece = longueurPiece;
		this.largeurPiece = largeurPiece;
		this.hauteurPiece = hauteurPiece;
		this.inclusDansPiece = inclusDansPiece;
		this.idOperaPhoto = idOperaPhoto;
		this.notesPiece = notesPiece;
		this.nom_type_Piece = nom_type_Piece;
		this.isFinish = isFinish;
	}
	
	/**
	 * Create a CEL_Piece 
	 * 
	 * @param idMission
	 * @param nomPiece
	 */
	public CEL_Piece(int idMission, String nomPiece, 
			String nom_type_Piece, int isFinish) {
		super();
		this.idMission = idMission;
		this.nomPiece = nomPiece;
		this.nom_type_Piece = nom_type_Piece;
		this.isFinish = isFinish;
	}
	
	
	//All Getters and Setters
	
	public int getIdMission() {
		return idMission;
	}
	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}
	public int getIdPiece() {
		return idPiece;
	}
	public void setIdPiece(int idPiece) {
		this.idPiece = idPiece;
	}
	public String getPiecePiece() {
		return nomPiece;
	}
	public void setPiecePiece(String piecePiece) {
		this.nomPiece = piecePiece;
	}
	public float getLongueurPiece() {
		return longueurPiece;
	}
	public void setLongueurPiece(float longueurPiece) {
		this.longueurPiece = longueurPiece;
	}
	public float getLargeurPiece() {
		return largeurPiece;
	}
	public void setLargeurPiece(float largeurPiece) {
		this.largeurPiece = largeurPiece;
	}
	public float getHauteurPiece() {
		return hauteurPiece;
	}
	public void setHauteurPiece(float hauteurPiece) {
		this.hauteurPiece = hauteurPiece;
	}
	public String getInclusDansPiece() {
		return inclusDansPiece;
	}
	public void setInclusDansPiece(String inclusDansPiece) {
		this.inclusDansPiece = inclusDansPiece;
	}
	public int getIdOperaPhoto() {
		return idOperaPhoto;
	}
	public void setIdOperaPhoto(int idOperaPhoto) {
		this.idOperaPhoto = idOperaPhoto;
	}
	public String getNotesPiece() {
		return notesPiece;
	}
	public void setNotesPiece(String notesPiece) {
		this.notesPiece = notesPiece;
	}
	public String getNomTypePiece() {
		return nom_type_Piece;
	}
	public void setNomTypePiece(String nom_type_Piece) {
		this.nom_type_Piece = nom_type_Piece;
	}
	public int getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
	}

	public String getNomPiece() {
		return nomPiece;
	}

	public void setNomPiece(String nomPiece) {
		this.nomPiece = nomPiece;
	}

	public String getNom_type_Piece() {
		return nom_type_Piece;
	}

	public void setNom_type_Piece(String nom_type_Piece) {
		this.nom_type_Piece = nom_type_Piece;
	}

    public boolean isHasUserStartedDeletion() {
        return hasUserStartedDeletion;
    }

    public void setHasUserStartedDeletion(boolean hasUserStartedDeletion) {
        this.hasUserStartedDeletion = hasUserStartedDeletion;
    }

	public boolean isHasUserStartedReport() {
		return hasUserStartedReport;
	}

	public void setHasUserStartedReport(boolean hasUserStartedReport) {
		this.hasUserStartedReport = hasUserStartedReport;
	}
}
