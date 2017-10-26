package com.appsolute.cel.models;

public class CEL_Compteurs {

	private int idCompteurs;
	private int index_1Compteurs;
	private int index_2Compteurs;
	private int typeCompteurs;
	private int idEtat;
	private int idMission;
	private String etat;
	private int idPhoto;

	//Constructor without field

	public CEL_Compteurs() {
		super();
	}


	/**
	 * Constructor of CEL_Compteur.
	 * Build a complete CEL_Compteur.
	 * 
	 * @param idCompteurs
	 * @param idEtat
	 * @param index_1Compteurs
	 * @param index_2Compteurs
	 * @param typeCompteurs
	 * @param photos
	 * @param idMission
	 */
	public CEL_Compteurs(int idCompteurs,
			int index_1Compteurs, int index_2Compteurs,
			int typeCompteurs, byte[] photos, 
			int idMission, int idEtat) {
		super();
		this.idCompteurs = idCompteurs;
		this.idEtat = idEtat;
		this.index_1Compteurs = index_1Compteurs;
		this.index_2Compteurs = index_2Compteurs;
		this.typeCompteurs = typeCompteurs;
		this.idMission = idMission;
	}

	/**
	 * Constructor of CEL_Compteur.
	 * Build a CEL_Compteur without a photo
	 * 
	 * @param idEtat
	 * @param index_1Compteurs
	 * @param index_2Compteurs
	 * @param typeCompteurs
	 * @param idMission
	 */
	public CEL_Compteurs(int index_1Compteurs, 
			int index_2Compteurs, int typeCompteurs, 
			int idMission, int idEtat) {
		this.idEtat = idEtat;
		this.index_1Compteurs = index_1Compteurs;
		this.index_2Compteurs = index_2Compteurs;
		this.typeCompteurs = typeCompteurs;
		this.idMission = idMission;
	}


	public CEL_Compteurs(CEL_Compteurs compteurs) {
		this.idCompteurs = compteurs.idCompteurs;
		this.idEtat = compteurs.idEtat;
		this.index_1Compteurs = compteurs.index_1Compteurs;
		this.index_2Compteurs = compteurs.index_2Compteurs;
		this.typeCompteurs = compteurs.typeCompteurs;
		this.idMission = compteurs.idMission;
	}

	//All Getters and Setters

	public int getIdCompteurs() {
		return idCompteurs;
	}
	public void setIdCompteurs(int idCompteurs) {
		this.idCompteurs = idCompteurs;
	}
	public int getIndex_1Compteurs() {
		return index_1Compteurs;
	}
	public void setIndex_1Compteurs(int index_1Compteurs) {
		this.index_1Compteurs = index_1Compteurs;
	}
	public int getIndex_2Compteurs() {
		return index_2Compteurs;
	}
	public void setIndex_2Compteurs(int index_2Compteurs) {
		this.index_2Compteurs = index_2Compteurs;
	}
	public int getTypeCompteurs() {
		return typeCompteurs;
	}
	public void setTypeCompteurs(int typeCompteurs) {
		this.typeCompteurs = typeCompteurs;
	}
	public int getIdEtat() {
		return idEtat;
	}
	public void setIdEtat(int idEtat) {
		this.idEtat = idEtat;
	}
	public int getIdMission() {
		return idMission;
	}
	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}	
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public int getIdPhoto() {
		return idPhoto;
	}
	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}
	
}
