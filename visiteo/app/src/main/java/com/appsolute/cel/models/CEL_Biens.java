package com.appsolute.cel.models;


public class CEL_Biens {
	
	private int idBiens;
	private String adresseBiens;
	private String suiteBiens;
	private String codePostalBiens;
	private String villeBiens;
	private int typeBiens;
	private int piecesBiens;
	private float surfaceBiens;
	private String etageBiens;
	private String digicodeBiens;
	private String immeubleBiens;
	private String lotBiens;
	private String mandatBiens;
	private String interieur;
	
	
	
	
	//Constructor without fields
	
	public CEL_Biens() {
		super();
	}

	//Constructor using all fields
	
	public CEL_Biens(String adresseBiens, String suiteBiens,
			String codePostalBiens, String villeBiens, int typeBiens,
			int piecesBiens,
			float surfaceBiens, String etageBiens,
			String digicodeBiens, String immeubleBiens, 
			String lotBiens, String mandatBiens) {
		super();
		this.adresseBiens = adresseBiens;
		this.suiteBiens = suiteBiens;
		this.codePostalBiens = codePostalBiens;
		this.villeBiens = villeBiens;
		this.typeBiens = typeBiens;
		this.piecesBiens = piecesBiens;
		this.surfaceBiens = surfaceBiens;
		this.etageBiens = etageBiens;
		this.digicodeBiens = digicodeBiens;
		this.immeubleBiens = immeubleBiens;
		this.lotBiens = lotBiens;
		this.mandatBiens = mandatBiens;
	}
	
	//All Getters and Setters
	
	public int getIdBiens() {
		return idBiens;
	}
	public void setIdBiens(int idBiens) {
		this.idBiens = idBiens;
	}
	public String getAdresseBiens() {
		return adresseBiens;
	}
	public void setAdresseBiens(String adresseBiens) {
		this.adresseBiens = adresseBiens;
	}
	public String getSuiteBiens() {
		return suiteBiens;
	}
	public void setSuiteBiens(String suiteBiens) {
		this.suiteBiens = suiteBiens;
	}
	public String getCodePostalBiens() {
		return codePostalBiens;
	}
	public void setCodePostalBiens(String codePostalBiens) {
		this.codePostalBiens = codePostalBiens;
	}
	public String getVilleBiens() {
		return villeBiens;
	}
	public void setVilleBiens(String villeBiens) {
		this.villeBiens = villeBiens;
	}
	public int getTypeBiens() {
		return typeBiens;
	}
	public void setTypeBiens(int typeBiens) {
		this.typeBiens = typeBiens;
	}
	public int getPiecesBiens() {
		return piecesBiens;
	}
	public void setPiecesBiens(int piecesBiens) {
		this.piecesBiens = piecesBiens;
	}
	public float getSurfaceBiens() {
		return surfaceBiens;
	}
	public void setSurfaceBiens(float surfaceBiens) {
		this.surfaceBiens = surfaceBiens;
	}
	public String getEtageBiens() {
		return etageBiens;
	}
	public void setEtageBiens(String etageBiens) {
		this.etageBiens = etageBiens;
	}
	public String getDigicodeBiens() {
		return digicodeBiens;
	}
	public void setDigicodeBiens(String digicodeBiens) {
		this.digicodeBiens = digicodeBiens;
	}
	public String getImmeubleBiens() {
		return immeubleBiens;
	}
	public void setImmeubleBiens(String immeubleBiens) {
		this.immeubleBiens = immeubleBiens;
	}
	public String getLotBiens() {
		return lotBiens;
	}
	public void setLotBiens(String lotBiens) {
		this.lotBiens = lotBiens;
	}
	public String getMandatBiens() {
		return mandatBiens;
	}
	public void setMandatBiens(String mandatBiens) {
		this.mandatBiens = mandatBiens;
	}

	public String getInterieur() {
		return interieur;
	}

	public void setInterieur(String interieur) {
		this.interieur = interieur;
	}
}
