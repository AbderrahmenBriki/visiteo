package com.appsolute.cel.models;


/**
 * 
 * @author lucien
 *
 */
public class CEL_ECS {
	
	private int idECS;
	private int typeECS;
	private int marqueECS;
	
	/**
	 * Build an empty CEL_ECS
	 */
	public CEL_ECS() {}
	
	/**
	 * Build a CEL_ECS without an Id
	 * 
	 * @param typeECS
	 * @param marqueECS
	 */
	public CEL_ECS(int typeECS, int marqueECS) {
		this.typeECS = typeECS;
		this.marqueECS = marqueECS;
	}
	
	/**
	 * Build a complete CEL_ECS
	 * 
	 * @param idECS
	 * @param typeECS
	 * @param marqueECS
	 */
	public CEL_ECS(int idECS, int typeECS, int marqueECS) {
		this.typeECS = typeECS;
		this.marqueECS = marqueECS;
	}

	public int getIdECS() {
		return idECS;
	}
	public void setIdECS(int idECS) {
		this.idECS = idECS;
	}
	public int getTypeEcs() {
		return typeECS;
	}
	public void setTypeEcs(int typeECS) {
		this.typeECS = typeECS;
	}
	public int getMarqueEcs() {
		return marqueECS;
	}
	public void setMarqueEcs(int marqueECS) {
		this.marqueECS = marqueECS;
	}
}
