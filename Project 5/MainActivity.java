package edu.birzeit.fall2014.encs539.id1110600.flyingflashcards;

import java.util.Random;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	BoardView boardview;
	TextView tv; // textview for the score
	Button left;
	Button right;
	Button newGame;
	Random random = new Random();
	Thread t;
	int row, r2;
	int col, c2;
	Cell c;
	int score = 0;
	boolean threadnull = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		boardview = (BoardView) findViewById(R.id.board);
		left = (Button) findViewById(R.id.Left);
		right = (Button) findViewById(R.id.Right);
		newGame = (Button) findViewById(R.id.NewGame);
		tv = (TextView) findViewById(R.id.textView1);

		tv.setText("score = " + score);
		c = new Cell();
		getRandEq();
		row = 0;
		col = random.nextInt(5);
		;
		r2 = row;
		c2 = col;
		
		
		t = new GameThread();//create game thread
		t.start();

		right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/**
				 * this button is used to move the card to the right while it
				 * can go to the right
				 * 
				 * **/
				if (col < 4) {
					if (boardview.checkEmpty(row, col, r2, col + 1)) {
						c2 = col + 1;
						if (boardview.check(row, col, r2, c2)) {
							row = r2;
							col = c2;
						}
					}

				}

			}
		});
		left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/**
				 * this button is used to move the card to the left while it can
				 * go to the left
				 * 
				 * **/
				if (col > 0) {
					if (boardview.checkEmpty(row, col, r2, col - 1)) {
						c2 = col - 1;
						if (boardview.check(row, col, r2, c2)) {
							row = r2;
							col = c2;
						}
					}
				}
			}
		});
		newGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * this button is used to start new game it initialise
				 * everything and if the game was lost so the thread was
				 * returned then will create new thread
				 * 
				 * **/
				boardview.isLost = false;
				boardview.init();
				score = 0;
				boardview.paint.setColor(Color.BLACK);
				tv.invalidate();
				boardview.invalidate();

				c = new Cell();
				getRandEq();
				row = 0;
				col = random.nextInt(5);
				r2 = row;
				c2 = col;

				if (threadnull) {
					t = new GameThread();
					t.start();
				}

				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Generate the equation randomly, the equation contain only 2 values and
	 * there is only 2 operation addition and subtraction, each value is from
	 * the range [0 to 9] the results will be in the range of [-18 to 18]
	 * 
	 * after generating the equation randomly calculate the result based on the
	 * operation the result of the equation is calculated
	 * 
	 * **/
	public void getRandEq() {
		char op = ' ';
		int res = 0;

		int x = random.nextInt(10);
		int y = random.nextInt(10);

		if (random.nextInt(2) == 1) {
			op = '+';
			res = x + y;
		} else {
			op = '-';
			res = x - y;
		}
		c.s = x + " " + op + " " + y;
		c.result = res;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class GameThread extends Thread implements Runnable {
		@Override
		public void run() {

			while (true) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						boardview.invalidate();
						tv.invalidate();
					}
				});
				boardview.setPosition(c, row, col);
				if (boardview.check(row, col, r2, c2)) {
					row = r2;
					col = c2;
				}
				try {
					sleep(500);// this is the speed of moving the card to the
								// bottom for 1 row
					if (!boardview.checkFlip(row, col, r2 + 1, c2) && r2 < 9) {
						/**
						 * check if we should flip the card or the card should
						 * be move down to the next row
						 * 
						 * */
						r2 = row + 1;
					} else {
						/**
						 * here we will flip the card so will change the
						 * equation with its result and then we will create new
						 * card to play with
						 * 
						 * **/
						c.s = c.result + "";
						boardview.setPosition(c, row, col);

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								boardview.invalidate();
								tv.invalidate();
							}
						});

						if (boardview.checkResults(row, col)) {
							/**
							 * if the two results are the same we will increase
							 * the score and the cards will be removed in the
							 * checkResults method
							 * 
							 * **/
							score++;
						}
						/**
						 * create new cell with new equation
						 * 
						 * **/
						c = new Cell();
						getRandEq();
						row = 0;
						col = random.nextInt(5);
						r2 = row;
						c2 = col;
						if (boardview.checkGameEnd(row, col)) {
							/**
							 * check if the game end to stop the thread and the
							 * game
							 * 
							 * **/
							boardview.isLost = true;
							threadnull = true;
							return;
						}
						tv.setText("score = " + score);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}