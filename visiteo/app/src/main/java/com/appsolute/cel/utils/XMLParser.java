package com.appsolute.cel.utils;

import android.content.Context;

import com.appsolute.cel.DAO.ItemType_DAO;
import com.appsolute.cel.DAO.PackElement_DAO;
import com.appsolute.cel.DAO.RoomItem_DAO;
import com.appsolute.cel.DAO.Room_DAO;
import com.appsolute.cel.models.ItemType;
import com.appsolute.cel.models.ItemsPack;
import com.appsolute.cel.models.PackElement;
import com.appsolute.cel.models.RoomItem;
import com.appsolute.cel.DAO.ItemsPack_DAO;
import com.appsolute.cel.DAO.Room_Damage_DAO;
import com.appsolute.cel.models.Room;
import com.appsolute.cel.models.RoomDamage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {
	private static Room_DAO room_DAO;
	private static RoomItem_DAO roomItem_DAO;
	private static ItemType_DAO itemType_DAO;
	private static Room_Damage_DAO roomDamage_DAO;
	private static ItemsPack itemsPack;
	private static PackElement_DAO packElement_DAO;
	private static Room room;
	private static RoomItem roomItem;
	private static ItemsPack_DAO itemsPack_DAO;
	private static PackElement packElement;
	private static ItemType itemType;
	private static RoomDamage roomDamage;
	static int idRoom, idRoomItem, idItemType;

	/**
	 * Parse XMLFile named Rooms.xml
	 * 
	 * @param context context of Activity using XML Parser
	 * @param inputStream 
	 */
	public static void ParseXmlFile(Context context, InputStream inputStream) {

		room_DAO = new Room_DAO(context);
		roomItem_DAO = new RoomItem_DAO(context);
		itemType_DAO = new ItemType_DAO(context);
		roomDamage_DAO = new Room_Damage_DAO(context);
		itemsPack_DAO = new ItemsPack_DAO(context);
		packElement_DAO = new PackElement_DAO(context);


		room_DAO.deleteTable();
		roomItem_DAO.deleteTable();
		itemType_DAO.deleteTable();
		roomDamage_DAO.deleteTable();

		room = new Room();
		itemsPack = new ItemsPack();
		packElement = new PackElement();
		roomItem = new RoomItem();
		itemType = new ItemType();
		roomDamage = new RoomDamage();

		Node item;
		Node itemGroupNode;
		Node item2;
		Node item3;
		Node item4;

		Element ielem;
		Element itemGroupElement;
		Element item1;
		Element ielem1;
		Element ielem2;
		Element ielem3;

		NodeList itemList;
		NodeList itemLst1;	
		NodeList itemLst2;
		NodeList itemLst3;

		int isEtatPiece = 0;
		int positionItemGroup = 0;

		//Retrieve the XML from the path
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(inputStream));
			document.getDocumentElement().normalize();

			// This is the root node of each section you want to parse
			NodeList mainitemLst = document.getElementsByTagName("Rooms");
			Element mainitem = (Element)mainitemLst.item(0);
			NodeList itemLst = mainitem.getElementsByTagName("Room");


			// Loop through the XML passing the data to the arrays
			for (int i = 0; i < itemLst.getLength(); i++) {
				item = itemLst.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					ielem = (Element) item;
					if(item.getNodeName().equals("Room")) {	
						room.setDescriptionRoom(ielem.getAttribute("description"));
						room.setMandatoryRoom(ielem.getAttribute("mandatory"));
						idRoom = room_DAO.addValue(room);
					}


					itemLst1 = ielem.getElementsByTagName("ItemGroup");
                    boolean eauExist = false, equipeExist = false;
					for (int j = 0; j < itemLst1.getLength(); j++) {
						itemGroupNode = itemLst1.item(j);
						if (itemGroupNode.getNodeType() == Node.ELEMENT_NODE) {
							itemGroupElement = (Element) itemGroupNode;							
							if(itemGroupElement.getNodeName().equals("ItemGroup")) {
								if(itemGroupElement.getAttribute("description").equals("Etat de la pièce"))
									isEtatPiece = 1;
								else if (itemGroupElement.getAttribute("description").equals("Elements d'eau")) {
									isEtatPiece = 2;
									positionItemGroup = 1;
									eauExist = true;
								}
								else if(itemGroupElement.getAttribute("description").equals("Equipée")) {
									isEtatPiece = 1;
									positionItemGroup = 2;
									equipeExist = true;
								}
								else if (itemGroupElement.getAttribute("description").equals("Inventaire")) {
									isEtatPiece = 3;
									int position = 1;
									if(eauExist)
										position++;
									if(equipeExist)
										position++;

									positionItemGroup = position;
								}
								else
									break;

								item1 = (Element)itemLst1.item(positionItemGroup);
								positionItemGroup = 0;
								itemList = item1.getElementsByTagName("RoomItem");
								for (int k = 0; k < itemList.getLength(); k++) {
									item2 = itemList.item(k);
									if (item2.getNodeType() == Node.ELEMENT_NODE) {
										ielem1 = (Element) item2;
										if(item2.getNodeName().equals("RoomItem")) {
											roomItem.setDescriptionString(ielem1.getAttribute("description"));
											roomItem.setDisplayRoomItem(ielem1.getAttribute("display"));
											roomItem.setCountableRoomItem(ielem1.getAttribute("countable"));	
											roomItem.setItemGroupDescription(isEtatPiece);
											roomItem.setIdRoom(idRoom);
											idRoomItem = roomItem_DAO.addValue(roomItem);
										} 

										itemLst2 = ielem1.getElementsByTagName("ItemType");
										for (int m = 0; m < itemLst2.getLength(); m++) {
											item3 = itemLst2.item(m); 
											if (item3.getNodeType() == Node.ELEMENT_NODE) {
												ielem2 = (Element) item3;
												if(ielem2.getNodeName().equals("ItemType")) {
													itemType.setDescriptionItemType(ielem2.getAttribute("description"));
													itemType.setPictureIDItemType(ielem2.getAttribute("pictureID"));
													itemType.setRepairIDItemType(ielem2.getAttribute("repairID"));	
													itemType.setCleanIDItemType(ielem2.getAttribute("cleanID"));	
													itemType.setIdRoomItem(idRoomItem);
													idItemType = itemType_DAO.addValue(itemType);
												}
												itemLst3 = ielem2.getElementsByTagName("Damage");
												for (int l = 0; l < itemLst3.getLength(); l++) {
													item4 = itemLst3.item(l);
													if (item4.getNodeType() == Node.ELEMENT_NODE) {
														ielem3 = (Element) item4;
														if(ielem3.getNodeName().equals("Damage")) {
															roomDamage.setDescriptionRoom(ielem3.getAttribute("description"));
															roomDamage.setMandatoryRoom(ielem3.getAttribute("throwAlert"));
															roomDamage.setIdItemType(idItemType);
															roomDamage_DAO.addValue(roomDamage);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					itemLst1 = ielem.getElementsByTagName("ItemsPack");
					for (int j = 0; j < itemLst1.getLength(); j++) {
						itemGroupNode = itemLst1.item(j);
						if (itemGroupNode.getNodeType() == Node.ELEMENT_NODE) {
							itemGroupElement = (Element) itemGroupNode;
							if(itemGroupElement.getNodeName().equals("ItemsPack")) {
								itemsPack.setDescription(itemGroupElement.getAttribute("description"));
								itemsPack.setIdRoom(idRoom);
								itemsPack.setIdItemsPack(itemsPack_DAO.addValue(itemsPack));
							}
							
							itemList = itemGroupElement.getElementsByTagName("PackElement");
							for (int k = 0; k < itemList.getLength(); k++) {
								item2 = itemList.item(k);
								if (item2.getNodeType() == Node.ELEMENT_NODE) {
									ielem1 = (Element) item2;
									if(item2.getNodeName().equals("PackElement")) {
										packElement.setItem(ielem1.getAttribute("item"));
										packElement.setType(ielem1.getAttribute("type"));
										packElement.setIdItemPack(itemsPack.getIdItemsPack());
										packElement.setIdItemType(itemType_DAO.getIdItemTypeFromDescription(ielem1.getAttribute("type")));
										packElement_DAO.addValue(packElement);

									}
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
