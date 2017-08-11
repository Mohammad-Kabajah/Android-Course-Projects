package edu.birzeit.fall2014.encs539.id1110600.walkplanner;

import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	Button refresh; // the refresh button
	TextView MACtv, Locationtv; // the textview of the MAC and location here i
								// might say MAC but i mean the unique BSSID
	WifiManager wm;
	MapView mapView;
	String noBSSID = "00:00:00:00:00:00";
	private float downX, downY, upX, upY;
	Button zoomIn;
	Button zoomOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		refresh = (Button) findViewById(R.id.refresh);
		zoomIn = (Button) findViewById(R.id.ZoomIN);
		zoomOut = (Button) findViewById(R.id.ZoomOUT);
		MACtv = (TextView) findViewById(R.id.MACtextView);
		Locationtv = (TextView) findViewById(R.id.LOCATIONtextView);
		mapView = (MapView) findViewById(R.id.board);

		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * this button will check the wifi network you are connected to
				 * and get the BSSID of the router and print it on the screen
				 * and then get the location of that router by its MACc address
				 * and print it on the screen
				 * **/
				wm = (WifiManager) getSystemService(WIFI_SERVICE);
				WifiInfo temp = wm.getConnectionInfo();

				if (temp.getBSSID() == null || noBSSID.equals(temp.getBSSID())) {
					MACtv.setText("No Wifi connection");
				} else {
					MACtv.setText(temp.getBSSID().toLowerCase());
				}
				String loc = Nodes.getLocation(temp.getBSSID());
				if (loc != null) {
					Locationtv.setText(loc);
				} else {
					Locationtv.setText("UNKNOWN Location");
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						MACtv.invalidate();
						Locationtv.invalidate();
					}
				});
			}
		});

		zoomIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/**
				 * call the zoom in method in the map view class to do the
				 * effect of zooming in
				 * **/
				mapView.zoomIn();
			}
		});

		zoomOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/**
				 * call the zoom out method in the map view class to do the
				 * effect of zooming out
				 * **/
				mapView.zoomOut();
			}
		});

		/**
		 * Creating this anonymous thread to do simple job the it will end this
		 * thread keep checking the dimensions of the map view if it is not
		 * created correctly yet it keep waiting until the map view is ready
		 * then it will try to open the map image from the Assets directory,
		 * decode the image to bitmap to send it to the map view to set the
		 * bitmap image that we will use to draw on the screen and to set the
		 * source and destination rect objects that will be used to draw the map
		 * after this thread complete this job and the map view is now ready and
		 * the map is drawn on it the thread will stop/quit/terminated
		 * **/
		new Thread() {
			@Override
			public void run() {
				while (mapView.getWidth() == 0 || mapView.getHeight() == 0)
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				try {
					InputStream bitmap = getAssets().open("map.png");
					Bitmap bit = BitmapFactory.decodeStream(bitmap);
					mapView.setMap(bit);
					mapView.setRect();
					// refresh.callOnClick(); //cannot be used in API level less
					// than 15

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mapView.invalidate();
					}
				});
			}
		}.start();

		mapView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				/**
				 * here we are doing the swipe gestures, we get the location of
				 * where the swipe start and the location of where the swipe
				 * ended and calculate the difference between the X and Y
				 * coordinates and send it to the adjust method to adjust the
				 * map in the map view, in other words to change the portion of
				 * what we see from the map
				 * **/
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					downX = event.getX();
					downY = event.getY();
					return true;
				}
				case MotionEvent.ACTION_UP: {
					upX = event.getX();
					upY = event.getY();

					float deltaX = downX - upX;
					float deltaY = downY - upY;

					mapView.adjust((int) deltaX, (int) deltaY);
					return true;
				}
				}
				return false;
			}
		});
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
}
