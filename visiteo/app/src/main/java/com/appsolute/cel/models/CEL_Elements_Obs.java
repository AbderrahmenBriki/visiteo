package com.appsolute.cel.models;

public class CEL_Elements_Obs {
	
	private int idElementsObs;
	private int idElements;
	private String obsElementsObs;
	

	/**
	 * Build a complete CEL_Element_Obs
	 * 
	 * @param idElementsObs
	 * @param idElements
	 * @param obsElementsObs
	 */
	public CEL_Elements_Obs(int idElementsObs, int idElements, String obsElementsObs) {
		super();
		this.idElementsObs = idElementsObs;
		this.idElements = idElements;
		this.obsElementsObs = obsElementsObs;
	}
	
	/**
	 * Build an empty CEL_Element_Obs
	 */
	public CEL_Elements_Obs() {
		// TODO Auto-generated constructor stub
	}

	
	
	//All Getters and Setters

	public int getIdElements() {
		return idElements;
	}
	public void setIdElements(int idElements) {
		this.idElements = idElements;
	}
	public int getIdElementsObs() {
		return idElementsObs;
	}
	public void setIdElementsObs(int idElementsObs) {
		this.idElementsObs = idElementsObs;
	}
	public String getObsElementsObs() {
		return obsElementsObs;
	}
	public void setObsElementsObs(String obsElementsObs) {
		this.obsElementsObs = obsElementsObs;
	}
}
