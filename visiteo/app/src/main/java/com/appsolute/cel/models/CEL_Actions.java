package com.appsolute.cel.models;

public class CEL_Actions {
	
	private int idActions;
	private int idElement;
	private String actionActions;
	private float quantiteActions;
	private String uniteActions;
	private String noteActions;
	
	
	//Constructor using all fields	
	
	public CEL_Actions(int idActions, int idElement, String actionActions,
			float quantiteActions, String uniteActions, String noteActions) {
		super();
		this.idActions = idActions;
		this.idElement = idElement;
		this.actionActions = actionActions;
		this.quantiteActions = quantiteActions;
		this.uniteActions = uniteActions;
		this.noteActions = noteActions;
	}
	
	/**
	 * Creating an CEL_Aciton from another CEL_Action, but missing idElement
	 * 
	 * @param actions
	 */
	public CEL_Actions(CEL_Actions actions) {
		super();
		this.actionActions = actions.actionActions;
		this.quantiteActions = actions.quantiteActions;
		this.uniteActions = actions.uniteActions;
		this.noteActions = actions.noteActions;
	}
	
	//Constructor without fields
	public CEL_Actions() {
		super();
	}
	
	
	//All getters and setters
	
	public int getIdActions() {
		return idActions;
	}
	public void setIdActions(int idActions) {
		this.idActions = idActions;
	}
	public int getIdElement() {
		return idElement;
	}
	public void setIdElement(int idElement) {
		this.idElement = idElement;
	}
	public String getActionActions() {
		return actionActions;
	}
	public void setActionActions(String actionActions) {
		this.actionActions = actionActions;
	}
	public float getQuantiteActions() {
		return quantiteActions;
	}
	public void setQuantiteActions(float quantiteActions) {
		this.quantiteActions = quantiteActions;
	}
	public String getUniteActions() {
		return uniteActions;
	}
	public void setUniteActions(String uniteActions) {
		this.uniteActions = uniteActions;
	}
	public String getNoteActions() {
		return noteActions;
	}
	public void setNoteActions(String noteActions) {
		this.noteActions = noteActions;
	}
}
