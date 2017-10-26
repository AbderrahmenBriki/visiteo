package com.appsolute.cel.models;

public class ItemsPack {
	
	private int idItemsPack;
	private String description;
	private int idRoom;
	
	public ItemsPack() {
		super();
	}
	
	public ItemsPack(int idItemsPack, String description) {
		super();
		this.idItemsPack = idItemsPack;
		this.description = description;
	}

	public int getIdItemsPack() {
		return idItemsPack;
	}

	public void setIdItemsPack(int idItemsPack) {
		this.idItemsPack = idItemsPack;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
}
