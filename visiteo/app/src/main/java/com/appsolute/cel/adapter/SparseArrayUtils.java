package com.appsolute.cel.adapter;

import java.util.List;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.appsolute.cel.models.ItemType;
import com.appsolute.cel.models.RoomItem;

public class SparseArrayUtils {
	
	/**
	 * 
	 * This method reorder a SparseIntArray
	 * 
	 * @param sparseArray
	 * @return
	 */
	public static SparseIntArray reorderSparseIntArray (SparseIntArray sparseArray) {

		int key = 0;
		int size = sparseArray.size();
		SparseIntArray sparseArrayReordered = new SparseIntArray();
		for(int i = 0; i < size; i++) {
			if(sparseArray.valueAt(i) != 0)
				sparseArrayReordered.put(key, sparseArray.valueAt(i));
			key++;
		}
		return sparseArrayReordered;		
	}

	/**
	 * 
	 * This method reorder a SparseArray<String>
	 * 
	 * @param sparseArray<String>
	 * 
	 * @return ordered sparseArray<String>
	 */
	public static SparseArray<String> reorderSparseArray (SparseArray<String> sparseArray) {

		int key = 0;
		int size = sparseArray.size();
		SparseArray<String> sparseArrayReordered = new SparseArray<String>();
		for(int i = 0; i < size; i++) {
			if(sparseArray.valueAt(i) != null)
				sparseArrayReordered.put(key, sparseArray.valueAt(i));
			key++;
		}
		return sparseArrayReordered;	
	}

	/**
	 * 
	 * This method reorder a SparseArray<Boolean>
	 * 
	 * @param sparseArray<Boolean>
	 * 
	 * @return ordered sparseArray<Boolean>
	 */
	public static SparseArray<Boolean> reorderBooleanSparseArray (SparseArray<Boolean> sparseArray) {

		int key = 0;
		int size = sparseArray.size();
		SparseArray<Boolean> sparseArrayReordered = new SparseArray<Boolean>();
		for(int i = 0; i < size; i++) {
			if(sparseArray.valueAt(i) != null)
				sparseArrayReordered.put(key, sparseArray.valueAt(i));
			key++;
		}
		return sparseArrayReordered;		
	}


	/**
	 * 
	 * This method reorder a SparseArray<RoomItem>
	 * 
	 * @param sparseArray<RoomItem>
	 * 
	 * @return ordered sparseArray<RoomItem>
	 */
	public static SparseArray<RoomItem> reorderRoomItemSparseArray (SparseArray<RoomItem> sparseArray) {

		int key = 0;
		int size = sparseArray.size();
		SparseArray<RoomItem> sparseArrayReordered = new SparseArray<RoomItem>();
		for(int i = 0; i < size; i++) {
			if(sparseArray.valueAt(i) != null)
				sparseArrayReordered.put(key, sparseArray.valueAt(i));
			key++;

		}
		return sparseArrayReordered;		
	}


	/**
	 * 
	 * This method reorder a SparseArray<Bitmap>
	 * 
	 * @param sparseArray<Bitmap>
	 * 
	 * @return ordered sparseArray<Bitmap>
	 */
	public static SparseArray<Bitmap> reorderBitmapSparseArray (SparseArray<Bitmap> sparseArray) {

		int key = 0;
		int size = sparseArray.size();
		SparseArray<Bitmap> sparseArrayReordered = new SparseArray<Bitmap>();
		for(int i = 0; i < size; i++) {
			if(sparseArray.valueAt(i) != null)
				sparseArrayReordered.put(key, sparseArray.valueAt(i));
			key++;

		}
		return sparseArrayReordered;		
	}


	/**
	 * This method reorder a SparseArray<List<String>>
	 * 
	 * @param sparseArray<List<String>>
	 * 
	 * @return ordered sparseArray<List<String>>
	 */
	public static SparseArray<List<String>> reorderSparseArrayList (SparseArray<List<String>> sparseArrayList) {

		int key = 0;
		int size = sparseArrayList.size();
		SparseArray<List<String>> sparseArrayReordered = new SparseArray<List<String>>();
		for(int i = 0; i < size; i++) {
			if(sparseArrayList.valueAt(i) != null)
				sparseArrayReordered.put(key, sparseArrayList.valueAt(i));
			key++;

		}
		return sparseArrayReordered;		
	}

	/**
	 * This method reorder a SparseArray<List<ItemType>>
	 * 
	 * @param sparseArray<List<ItemType>>
	 * 
	 * @return ordered sparseArray<List<ItemType>>
	 */
	public static SparseArray<List<ItemType>> reorderSparseArrayListItemType (SparseArray<List<ItemType>> sparseArrayList) {

		int key = 0;
		int size = sparseArrayList.size();
		SparseArray<List<ItemType>> sparseArrayReordered = new SparseArray<List<ItemType>>();
		for(int i = 0; i < size; i++) {
			if(sparseArrayList.valueAt(i) != null)
				sparseArrayReordered.put(key, sparseArrayList.valueAt(i));
			key++;

		}
		return sparseArrayReordered;		
	}

}
