package com.appsolute.cel.models;

public class CEL_Elements {
	
	private int idElements;
	private int idPiece;
	private String elementElements;
	private String typeElements;
	private int typeElementsFromXML;
	private int quantiteElements;
	private String etatElements;
	private int idOperaPhoto;
	private int nombreTrousChevilleElements;
	private int isNettoyer;
	private int isCountable;
	private int itemGroupDescription;
	private int idRoomItem;
	private int idItemType;
	private int mandatory;
	private int ordre;

	//Constructor without field
	public CEL_Elements() {
		super();
	}

	
	//All Getters and Setters
	
	public int getIdPiece() {
		return idPiece;
	}
	public void setIdPiece(int idPiece) {
		this.idPiece = idPiece;
	}
	public int getIdElements() {
		return idElements;
	}
	public void setIdElements(int idElements) {
		this.idElements = idElements;
	}
	public String getElementElements() {
		return elementElements;
	}
	public void setElementElements(String elementElements) {
		this.elementElements = elementElements;
	}
	public String getTypeElements() {
		return typeElements;
	}
	public void setTypeElements(String typeElements) {
		this.typeElements = typeElements;
	}
	public int getTypeElementsFromXML() {
		return typeElementsFromXML;
	}
	public void setTypeElementsFromXML(int typeElementsFromXML) {
		this.typeElementsFromXML = typeElementsFromXML;
	}
	public int getQuantiteElements() {
		return quantiteElements;
	}
	public void setQuantiteElements(int quantiteElements) {
		this.quantiteElements = quantiteElements;
	}
	public String getEtatElements() {
		return etatElements;
	}
	public void setEtatElements(String etatElements) {
		this.etatElements = etatElements;
	}
	public int getNombreTrousChevilleElements() {
		return nombreTrousChevilleElements;
	}
	public void setNombreTrousChevilleElements(int nombreTrousChevilleElements) {
		this.nombreTrousChevilleElements = nombreTrousChevilleElements;
	}
	public int getIsNettoyer() {
		return isNettoyer;
	}
	public void setIsNettoyer(int isNettoyer) {
		this.isNettoyer = isNettoyer;
	}
	public int getIsCountable() {
		return isCountable;
	}
	public void setIsCountable(int isCountable) {
		this.isCountable = isCountable;
	}
	public int getItemGroupDescription() {
		return itemGroupDescription;
	}
	public void setItemGroupDescription(int itemGroupDescription) {
		this.itemGroupDescription = itemGroupDescription;
	}
	public int getIdRoomItem() {
		return idRoomItem;
	}
	public void setIdRoomItem(int idRoomItem) {
		this.idRoomItem = idRoomItem;
	}
	public int getIdItemType() {
		return idItemType;
	}
	public void setIdItemType(int idItemType) {
		this.idItemType = idItemType;
	}
	public int getIdOperaPhoto() {
		return idOperaPhoto;
	}
	public void setIdOperaPhoto(int idOperaPhoto) {
		this.idOperaPhoto = idOperaPhoto;
	}
    public int getMandatory() {
        return mandatory;
    }
    public void setMandatory(int mandatory) {
        this.mandatory = mandatory;
    }
	public int getOrdre() {
		return ordre;
	}
	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
}
