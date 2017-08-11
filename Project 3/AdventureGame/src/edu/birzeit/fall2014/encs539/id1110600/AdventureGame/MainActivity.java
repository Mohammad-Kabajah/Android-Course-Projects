package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;

import java.io.InputStream;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	
	TextView text;

	EditText edittxt;
	String str="";
	Adventure a;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.textView1);
        Button b = (Button)findViewById(R.id.button1);
        edittxt = (EditText)findViewById(R.id.editText1);
        text.setText("");
        InputStream input;
         a = new Adventure();
        try{
        input = this.getAssets().open("adv.dat");
        a.setup(input);
        String s = a.ready();
        text.append(s);
        
        }catch(Exception e){
        	
        }

        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s = "";
				String[] line = (""+edittxt.getText()).split(" ");
				
				Word w;
				Word y = null;
				


					if (line.length == 0 || line.length > 2) {
						s = Adventure.speak(13);
						text.append(s);
					}
					
					w = Word.getWordByName(line[0]);
					if (w == null) {
						s = Adventure.speak(60);
						text.append(s);
					}
					if (line.length == 2){
					y =  Word.getWordByName(line[1]);
					if (y == null) {
						s = Adventure.speak(60);
						text.append(s);;
					}
					}
					 Command  cmd;
		                if (v!=null&&w!=null){
		                    cmd = new Command(w, y);
		                }
		                else if (w!=null && v==null)
		                    cmd = new Command(w);
		                else 
		                    cmd= null;
		                
		                
					
					s=a.play(cmd);
					text.setText(s);
					edittxt.setText("");
				
				
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
