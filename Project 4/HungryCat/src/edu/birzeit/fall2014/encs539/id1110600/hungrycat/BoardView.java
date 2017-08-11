package edu.birzeit.fall2014.encs539.id1110600.hungrycat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by staylor on 9/29/14.
 * This code maintains and displays a pacman grid of ten by ten
 */
public class BoardView extends View {
    public BoardView(Context context){
        super(context);
        init();
    }
    public BoardView(Context context, AttributeSet as){
        super(context, as);
        init();
    }
    public BoardView(Context context, AttributeSet as, int t){
        super(context, as, t);
        init();
    }

    Paint paint = new Paint();
    final static int ten = 10;
    byte [][] rooms = new byte[ten][ten];
    Bitmap mouse;
    Bitmap cat;
    Bitmap dog;
    Rect rect = new Rect();
    Rect rect2 = new Rect();//Mohammad Kabajah
    boolean isLost = false;//Mohammad Kabajah
    boolean isWon = false;//MOhammad Kabajah

    final static byte westWall = 1;
    final static byte northWall= 2;
    final static byte eastWall = 4;
    final static byte southWall= 8;
    final static byte mouseHere = 16;
    final static byte catHere = 32;
    final static byte dogHere = 64;	//Mohammad Kabajah
    
    @Override
    public void onDraw(Canvas canvas){
        int h = getHeight();
        int w = getWidth();
        int d = Math.min(h, w);
        float dcell = (float)d/ten;
        for (int r=0; r<ten; r++)
            for (int c=0; c<ten; c++){
                byte room = rooms[r][c];
                float ulx = dcell*c;
                float uly = dcell*r;
                if ((room & westWall) !=0) canvas.drawLine(ulx,uly,ulx,uly+dcell,paint);
                if ((room & northWall) !=0) canvas.drawLine(ulx,uly,ulx+dcell,uly,paint);
                if ((room & eastWall) !=0) canvas.drawLine(ulx+dcell,uly,ulx+dcell,uly+dcell,paint);
                if ((room & southWall) !=0) canvas.drawLine(ulx,uly+dcell,ulx+dcell,uly+dcell,paint);

                if (0!=(room & mouseHere)){
                    rect.top = (int)uly;
                    rect.left = (int)ulx;
                    rect.right = rect.left +(int) dcell;
                    rect.bottom = rect.top + (int)dcell;
                    canvas.drawBitmap(mouse, null, rect, paint);
                }
                if (0!=(room & catHere)){
                    rect.top = (int)uly;
                    rect.left = (int)ulx;
                    rect.right = rect.left +(int) dcell;
                    rect.bottom = rect.top + (int)dcell;
                    canvas.drawBitmap(cat, null, rect, paint);

                }
                if (0!=(room & dogHere)){//Mohammad Kabajah
                    rect.top = (int)uly;
                    rect.left = (int)ulx;
                    rect.right = rect.left +(int) dcell;
                    rect.bottom = rect.top + (int)dcell;
                    canvas.drawBitmap(dog, null, rect, paint);
                }
            }
        /**
         * Mohammad Kabajah
         * this is used to check the flag isLost if it is true 
         * so that you lose the game and the game will be stoped
         * and here will show on the screen you lose
         * 
         * credit to Ala Hashesh i asked him how to do the drawtext in the middle of the maze
         * 
         * **/
        if(isLost){
        	int size= R.dimen.textSize;
   
        	String text = "You lose";
        	paint.setTextSize(getResources().getDimensionPixelSize(size));
        	paint.getTextBounds(text, 0, text.length(), rect2);
        	paint.setColor(Color.BLACK);
        	canvas.drawText("You lose", (d-rect2.width())/2, d/2, paint);
        }
        /**
         * Mohammad Kabajah
         * this is used to check the flag isWon if it is true 
         * so that you win the game and the game will be stoped
         * and here will show on the screen you win
         * 
         *  credit to Ala Hashesh i asked him how to do the drawtext in the middle of the maze
         *  
         * **/
        if(isWon){
        	int size= R.dimen.textSize;
        	String text = "You win";
        	paint.setTextSize(getResources().getDimensionPixelSize(size));
        	paint.getTextBounds(text, 0, text.length(), rect2);
        	
        	paint.setColor(Color.BLACK);
        	canvas.drawText("You win", (d-rect2.width())/2, d/2, paint);
        	
        }
    }

    public void init() {
        for (int i = 0; i < ten; i++) {
            rooms[0][i] |= northWall;
            rooms[ten - 1][i] |= southWall;
            rooms[i][0] |= westWall;
            rooms[i][ten - 1] |= eastWall;
        }
    }

