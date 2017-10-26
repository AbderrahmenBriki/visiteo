package com.appsolute.cel.models;

public class OPERA_Photos {
	
	private int idPhotos;
	private int numeroPhotos;
	private String emplacementPhotos;
	private Boolean degradationPhotos;
	private int ordrePhotos;
	private String dateRattachementPhotos;
	private int idMission;
	private byte[] photo;
	private String photoName;
	
	//Constructor using all fields
	
	public OPERA_Photos(int idPhotos, int numeroPhotos,
			String emplacementPhotos, Boolean degradationPhotos,
			int ordrePhotos, String dateRattachementPhotos) {
		super();
		this.idPhotos = idPhotos;
		this.numeroPhotos = numeroPhotos;
		this.emplacementPhotos = emplacementPhotos;
		this.degradationPhotos = degradationPhotos;
		this.ordrePhotos = ordrePhotos;
		this.dateRattachementPhotos = dateRattachementPhotos;
	}
	
	public OPERA_Photos(int idMission, byte[] photo) {
		super();
		this.idMission = idMission;
		this.photo = photo;
	}
	
	public OPERA_Photos(int idMission, byte[] photo, String photoName) {
		super();
		this.idMission = idMission;
		this.photo = photo;
		this.photoName = photoName;
	}

	//Constructor without field
	
	public OPERA_Photos() {
		super();
	}

	//All Getters and Setters
	
	public int getIdPhotos() {
		return idPhotos;
	}
	public void setIdPhotos(int idPhotos) {
		this.idPhotos = idPhotos;
	}
	public int getNumeroPhotos() {
		return numeroPhotos;
	}
	public void setNumeroPhotos(int numeroPhotos) {
		this.numeroPhotos = numeroPhotos;
	}
	public String getEmplacementPhotos() {
		return emplacementPhotos;
	}
	public void setEmplacementPhotos(String emplacementPhotos) {
		this.emplacementPhotos = emplacementPhotos;
	}
	public Boolean getDegradationPhotos() {
		return degradationPhotos;
	}
	public void setDegradationPhotos(Boolean degradationPhotos) {
		this.degradationPhotos = degradationPhotos;
	}
	public int getOrdrePhotos() {
		return ordrePhotos;
	}
	public void setOrdrePhotos(int ordrePhotos) {
		this.ordrePhotos = ordrePhotos;
	}
	public String getDateRattachementPhotos() {
		return dateRattachementPhotos;
	}
	public void setDateRattachementPhotos(String dateRattachementPhotos) {
		this.dateRattachementPhotos = dateRattachementPhotos;
	}
	public int getIdMission() {
		return idMission;
	}
	public void setIdMission(int idMission) {
		this.idMission = idMission;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
}
