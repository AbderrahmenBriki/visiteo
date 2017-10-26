package com.appsolute.cel.dragdrop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appsolute.cel.R;

class DynGridViewItemView extends RelativeLayout implements DragSource, DropTarget {       
	public static final int FAVICONID = -5;
	DynGridViewItemData mitem;
	ImageView ivFavorite;
	
	public ImageView getFavoriteView() {
		return ivFavorite;
	}
	
	@SuppressLint("ResourceAsColor")
	public DynGridViewItemView(Context context, DynGridViewItemData item) 
	{
		super( context );
		mitem = item;
		// get/set ID
		setId(item.getItemId());
		
		ImageView ivBack = new ImageView(context);
		//ivBack.setImageResource(item.getBackgroundRes());
		RelativeLayout.LayoutParams lp_ivBack = new RelativeLayout.LayoutParams(mitem.getWidth(), mitem.getHeight());
				//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp_ivBack.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(ivBack, lp_ivBack);
		
		// Configure holder layout
		RelativeLayout panel = new RelativeLayout(context);
		panel.setPadding(mitem.getPadding(), 0, mitem.getPadding(), 0);
		RelativeLayout.LayoutParams lp_PV = new RelativeLayout.LayoutParams(mitem.getWidth(), mitem.getHeight());
				//LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp_PV.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(panel, lp_PV);
				
		// text below image
		TextView textName = new TextView( context );
		textName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		if(item.getPieceFlag())
			textName.setTextColor(ContextCompat.getColor(context, R.color.selector_pieces_green));
		else
			textName.setTextColor(ContextCompat.getColor(context, R.color.selector_piecestext));

		textName.setText( item.getLabel());
		textName.setGravity(Gravity.CENTER);
		if(getResources().getDisplayMetrics().density != 1.0)
			textName.setCompoundDrawablesWithIntrinsicBounds(0, item.getImageRes(), 0, 0);
		else {
			Drawable img;
			img = ContextCompat.getDrawable(context, item.getImageRes());
			img.setBounds(0, 0, img.getMinimumWidth()+30, img.getMinimumHeight()+30);
			textName.setCompoundDrawables(null, img, null, null); 
		}
		RelativeLayout.LayoutParams lp_text = new RelativeLayout.LayoutParams(
				mitem.getWidth() - 2*mitem.getPadding(), LayoutParams.WRAP_CONTENT);
		lp_text.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp_text.addRule(RelativeLayout.CENTER_HORIZONTAL);
		panel.addView(textName, lp_text);
	}
	
	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//this.setMeasuredDimension(mitem.getWidth(), mitem.getHeight());
	}

	/**
     */
    // interface DragTarget implementation: handle what to do when we are the Drag TARGET
	// our item is a target, and is receiving a new drop from somewhere
	// --------------------------------------------------------------------------- //

	/**
	 * the drop is released on our item
	 */
	public void onDrop(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
	}

	public void onDragEnter(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
	}

	/**
	 * Another item is being dragged and hovering on us, switch items animation ?!
	 */
	public void onDragOver(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
		// TODO: switch animation
	}

	public void onDragExit(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
	}
	
	/**
	 * Another item has been dragged on us, accept or reject
	 * By default all gridview items accept any drop
	 */
	public boolean acceptDrop(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo) {
		return true;
	}

	public Rect estimateDropLocation(
			DragSource source,
			int x, int y, int xOffset, int yOffset, DragView dragView,
			Object dragInfo, Rect recycle) {
		return null;
	}

    /**
     */
    // interface DragSource implementation: handle what to do when we are the Drag SOURCE
	// our item is dragged away
	// --------------------------------------------------------------------------- //

	/**
	 * To accept to be dragged. By default all gridview items can be dragged!
	 */
	public boolean allowDrag() {
		//return true;
		return mitem.getAllowDrag();
	}

	/**
	 * Get the drag controller object
	 */
	public void setDragController(DragController dragger) {
	    // Do nothing. We do not need to know the controller object.
	}

	/**
	 * one of our gridviewitems is now a source and is being dragged and released on Target
	 * Target may accept of reject this drag
	 */
	public void onDropCompleted(View target, boolean success) {		
		// Do nothing
	}

	 public boolean onDown(MotionEvent e) {
		 Log.e("bla","ondown");
	  return true;
	 }

	 public boolean onSingleTapUp(MotionEvent e) {
		 Log.e("bla","onSingleTapUp");
	  return true;
	 }

}
