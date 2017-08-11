package edu.birzeit.fall2014.encs539.id1110600.hungrycat;

import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class PacCat extends ActionBarActivity {

    BoardView boardView;
    Cat cat;
    Dog dog1,dog2,dog3;
    Button left;
    Button up;
    Button down;
    Button right;
    Random random = new Random();
    static Thread tc,t1,t2,t3;//Mohammad Kabajah
    /** 
     * Mohammad Kabajah
     * This int count is the counter for the number of mice was collected
     * used to know when you finsh the game
     * updated in the cat class in the partiallyUpdatePosition method
     * **/
    static int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_cat);
        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);
        up = (Button)findViewById(R.id.upButton);
        down = (Button)findViewById(R.id.down);
        boardView = (BoardView)findViewById(R.id.board);
        cat = new Cat(boardView);
      //Mohammad Kabajah
    	dog1 = new Dog(boardView);
        dog2 = new Dog(boardView);
        dog3 = new Dog(boardView);
        boardView.walls(200);
        boardView.mice(getResources().getDrawable(R.drawable.mouse));
        boardView.cat(cat,getResources().getDrawable(R.drawable.cat)); 
        //Mohammad Kabajah
        boardView.dog(dog1,getResources().getDrawable(R.drawable.dog));
        boardView.dog(dog2,getResources().getDrawable(R.drawable.dog));
        boardView.dog(dog3,getResources().getDrawable(R.drawable.dog));
        //cat
        tc =new Thread(){
            @Override 
            public void  run(){
                // run forever
                while (true) {
                    //cat.updatePosition();
                	cat.partiallyUpdatePosition();//Mohammad Kabbajah
                	runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            boardView.invalidate();
                        }
                    });
                	 /**
                     * Mohammad Kabajah
                     *  check if the cat and one of the dogs in the same room
                     *  is in  the same room it will end the game will lose
                     *  if you collect all the mice in the game
                     *  collect 100 mice you will win and the game will end 
                     *  **/
                	 if((boardView.rooms[cat.row][cat.col]&BoardView.dogHere)!=0){
                     	PacCat.endGame();
                		boardView.isLost=true;          	
                     	return;
                     }
                	if(count == 100){
                    	PacCat.endGame();
                		boardView.isWon=true;
                    	return;
                    }
                	
                	 runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             boardView.invalidate();
                         }
                     });
                    try {
                        sleep(500); // redraw 20 frames / sec
                    } catch (Exception e){e.printStackTrace();} // log exception and quit
                }
            }

        };
        tc.start();
        /**
         * Mohammad Kabajah
         *  This thread is the dog thread
         *  each dog have its thread 
         *  **/
        //dog1
        t1 = new Thread(){
        	@Override
        	public void run(){
        		
        	
                
        		while(true){
        			if(tc==null){
            			return;
            		}
        			dog1.partiallyUpdatePosition();
        			 runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             boardView.invalidate();
                         }
                     });
                     try {      
                    	 sleep(500);
                    	 if(dog1.direction==Drifter.STUCK)
                    		 dog1.direction=random.nextInt(4);
                    	 else{
                    		 int r = dog1.row;
                    		 int c = dog1.col;
                    		 if(dog1.direction==0||dog1.direction==2)
                    			 if(((boardView.rooms[r][c])&2)==0 ||((boardView.rooms[r][c])&8)==0 )
                            		 dog1.direction=random.nextInt(4);
                    		 if(dog1.direction==1||dog1.direction==3)
                    			 if(((boardView.rooms[r][c])&1)==0 ||((boardView.rooms[r][c])&4)==0 )
                            		 dog1.direction=random.nextInt(4);
                    	 }
                     } catch (Exception e){e.printStackTrace();} // log exception and quit
        		}
        	}
        };
        /**
         * Mohammad Kabajah
         *  This thread is the dog thread
         *  each dog have its thread 
         *  **/
        //dog2
        t2 = new Thread(){
        	@Override
        	public void run(){

        		while(true){
        			if(tc==null){
            			return;
            		}
        			dog2.partiallyUpdatePosition();
        			 runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             boardView.invalidate();
                         }
                     });
                     try {
                    	 if(dog2.direction==Drifter.STUCK)
                    		 dog2.direction=random.nextInt(4);
                    	 else{
                    		 int r = dog2.row;
                    		 int c = dog2.col;
                    		 if(dog2.direction==0||dog2.direction==2)
                    			 if(((boardView.rooms[r][c])&2)==0 ||((boardView.rooms[r][c])&8)==0 )
                            		 dog2.direction=random.nextInt(4);
                    		 if(dog2.direction==1||dog2.direction==3)
                    			 if(((boardView.rooms[r][c])&1)==0 ||((boardView.rooms[r][c])&4)==0 )
                            		 dog2.direction=random.nextInt(4);
                    	 }
                         sleep(500);
                     } catch (Exception e){e.printStackTrace();} // log exception and quit
        		}
        	}
        };
        /**
         * Mohammad Kabajah
         *  This thread is the dog thread
         *  each dog have its thread 
         *  **/
        //dog3
        t3 = new Thread(){
        	@Override
        	public void run(){

        		while(true){
        			if(tc==null){
            			return;
            		}
        			dog3.partiallyUpdatePosition();
        			 runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             boardView.invalidate();
                         }
                     });
                     try {
                    	 if(dog3.direction==Drifter.STUCK)
                    		 dog3.direction=random.nextInt(4);
                    	 else{
                    		 int r = dog3.row;
                    		 int c = dog3.col;
                    		 if(dog3.direction==0||dog3.direction==2)
                    			 if(((boardView.rooms[r][c])&2)==0 ||((boardView.rooms[r][c])&8)==0 )
                            		 dog3.direction=random.nextInt(4);
                    		 if(dog3.direction==1||dog3.direction==3)
                    			 if(((boardView.rooms[r][c])&1)==0 ||((boardView.rooms[r][c])&4)==0 )
                            		 dog3.direction=random.nextInt(4);
                    	 }
                         sleep(500);
                     } catch (Exception e){e.printStackTrace();} // log exception and quit
        		}
        	}
        };
        
        /**
         * Mohammad Kabajah
         *  starting the threads with 250 millisecond between each one 
         *  **/
        try {
			Thread.sleep(250);
			t1.start();
			Thread.sleep(250);
			t2.start();
			Thread.sleep(250);
			t3.start();
			Thread.sleep(250);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat.direction = Drifter.EAST;
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat.direction = Drifter.WEST;
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat.direction = Drifter.NORTH;
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat.direction = Drifter.SOUTH;
            }
        });
    }

    /**
     * Mohammad Kabajah
     * this method is used to stop the threads from working
     * i tried the thread.stop() method but it cant be used it crashes the app
     * so using this we interrupt the thread then set it to null
     * so there will be no thread to run
     * **/
	protected static void endGame() {
    	try{
    		tc.interrupt();
    		tc=null;
    		t1.interrupt();
    		t2.interrupt();
    		t3.interrupt();
    		t1=null;
    		t2=null;
    		t3=null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pac_cat, menu);
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
