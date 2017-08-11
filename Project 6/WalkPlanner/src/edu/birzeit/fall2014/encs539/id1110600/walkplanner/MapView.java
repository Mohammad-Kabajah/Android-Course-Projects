package edu.birzeit.fall2014.encs539.id1110600.walkplanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * This class is used to draw the map on the screen zoom in and out from the map
 * **/

public class MapView extends View {

	Bitmap bit = null;// The object that will hold the map
	Paint paint = new Paint();
	Paint paint2 = new Paint();
	Rect source = null; // this will be the portion of the map that will be
						// drawn
	Rect rect = new Rect();
	Rect destination = null; // this will be the object where to draw the map
								// and will
	// be always the same
	int realX = 0; // the real dimension of the map
	int realY = 0; // the real dimensions if the map
	int maximumWidth = 0;
	int maximumHeight = 0;
	int cutWidth = 0; // the dimensions of the portion that will be displayed on
						// the screen from the map
	int cutHeight = 0; // the dimensions of the portion that will be displayed
						// on the screen from the map

	public MapView(Context context) {
		super(context);
	}

	public MapView(Context context, AttributeSet as) {
		super(context, as);
	}

	public MapView(Context context, AttributeSet as, int t) {
		super(context, as, t);
	}

	/**
	 * OnDraw method is used to print the map on the screen it first check if
	 * the bitmap file is set and not null while it is loading the application
	 * the bitmap file will be set if the the view is ready and if it is not
	 * ready then its dimensions will be 0,0 and then if we try to invalidate it
	 * will not draw anything we keep waiting until it will be set
	 * 
	 * this happened when the application is launched at first the map view is
	 * not ready yet so the dimensions is 0,0 so we have to wait to the map view
	 * to take its size so then we can draw in it
	 * **/
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (bit != null) {
			canvas.drawBitmap(bit, source, destination, paint);
			canvas.drawLine(1, 1, 1, getHeight() - 1, paint);
			canvas.drawLine(1, 1, getWidth() - 1, 1, paint);
			canvas.drawLine(1, getHeight() - 1, getWidth() - 1,
					getHeight() - 1, paint);
			canvas.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 1,
					paint);
		    int h = getHeight();
	        int w = getWidth();
        	int size= R.dimen.textSize;
        	   
        	String text = "Data provided by OpenStreetMap.org";
        	paint.setTextSize(getResources().getDimensionPixelSize(size));
        	paint.getTextBounds(text, 0, text.length(), rect);
        	paint.setColor(Color.BLACK);
        	canvas.drawText("Data provided by OpenStreetMap.org", w-rect.width()-getResources().getDimensionPixelSize(size) , h-getResources().getDimensionPixelSize(size), paint);
		}
	}

	/**
	 * adjust the location of the map according to the moving gestures or the
	 * swipe that the user do to view the map
	 * **/
	public void adjust(int deltaX, int deltaY) {
		if (realX >= 0 && realX <= maximumWidth)
			realX += deltaX;
		if (realX < 0)
			realX = 0;
		if (realX > maximumWidth)
			realX = maximumWidth;

		if (realY >= 0 && realY <= maximumHeight)
			realY += deltaY;
		if (realY > maximumHeight)
			realY = maximumHeight;
		if (realY < 0)
			realY = 0;
		reDraw();

	}

	/**
	 * this method is used to check on the parameters before drawing the map and
	 * if some of them is out of the boundaries to correct them to stay in the
	 * boarders of the map dimensions and then invalidate the view
	 * **/
	public void reDraw() {
		if (realX < 0)
			realX = 0;
		if (realY < 0)
			realY = 0;
		source.left = realX;
		source.top = realY;
		source.right = realX + cutWidth;
		source.bottom = realY + cutHeight;
		invalidate();
	}

	/**
	 * this method is used to set the bitmap file of the map
	 * **/
	public void setMap(Bitmap bitmap) {
		bit = bitmap;
		paint.setStrokeWidth(2);
		cutWidth = bit.getWidth() / 6; // used to get the width of the portion
										// we want to draw on screen with the
										// ration 1.5 which is the same ration
										// as the original file
		cutHeight = bit.getHeight() / 4; // used to get the height of the
											// portion we want to draw on screen
											// with the ration 1.5 which is the
											// same ration as the original file
	}

	/**
	 * this method is used to set the portion of the map that we want to draw on
	 * the screen and the destination is always the same which is the size of
	 * the map view
	 * **/
	public void setRect() {
		destination = new Rect(0, 0, getWidth(), getHeight());
		source = new Rect(0, 0, cutWidth, cutHeight);
		maximumWidth = bit.getWidth() - cutWidth;
		maximumHeight = bit.getHeight() - cutHeight;
	}

	/**
	 * this method is used to zoom in the map by decreasing the dimensions of
	 * the source portion of the map and keeping the destination is the same
	 * size as the view so it will give the effect of zoom in
	 * **/
	public void zoomIn() {
		int oldX = realX + cutWidth / 2;
		int oldY = realY + cutHeight / 2;
		cutWidth = cutWidth / 2;
		cutHeight = cutHeight / 2;
		int newX = realX + cutWidth / 2;
		if (cutWidth < bit.getWidth() / 12) {
			cutWidth = bit.getWidth() / 12;
			cutHeight = bit.getHeight() / 8;
			newX = cutWidth / 2;
		}

		if (newX != oldX) {
			realX = oldX - cutWidth / 2;
			realY = oldY - cutHeight / 2;
		}

		maximumWidth = bit.getWidth() - cutWidth;
		maximumHeight = bit.getHeight() - cutHeight;
		reDraw();
	}

	
	/**
	 * this method is used to zoom out the map by increasing the dimensions of
	 * the source portion of the map and keeping the destination is the same
	 * size as the view so it will give the effect of zoom out
	 * 
	 * **/
	public void zoomOut() {
		int oldX = realX + cutWidth / 2;
		int oldY = realY + cutHeight / 2;

		int oldcutWidth = cutWidth;
		int oldcutHeight = cutHeight;

		cutWidth = (int) (cutWidth * 1.5);
		cutHeight = (int) (cutHeight * 1.5);
		int newX = realX + cutWidth / 2;

		if (cutWidth > bit.getWidth() / 1.5) {
			cutWidth = oldcutWidth;
			cutHeight = oldcutHeight;
			newX = cutWidth / 2;
		}

		if (newX != oldX) {
			realX = oldX - cutWidth / 2;
			realY = oldY - cutHeight / 2;
		}

		maximumWidth = bit.getWidth() - cutWidth;
		maximumHeight = bit.getHeight() - cutHeight;
		reDraw();
	}

}