    /**
     * This method initializes the interior walls.  A 10 x 10 grid seems to take about
     * five seconds, with a parameter of 200.  It takes so long because of the floodTest()
     * which attempts to determine whether the thing is still connected.  If I built
     * symmetrical mazes, I could reduce the number of tests per random insertion.
     * @param howMany tells how many iterations to make.
     */
    public void walls(int howMany) {
        Random random = new Random();
        for (int i = 0; i<howMany; i++) {
            int r = random.nextInt(ten);
            int c = random.nextInt(ten);
            byte w = (byte)(1 << random.nextInt(4));
            int fr,fc;
            byte or,or2;
            or = rooms[r][c];
            rooms[r][c] |= w;


            if (w == westWall && c > 0){
                fr = r;
                fc = c-1;
                or2 = rooms[fr][fc];
                rooms[r][c-1] |= eastWall;
            }else
            if (w == northWall && r > 0){
                fr = r-1;
                fc = c;
                or2 = rooms[fr][fc];
                rooms[r-1][c] |= southWall;
            }else
            if (w == eastWall && c <ten-1){
                fr = r;
                fc = c+1;
                or2 = rooms[fr][fc];
                rooms[r][c+1] |= westWall;
            }else
            if (w == southWall && r < ten-1){
                fr = r+1;
                fc = c;
                or2 = rooms[fr][fc];
                rooms[r+1][c] |= northWall;
            }else{
                fr = -1;
                fc = -1; // this shouldn't happen.  But compiler wants an error
                or2 = -1;
            }
            if (!floodtest()){
                rooms[r][c] = or;       // take out change
                rooms[fr][fc] = or2;
            }
        }
    }

    /**
     * initialize mice on board,
     * mouse image
     * @param x a png file from the drawable resources
     */
    public void mice(Drawable x){
        mouse = drawableToBitmap(x);
        for (int r = 0; r<ten; r++){
            for (int c=0; c<ten; c++){
                rooms[r][c] |= mouseHere;
            }
        }
    }

    public void cat(Cat c, Drawable x){
        cat = drawableToBitmap(x);
        rooms[0][0]|=catHere;//Mohammad Kabajah
    }
  //Mohammad Kabajah
    public void dog(Dog d ,Drawable x){
    	dog = drawableToBitmap(x);
        rooms[d.row][d.col]|=dogHere;
    }
    /**
     * Code borrowed from
     *  http://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
     * with thanks to kabuko
     * @param drawable a passed-in resource
     */
    Bitmap drawableToBitmap(Drawable drawable){
        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     *
     * @return true if every room reachable from every other
     */
    boolean floodtest(){

        byte wet = (byte) 0x80; // a bit showing that the cell can be reached
        byte [][] t =  rooms.clone(); // not a deep copy; each row identical in both
        //for (int i=0; i<ten; i++){
        //    t[i] = rooms[i].clone();  // replace the rows with a clone of each row
        //}

        PriorityQueue<Point> q = new PriorityQueue<Point>(ten*ten ,new Comparator<Point>() {
            @Override
            public int compare(Point point, Point point2) {
                return 0;
            }
        });
        q.add(new Point(0,0));
        while (q.size()>0){
            Point spot = q.poll();
            int r = spot.x;
            int c = spot.y;
            t[r][c] |= wet;  // spot certainly floodable.
            byte v = t[r][c];
            if (0==(v&westWall) && (0==(wet&t[r][c-1]))) {
                    q.add(new Point(r, c - 1));
                    t[r][c - 1] |= wet;
            }
            if (0==(v&eastWall) && (0==(wet&t[r][c+1]))) {
                q.add(new Point(r, c + 1));
                t[r][c + 1] |= wet;
            }
            if (0==(v&northWall) && (0==(wet&t[r-1][c]))) {
                q.add(new Point(r-1, c));
                t[r-1][c] |= wet;
            }
            if (0==(v&southWall) && (0==(wet&t[r+1][c]))) {
                q.add(new Point(r+1, c ));
                t[r+1][c] |= wet;
            }
        }
        boolean retval = true;
        for (int r=0; r<ten; r++)
            for (int c=0; c<ten; c++){
                if (0==(wet & t[r][c])) {
                    retval = false;
                }
                t[r][c] &= 0x7F; // dry this spot.  It was wet
                if (t[r][c] >= 15){
                    retval = false;
                }
            }
        return retval;
    }

    /**
     *
     * @param d direction, one of 0-3
     * @param row position in rooms array
     * @param col position in rooms array
     * @return true if way blocked in direction
     */
    public boolean blocked(int d, int row, int col) {
        if (d>3 || d<0){
            return false; // bad direction, but way cannot be blocked
        }
        return 0!=((1<<d)&rooms[row][col]);
    }
}
