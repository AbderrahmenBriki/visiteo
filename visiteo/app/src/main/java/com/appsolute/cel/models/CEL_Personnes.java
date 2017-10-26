package com.appsolute.cel.models;

public class CEL_Personnes {
	
	private int idPersonnes;
	private String nomPersonnes;
	private String prenomPersonnes;
	private String adressePersonnes;
	private String suitePersonnes;
	private String codePostalPersonnes;
	private String villePersonnes;
	private String representantPersonnes;
	private String emailPersonnes;
	private String telephonePersonnes;
	private int typePersonnes;
	private String dateEntree;
	
	/**
	 * Build an empty CEL_Personnes
	 */
	public CEL_Personnes() {
		super();
	}
	
	/**
	 * Constructor of CEL_Personnes without an id
	 * 
	 * @param nomPersonnes
	 * @param prenomPersonnes
	 * @param adressePersonnes
	 * @param suitePersonnes
	 * @param codePostalPersonnes
	 * @param villePersonnes
	 * @param representantPersonnes
	 * @param emailPersonnes
	 * @param telephonePersonnes
	 * @param typePersonnes
	 */
	public CEL_Personnes(String nomPersonnes,
			String prenomPersonnes, String adressePersonnes,
			String suitePersonnes, String codePostalPersonnes,
			String villePersonnes, String representantPersonnes,
			String emailPersonnes, String telephonePersonnes,
			int typePersonnes) {
		super();
		this.nomPersonnes = nomPersonnes;
		this.prenomPersonnes = prenomPersonnes;
		this.adressePersonnes = adressePersonnes;
		this.suitePersonnes = suitePersonnes;
		this.codePostalPersonnes = codePostalPersonnes;
		this.villePersonnes = villePersonnes;
		this.representantPersonnes = representantPersonnes;
		this.emailPersonnes = emailPersonnes;
		this.telephonePersonnes = telephonePersonnes;
	}
	
	
	/**
	 * Constructor of CEL_Personnes
	 * 
	 * @param idPersonnes
	 * @param nomPersonnes
	 * @param prenomPersonnes
	 * @param adressePersonnes
	 * @param suitePersonnes
	 * @param codePostalPersonnes
	 * @param villePersonnes
	 * @param representantPersonnes
	 * @param emailPersonnes
	 * @param telephonePersonnes
	 */
	public CEL_Personnes(int idPersonnes, String nomPersonnes,
			String prenomPersonnes, String adressePersonnes,
			String suitePersonnes, String codePostalPersonnes,
			String villePersonnes, String representantPersonnes,
			String emailPersonnes, String telephonePersonnes) {
		super();
		this.idPersonnes = idPersonnes;
		this.nomPersonnes = nomPersonnes;
		this.prenomPersonnes = prenomPersonnes;
		this.adressePersonnes = adressePersonnes;
		this.suitePersonnes = suitePersonnes;
		this.codePostalPersonnes = codePostalPersonnes;
		this.villePersonnes = villePersonnes;
		this.representantPersonnes = representantPersonnes;
		this.emailPersonnes = emailPersonnes;
		this.telephonePersonnes = telephonePersonnes;
	}
	
	
	//All Getters and Setters
	
	public int getIdPersonnes() {
		return idPersonnes;
	}
	public void setIdPersonnes(int idPersonnes) {
		this.idPersonnes = idPersonnes;
	}
	public String getNomPersonnes() {
		return nomPersonnes;
	}
	public void setNomPersonnes(String nomPersonnes) {
		this.nomPersonnes = nomPersonnes;
	}
	public String getPrenomPersonnes() {
		return prenomPersonnes;
	}
	public void setPrenomPersonnes(String prenomPersonnes) {
		this.prenomPersonnes = prenomPersonnes;
	}
	public String getAdressePersonnes() {
		return adressePersonnes;
	}
	public void setAdressePersonnes(String adressePersonnes) {
		this.adressePersonnes = adressePersonnes;
	}
	public String getSuitePersonnes() {
		return suitePersonnes;
	}
	public void setSuitePersonnes(String suitePersonnes) {
		this.suitePersonnes = suitePersonnes;
	}
	public String getCodePostalPersonnes() {
		return codePostalPersonnes;
	}
	public void setCodePostalPersonnes(String codePostalPersonnes) {
		this.codePostalPersonnes = codePostalPersonnes;
	}
	public String getVillePersonnes() {
		return villePersonnes;
	}
	public void setVillePersonnes(String villePersonnes) {
		this.villePersonnes = villePersonnes;
	}
	public String getRepresentantPersonnes() {
		return representantPersonnes;
	}
	public void setRepresentantPersonnes(String representantPersonnes) {
		this.representantPersonnes = representantPersonnes;
	}
	public String getEmailPersonnes() {
		return emailPersonnes;
	}
	public void setEmailPersonnes(String emailPersonnes) {
		this.emailPersonnes = emailPersonnes;
	}
	public String getTelephonePersonnes() {
		return telephonePersonnes;
	}
	public void setTelephonePersonnes(String telephonePersonnes) {
		this.telephonePersonnes = telephonePersonnes;
	}
	public int getTypePersonnes() {
		return typePersonnes;
	}
	public void setTypePersonnes(int typePersonnes) {
		this.typePersonnes = typePersonnes;
	}
	public String getDateEntree() {
		return dateEntree;
	}
	public void setDateEntree(String dateSortie) {
		this.dateEntree = dateSortie;
	}
}
