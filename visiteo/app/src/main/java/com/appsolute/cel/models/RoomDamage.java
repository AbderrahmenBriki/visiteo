package com.appsolute.cel.models;

/**
 * 
 * Room class is used to parse XmlFile named "Rooms"
 * 
 * @author Karthikeyan
 * 
 */
public class RoomDamage {
	
	private int idRoomDamage;
	private String descriptionRoom;
	private String mandatoryRoom;
	private int idItemType;
	
	
	/**
	 * Build an empty RoomDamage
	 */
	public RoomDamage() {
		super();
	}
	
	/**
	 * Build a RoomDamage without an Id
	 * 
	 * @param descriptionRoom
	 * @param mandatoryRoom
	 */
	public RoomDamage(String descriptionRoom, String mandatoryRoom, int idItemType) {
		this.descriptionRoom = descriptionRoom;
		this.mandatoryRoom = mandatoryRoom;
		this.idItemType = idItemType;
	}


	/**
	 * Build a complete RoomDamage
	 * 
	 * @param idRoomDamage
	 * @param descriptionRoom
	 * @param mandatoryRoom
	 */
	public RoomDamage(int idRoomDamage, String descriptionRoom, String mandatoryRoom, int idItemType) {		
		this.idRoomDamage = idRoomDamage;
		this.descriptionRoom = descriptionRoom;
		this.mandatoryRoom = mandatoryRoom;
		this.idItemType = idItemType;
	}

	
	//Getters and Setters
	public int getIdRoomDamage() {
		return idRoomDamage;
	}
	public void setIdRoomDamage(int idRoomDamage) {
		this.idRoomDamage = idRoomDamage;
	}
	public String getDescriptionRoom() {
		return descriptionRoom;
	}
	public void setDescriptionRoom(String descriptionRoom) {
		this.descriptionRoom = descriptionRoom;
	}
	public String getMandatoryRoom() {
		return mandatoryRoom;
	}
	public void setMandatoryRoom(String mandatoryRoom) {
		this.mandatoryRoom = mandatoryRoom;
	}
	public int getIdItemType() {
		return idItemType;
	}
	public void setIdItemType(int idItemType) {
		this.idItemType = idItemType;
	}
}
