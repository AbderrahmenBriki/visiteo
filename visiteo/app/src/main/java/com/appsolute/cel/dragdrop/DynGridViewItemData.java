package com.appsolute.cel.dragdrop;

public class DynGridViewItemData {
		   
	    private String m_szLabel;
	    private int m_nWidth, m_nHeight, m_nPadding;
	    private int m_nImgRes, m_nItemId;
	    private boolean m_bAllowDrag = true;
	    private boolean m_pieceFlag = false;
	    
	    public DynGridViewItemData( String label, int w, int h, int padding, boolean b_favorite_state, boolean b_show_fav,
	    		int itemimg, int id, boolean pieceFlag) {
	    	m_nWidth = w; 
	    	m_nHeight = h;
	    	m_nPadding = padding;
	        m_szLabel = label;
	        m_nImgRes = itemimg; 
	        m_nItemId = id; 
	        m_pieceFlag = pieceFlag;
	      }
	    
	    public boolean getPieceFlag() {
	    	return m_pieceFlag;
	    }
	    public int getWidth() {
	    	return m_nWidth;
	    }
	    public int getHeight() {
	    	return m_nHeight;
	    }
	    public int getPadding() {
	    	return m_nPadding;
	    }
	    public String getLabel() { return m_szLabel; }
	    public void setLabel(String p) { m_szLabel = p;}	    	    
	    
	    public int getImageRes() { return m_nImgRes; }
	    public void setImageRes(int p) { m_nImgRes = p;}
	    
	    
	    public int getItemId() { return m_nItemId; }
	    public void setItemId(int p) { m_nItemId = p;}
	    
	    public boolean getAllowDrag() { return m_bAllowDrag; }
	    public void setAllowDrag(boolean p) { m_bAllowDrag = p;}

}