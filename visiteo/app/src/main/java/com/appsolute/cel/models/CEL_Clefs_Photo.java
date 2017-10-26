package com.appsolute.cel.models;


public class CEL_Clefs_Photo {
	
	private int idClefsPhoto;
	private byte[] photo;
	private int idMission;
	private int total;
	
	public int getIdClefsPhoto() {
		return idClefsPhoto;
	}

	public void setIdClefsPhoto(int idClefsPhoto) {
		this.idClefsPhoto = idClefsPhoto;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
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

	public CEL_Clefs_Photo(int idClefsPhoto, byte[] photo, int idMission,
			int total) {
		super();
		this.idClefsPhoto = idClefsPhoto;
		this.photo = photo;
		this.idMission = idMission;
		this.total = total;
	}

	
	public CEL_Clefs_Photo() {
		super();
	}

	
}
