package com.appsolute.cel.models;

public class Photo_Element_Lier {
	
	private int idOpera_Photos;
	private int idElement;
	
	public Photo_Element_Lier() {
		super();
	}

	public Photo_Element_Lier(int idOpera_Photos, int idElement) {
		super();
		this.idOpera_Photos = idOpera_Photos;
		this.idElement = idElement;
	}

	public int getIdOpera_Photos() {
		return idOpera_Photos;
	}

	public void setIdOpera_Photos(int idOpera_Photos) {
		this.idOpera_Photos = idOpera_Photos;
	}

	public int getIdElement() {
		return idElement;
	}

	public void setIdElement(int idElement) {
		this.idElement = idElement;
	}

}
