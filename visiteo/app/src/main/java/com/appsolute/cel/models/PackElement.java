package com.appsolute.cel.models;

public class PackElement {
	
	private int idPackElement;
	private String item;
	private String type;
	private int idItemType;
	private int idItemPack;
	
	public PackElement() {
		super();
	}
	
	public PackElement(int idPackElement, String item, String type,
			int idItemType, int idItemPack) {
		super();
		this.idPackElement = idPackElement;
		this.item = item;
		this.type = type;
		this.idItemType = idItemType;
		this.idItemPack = idItemPack;
	}
	public int getIdPackElement() {
		return idPackElement;
	}
	public void setIdPackElement(int idPackElement) {
		this.idPackElement = idPackElement;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getIdItemType() {
		return idItemType;
	}
	public void setIdItemType(int idItemType) {
		this.idItemType = idItemType;
	}
	public int getIdItemPack() {
		return idItemPack;
	}
	public void setIdItemPack(int idItemPack) {
		this.idItemPack = idItemPack;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}		
}
