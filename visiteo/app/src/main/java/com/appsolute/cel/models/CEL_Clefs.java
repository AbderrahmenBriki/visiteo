package com.appsolute.cel.models;


public class CEL_Clefs {
	
	private int idClefs;
	private int typeClefs;
	private int nombreClefVerifiee;
	private boolean hsClefs;
	private int nombreClefNonVerifiee;
	private int idMission;
	private int total;
	private String comment;
	
	/**
	 * Build an empty CEL_Clefs
	 * 
	 */
	public CEL_Clefs() {
		super();
	}
	
	public CEL_Clefs(int idMission) {
		super();
		this.idMission = idMission;
	}

	
	/**
	 * Build a CEL_Clefs without an id
	 * 
	 * @param typeClefs
	 * @param nombreClefVerifiee
	 * @param hsClefs
	 * @param nombreClefNonVerifiee
	 * @param idMission
	 */
	public CEL_Clefs(int typeClefs, int nombreClefVerifiee, boolean hsClefs,
			int nombreClefNonVerifiee, int idMission, int total, String comment) {
		super();
		this.typeClefs = typeClefs;
		this.nombreClefVerifiee = nombreClefVerifiee;
		this.hsClefs = hsClefs;
		this.nombreClefNonVerifiee = nombreClefNonVerifiee;
		this.idMission = idMission;
		this.total = total;
		this.comment = comment;
	}
	
	

	/**
	 * Build a full CEL_Clefs
	 * 
	 * @param idClefs
	 * @param typeClefs
	 * @param nombreClefVerifiee
	 * @param hsClefs
	 * @param nombreClefNonVerifiee
	 * @param idMission
	 */
	public CEL_Clefs(int idClefs, int typeClefs, int nombreClefVerifiee,
			boolean hsClefs, int nombreClefNonVerifiee, int idMission, int total, String comment) {
		super();
		this.idClefs = idClefs;
		this.typeClefs = typeClefs;
		this.nombreClefVerifiee = nombreClefVerifiee;
		this.hsClefs = hsClefs;
		this.nombreClefNonVerifiee = nombreClefNonVerifiee;
		this.idMission = idMission;
		this.total = total;
		this.comment = comment;
	}


	//All Getters and Setters
	
	public int getIdClefs() {
		return idClefs;
	}
	public void setIdClefs(int idClefs) {
		this.idClefs = idClefs;
	}
	public int getTypeClefs() {
		return typeClefs;
	}
	public void setTypeClefs(int typeClefs) {
		this.typeClefs = typeClefs;
	}
	public int getNombreClefVerifiee() {
		return nombreClefVerifiee;
	}
	public void setNombreClefVerifiee(int nombreClefVerifiee) {
		this.nombreClefVerifiee = nombreClefVerifiee;
	}
	public boolean isHsClefs() {
		return hsClefs;
	}
	public void setHsClefs(boolean hsClefs) {
		this.hsClefs = hsClefs;
	}
	public int getNombreClefNonVerifiee() {
		return nombreClefNonVerifiee;
	}
	public void setNombreClefNonVerifiee(int nombreClefNonVerifiee) {
		this.nombreClefNonVerifiee = nombreClefNonVerifiee;
	}
	public int getIdMission() {
		return idMission;
	}
	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getTotalClefs() {
		return (getNombreClefNonVerifiee()+getNombreClefVerifiee());
	}
}
