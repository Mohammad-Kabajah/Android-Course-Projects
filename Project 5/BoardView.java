package edu.birzeit.fall2014.encs539.id1110600.flyingflashcards;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {
	public BoardView(Context context) {
		super(context);
		init();
	}

	public BoardView(Context context, AttributeSet as) {
		super(context, as);
		init();
	}

	public BoardView(Context context, AttributeSet as, int t) {
		super(context, as, t);
		init();
	}

	Paint paint = new Paint();
	final static int rows = 10;
	final static int columns = 5;
	Cell[][] cells = new Cell[rows][columns];
	Rect rect = new Rect();
	Rect rect2 = new Rect();
	boolean isLost = false;

	@Override
	public void onDraw(Canvas canvas) {
		int h = getHeight();
		int w = getWidth();
		float dcell_hor = (float) w / columns;
		float dcell_ver = (float) h / rows;

		canvas.drawLine(0, h, w, h, paint);
		canvas.drawLine(0, 1, w, 1, paint);
		canvas.drawLine(w - 1, 0, w - 1, h, paint);
		canvas.drawLine(1, 0, 1, h, paint);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (!cells[i][j].s.equals("")) {
					rect.top = (int) (i * dcell_ver);
					rect.left = (int) (j * dcell_hor);
					rect.right = rect.left + (int) dcell_hor;
					rect.bottom = rect.top + (int) dcell_ver;
					canvas.drawRect(rect, cells[i][j].paint);
					int size = R.dimen.textSize;
					String text = cells[i][j].s;
					paint.setTextSize(getResources()
							.getDimensionPixelSize(size));
					cells[i][j].paint.getTextBounds(text, 0, text.length(),
							rect);
					canvas.drawText(cells[i][j].s, (rect.right - rect.width())
							/ 2 + (int) (dcell_hor * (j + .25)),
							(rect.top - rect.bottom) / 2
									+ (int) (dcell_ver * (i + .75)), paint);

				}
			}
		}
		if (isLost) {
			int size = R.dimen.textSize2;

			String text = "You Lose";
			paint.setTextSize(getResources().getDimensionPixelSize(size));
			paint.getTextBounds(text, 0, text.length(), rect2);
			paint.setColor(Color.RED);
			canvas.drawText("You lose", (w - rect2.width()) / 2, h / 2, paint);
		}

	}

	/**
	 * set the position of the cell with the indexes provided
	 * 
	 * **/
	public void setPosition(Cell c, int ro, int co) {
		cells[ro][co] = c;
	}

	/**
	 * initialise the array that will hold all the cards
	 * 
	 * **/
	public void init() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new Cell();
			}
		}
	}

	/**
	 * check if the second cell with the indexes r2 and c2 is available and set
	 * the cell in it and remove it from the old cell
	 * 
	 * **/
	public boolean check(int r1, int c1, int r2, int c2) {
		boolean flag = false;

		if (cells[r2][c2].s.equals("")) {
			cells[r2][c2].s = cells[r1][c1].s + "";
			cells[r2][c2].paint = cells[r1][c1].paint;
			cells[r1][c1] = new Cell();
			flag = true;
		}
		return flag;
	}

	/**
	 * check if the next position of the cell is available or not so we will
	 * flip the card or not
	 * 
	 * **/
	public boolean checkFlip(int r1, int c1, int r2, int c2) {
		boolean flag = false;

		try {
			if (!cells[r2][c2].s.equals("")) {
				flag = true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return flag;
	}

	/**
	 * check to see if the column we want to move to in this row is free or not
	 * so we can move it across or skip the cards if there is a card in the same
	 * row in the next column so the card will not overlap
	 * 
	 * if there is in the middle column too many cards and the card on the left
	 * or the right side it cant move to the other side if there is some card in
	 * the middle of them
	 * 
	 * **/
	public boolean checkEmpty(int r1, int c1, int r2, int c2) {
		boolean flag = false;
		if (cells[r2][c2].s.equals("")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * check if the result of the two cards is the same then we will remove
	 * these two cards
	 * 
	 * **/
	public boolean checkResults(int ro, int co) {
		boolean flag = false;
		if (ro <= 8) {
			int s1 = cells[ro][co].result;
			int s2 = cells[ro + 1][co].result;
			if (s1 == s2) {
				cells[ro][co] = new Cell();
				cells[ro + 1][co] = new Cell();
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * check if the cell in the first row and if the column is full so there is
	 * no available space for this cell to be in this column and return true to
	 * end the game
	 * 
	 * **/
	public boolean checkGameEnd(int ro, int co) {
		boolean flag = false;
		if (!cells[ro][co].s.equals("") && ro == 0)
			flag = true;
		return flag;
	}

}