package edu.birzeit.fall2014.encs539.id1110600.flyingflashcards;

import java.util.Random;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {

	/**
	 * each cell contain a String for the equation a paint object for the colour
	 * of the card and a result for the equation
	 * 
	 * **/
	String s = "";
	Paint paint = new Paint();
	int result;

	/**
	 * create each cell and generate the colour of the card randomly the +128 in
	 * int b = rand.nextInt(128) + 128; is added in case all the randomly
	 * generated numbers is 0 so the card will be black and we can't read the
	 * equation inside
	 * 
	 * **/
	public Cell() {
		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(128) + 128;
		int color = Color.rgb(r, g, b);
		paint.setColor(color);
	}
}