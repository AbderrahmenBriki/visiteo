package com.appsolute.cel.models;

/**
 * 
 * Room class is used to parse XmlFile named "Rooms"
 * 
 * @author lucien
 * 
 */
public class Room {
	
	private int idRoom;
	private String descriptionRoom;
	private String mandatoryRoom;
	
	
	/**
	 * Build an empty Room
	 */
	public Room() {
		super();
	}
	
	/**
	 * Build a Room without an Id
	 * 
	 * @param descriptionRoom
	 * @param mandatoryRoom
	 */
	public Room(String descriptionRoom, String mandatoryRoom) {
		super();
		
		this.descriptionRoom = descriptionRoom;
		this.mandatoryRoom = mandatoryRoom;
	}


	/**
	 * Build a complete Room
	 * 
	 * @param idRoom
	 * @param descriptionRoom
	 * @param mandatoryRoom
	 */
	public Room(int idRoom, String descriptionRoom, String mandatoryRoom) {
		super();
		
		this.idRoom = idRoom;
		this.descriptionRoom = descriptionRoom;
		this.mandatoryRoom = mandatoryRoom;
	}

	
	//Getters and Setters
	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
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
}
