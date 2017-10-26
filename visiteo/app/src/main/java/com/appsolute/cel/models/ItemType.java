package com.appsolute.cel.models;

/**
 * ItemType class is used to parse XmlFile named "Rooms"
 * 
 * @author lucien
 *
 */
public class ItemType {
	
	private int idItemType;
	private String descriptionItemType;
	private String pictureIDItemType;
	private String repairIDItemType;
	private String cleanIDItemType;
	private String damageItemType;
	private int idRoomItem;
	
	/**
	 * Build an empty ItemType
	 */
	public ItemType() {
		super();
	}
	
	/**
	 * Build an ItemType without an Id
	 * 
	 * @param descriptionItemType
	 * @param pictureIDItemType
	 * @param repairIDItemType
	 * @param cleanIDItemType
	 * @param damageItemType
	 * @param idRootItem
	 */
	public ItemType(String descriptionItemType,
			String pictureIDItemType, String repairIDItemType,
			String cleanIDItemType, String damageItemType,
			int idRootItem) {
		super();
		this.descriptionItemType = descriptionItemType;
		this.pictureIDItemType = pictureIDItemType;
		this.repairIDItemType = repairIDItemType;
		this.cleanIDItemType = cleanIDItemType;
		this.damageItemType = damageItemType;
	}
	
	/**
	 * Build a complete ItemType
	 * 
	 * @param idItemType
	 * @param descriptionItemType
	 * @param pictureIDItemType
	 * @param repairIDItemType
	 * @param cleanIDItemType
	 * @param damageItemType
	 * @param idRootItem
	 */
	public ItemType(int idItemType, String descriptionItemType,
			String pictureIDItemType, String repairIDItemType,
			String cleanIDItemType, String damageItemType,
			int idRootItem) {
		super();
		this.idItemType = idItemType;
		this.descriptionItemType = descriptionItemType;
		this.pictureIDItemType = pictureIDItemType;
		this.repairIDItemType = repairIDItemType;
		this.cleanIDItemType = cleanIDItemType;
		this.damageItemType = damageItemType;
	}
	
	
	//Getters and Setters

	public int getIdItemType() {
		return idItemType;
	}
	public void setIdItemType(int idItemType) {
		this.idItemType = idItemType;
	}
	public String getDescriptionItemType() {
		return descriptionItemType;
	}
	public void setDescriptionItemType(String descriptionItemType) {
		this.descriptionItemType = descriptionItemType;
	}
	public String getPictureIDItemType() {
		return pictureIDItemType;
	}
	public void setPictureIDItemType(String pictureIDItemType) {
		this.pictureIDItemType = pictureIDItemType;
	}
	public String getRepairIDItemType() {
		return repairIDItemType;
	}
	public void setRepairIDItemType(String repairIDItemType) {
		this.repairIDItemType = repairIDItemType;
	}
	public String getCleanIDItemType() {
		return cleanIDItemType;
	}
	public void setCleanIDItemType(String cleanIDItemType) {
		this.cleanIDItemType = cleanIDItemType;
	}
	public String getDamageItemType() {
		return damageItemType;
	}
	public void setDamageItemType(String damageItemType) {
		this.damageItemType = damageItemType;
	}
	public int getIdRoomItem() {
		return idRoomItem;
	}
	public void setIdRoomItem(int idRootItem) {
		this.idRoomItem = idRootItem;
	}
}
