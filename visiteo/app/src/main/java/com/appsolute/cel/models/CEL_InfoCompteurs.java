package com.appsolute.cel.models;

public class CEL_InfoCompteurs {

	private int id;
	private int numero;
	private String commande;
	private String compteur;
	private String chauffage;
	private String emplacement;
	private int idMission;


	public CEL_InfoCompteurs() {
		super();
	}
	
	public CEL_InfoCompteurs(int id, int numero, String commande,
			String compteur, String chauffage, String emplacement, int idMission) {
		super();
		this.id = id;
		this.numero = numero;
		this.commande = commande;
		this.compteur = compteur;
		this.chauffage = chauffage;
		this.emplacement = emplacement;
		this.idMission = idMission;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getIdMission() {
		return idMission;
	}

	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}


	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCommande() {
		return commande;
	}

	public void setCommande(String commande) {
		this.commande = commande;
	}

	public String getCompteur() {
		return compteur;
	}

	public void setCompteur(String compteur) {
		this.compteur = compteur;
	}

	public String getChauffage() {
		return chauffage;
	}

	public void setChauffage(String chauffage) {
		this.chauffage = chauffage;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}	
}
