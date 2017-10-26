package com.appsolute.cel.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

public class ExpandableCustomGridView extends GridView {
    boolean expanded = false;

    public ExpandableCustomGridView(Context context) {
        super(context);
    }

    public ExpandableCustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableCustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	try {
	        if (isExpanded()) {
	            // Calculate entire height by providing a very large height hint.
	            // View.MEASURED_SIZE_MASK represents the largest height possible.
	            int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
	            super.onMeasure(widthMeasureSpec, expandSpec);
	
	            ViewGroup.LayoutParams params = getLayoutParams();
	            params.height = getMeasuredHeight();
	        } else {
	            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}