package com.appsolute.cel.models;

public class CEL_Chauffage {
	
	private int idChauffage;
	private int typeChauffage;
	private int chaudiereChauffage;
	private int marqueChauffage;
	private String entretienChauffage;
	private boolean horsService;
	
	
	/**
	 * Build an empty CEL_Chauffage
	 */
	public CEL_Chauffage() {}
	
	
	/**
	 * Create a CEL_Chauffage without an idChauffage,
	 * it's using to create a CEL_Chauffage on 
	 * database with a DAO
	 * 
	 * @param typeChauffage
	 * @param chaudiereChauffage
	 * @param marqueChauffage
	 * @param entretienChauffage
	 * @param horsService
	 */
	public CEL_Chauffage(int typeChauffage,
			int chaudiereChauffage, int marqueChauffage,
			String entretienChauffage, boolean horsService) {
		this.typeChauffage = typeChauffage;
		this.chaudiereChauffage = chaudiereChauffage;
		this.marqueChauffage = marqueChauffage;
		this.entretienChauffage = entretienChauffage;
		this.horsService = horsService;
	}
	
	
	//All Getters and Setters

	public int getIdChauffage() {
		return idChauffage;
	}
	public void setIdChauffage(int idChauffage) {
		this.idChauffage = idChauffage;
	}
	public int getTypeChauffage() {
		return typeChauffage;
	}
	public void setTypeChauffage(int typeChauffage) {
		this.typeChauffage = typeChauffage;
	}
	public int getChaudiereChauffage() {
		return chaudiereChauffage;
	}
	public void setChaudiereChauffage(int chaudiereChauffage) {
		this.chaudiereChauffage = chaudiereChauffage;
	}
	public int getMarqueChauffage() {
		return marqueChauffage;
	}
	public void setMarqueChauffage(int marqueChauffage) {
		this.marqueChauffage = marqueChauffage;
	}
	public String getEntretienChauffage() {
		return entretienChauffage;
	}
	public void setEntretienChauffage(String entretienChauffage) {
		this.entretienChauffage = entretienChauffage;
	}
	public boolean getHorsService() {
		return horsService;
	}
	public void setHorsService(boolean horsService) {
		this.horsService = horsService;
	}
}
