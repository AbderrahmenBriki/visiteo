package com.appsolute.cel.models;

import java.util.HashMap;

/**
 * 
 * RoomItem class is used to parse XmlFile named "Rooms"
 * 
 * @author lucien
 * 
 */
public class RoomItem {
	
	private int idRoomItem;
	private String descriptionRoomItem;
	private String displayRoomItem;
	private String countableRoomItem;
	private int itemGroupDescription;
	private int idRoom;
	
	/**
	 * Build an empty RoomItem
	 */
	public RoomItem() {
		super();
	}
	
	/**
	 * Build a RoomItem without an Id
	 * 
	 * @param displayRoomItem
	 * @param descriptionRoomItem
	 * @param countableRoomItem
	 * @param itemGroupDescription
	 * @param idRoom
	 */
	public RoomItem(String displayRoomItem,
			String descriptionRoomItem,
			String countableRoomItem, int idRoom,
			int itemGroupDescription) {
		super();
		this.displayRoomItem = displayRoomItem;
		this.descriptionRoomItem = descriptionRoomItem;
		this.countableRoomItem = countableRoomItem;
		this.itemGroupDescription = itemGroupDescription;
		this.idRoom = idRoom;
	}
	
	/**
	 * Build a complete RoomItem
	 * 
	 * @param idRoomItem
	 * @param displayRoomItem
	 * @param countableRoomItem
	 * @param itemGroupDescription
	 * @param idRoom
	 */
	public RoomItem(int idRoomItem, String displayRoomItem,
			String descriptionString,
			String countableRoomItem, int idRoom,
			int itemGroupDescription) {
		super();
		this.idRoomItem = idRoomItem;
		this.descriptionRoomItem = descriptionString;
		this.displayRoomItem = displayRoomItem;
		this.countableRoomItem = countableRoomItem;
		this.idRoom = idRoom;
	}

	
	//Getters and Setters
	
	public int getIdRoomItem() {
		return idRoomItem;
	}
	public void setIdRoomItem(int idRoomItem) {
		this.idRoomItem = idRoomItem;
	}
	public String getDescriptionString() {
		return descriptionRoomItem;
	}
	public void setDescriptionString(String descriptionString) {
		this.descriptionRoomItem = descriptionString;
	}

	public String getDisplayRoomItem() {
		return displayRoomItem;
	}
	public void setDisplayRoomItem(String displayRoomItem) {
		this.displayRoomItem = displayRoomItem;
	}
	public String getCountableRoomItem() {
		return countableRoomItem;
	}
	public void setCountableRoomItem(String countableRoomItem) {
		this.countableRoomItem = countableRoomItem;
	}
	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	public String getDescriptionRoomItem() {
		return descriptionRoomItem;
	}
	public void setDescriptionRoomItem(String descriptionRoomItem) {
		this.descriptionRoomItem = descriptionRoomItem;
	}
	public int getItemGroupDescription() {
		return itemGroupDescription;
	}
	public void setItemGroupDescription(int itemGroupDescription) {
		this.itemGroupDescription = itemGroupDescription;
	}
	
	/**
	 * Display the information contained in the RoomItem object
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		HashMap<String,String> map = new HashMap<>();
		map.put("idRoom",String.valueOf(idRoom));
		map.put("idRoomItem", String.valueOf(idRoomItem));
		map.put("itemGroupDescription",String.valueOf(itemGroupDescription));
		map.put("descriptionRoomItem",descriptionRoomItem);
		map.put("countableRoomItem", countableRoomItem);
		map.put("displayRoomItem", displayRoomItem);
		return map.toString();
	}
}
