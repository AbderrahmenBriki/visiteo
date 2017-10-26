package com.appsolute.cel.models;

public class SignatureItem {
	
	private int idSignature;
	private String type;
	private int idOperaPhoto;
	private int idMission;
	
	public SignatureItem() {
		super();
	}
	
	public int getIdSignature() {
		return idSignature;
	}
	public void setIdSignature(int idSignature) {
		this.idSignature = idSignature;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIdOperaPhoto() {
		return idOperaPhoto;
	}
	public void setIdOperaPhoto(int idOperaPhoto) {
		this.idOperaPhoto = idOperaPhoto;
	}
	public int getIdMission() {
		return idMission;
	}

	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}
	
	public SignatureItem(int idSignature, String type, int idOperaPhoto, int idMission) {
		super();
		this.idSignature = idSignature;
		this.type = type;
		this.idOperaPhoto = idOperaPhoto;
		this.idMission = idMission;
	}
	
	
}
