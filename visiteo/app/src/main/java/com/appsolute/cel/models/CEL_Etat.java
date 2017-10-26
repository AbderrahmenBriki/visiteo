package com.appsolute.cel.models;

public class CEL_Etat {
	
	private int idEtat;
	private String descriptionEtat;
	
	/**
	 * Build an empty CEL_Etat
	 */
	public CEL_Etat() {}
	
	
	/**
	 * Build a CEL_Etat only with description
	 * 
	 * @param descriptionEtat
	 */
	public CEL_Etat(String descriptionEtat) {
		this.descriptionEtat = descriptionEtat;
	}
	
	
	/**
	 * Build a complete CEL_Etat
	 * 
	 * @param idEtat
	 * @param descriptionEtat
	 */
	public CEL_Etat(int idEtat, String descriptionEtat) {
		this.idEtat = idEtat;
		this.descriptionEtat = descriptionEtat;
	}

	
	public int getIdEtat() {
		return idEtat;
	}
	public void setIdEtat(int idEtat) {
		this.idEtat = idEtat;
	}
	public String getDescriptionEtat() {
		return descriptionEtat;
	}
	public void setDescriptionEtat(String descriptionEtat) {
		this.descriptionEtat = descriptionEtat;
	}
}
