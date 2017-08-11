package edu.birzeit.fall2014.encs539.id1110600.hungrycat;

/**
 * Created by staylor on 10/6/14.
 * This class describes the cat and the dogs in pac-cat game.
 * Each moves at a constant speed, so depending on the time of update, we may advance it
 * a greater distance.  But in order to have smooth motion, a Drifter has a fractional
 * position in a cell.
 */
public class Drifter {
    final static int [] sinMinusXHalfPi = {0, -1, 0, 1, 0 };
    final static int WEST = 0;
    final static int NORTH = 1;
    final static int EAST = 2;
    final static int SOUTH = 3;
    final static int STUCK = 4;
    final static int quantum = 20; // smallest unit of time.

    BoardView board;
    int row;
    float rowfrac; // a fraction between zero and 1 describing progress to the next cell.
    int col;
    float colfrac; // yet another fraction
    float speed;   // speed is the rate in rows or columns per millisecond.  It is a game parameter
    long timeUpdate; // last time the position was updated.
    int direction; // one of 0: west; 1: north; 2: east; 3: south; 4: stuck.

    public Drifter(BoardView b, int r, int c, int d){
        board = b;
        row = r;
        rowfrac = 0;
        col = c;
        colfrac = 0;
        speed = 0.001f;
        direction = d;
        timeUpdate = System.currentTimeMillis();
    }

    /**
     * partiallyUpdatePosition() updates the position at most one cell,
     * so that its caller can check for collisions with other drifters.
     */
    void partiallyUpdatePosition(long now){

        if (direction == STUCK) {
            timeUpdate = now;
            return; // stuck.  Won't move
        }
        float deltaT = (now-timeUpdate);
        int dirRow = sinMinusXHalfPi[direction];
        int dirCol = sinMinusXHalfPi[1+direction];
        float nr = dirRow*speed*deltaT+rowfrac + row ;
        float nc = dirCol*speed*deltaT+colfrac + col;
        if (nr < row) while (nr < row){// if we get here, the direction is NORTH
            if (board.blocked(direction,row,col)){
                direction = STUCK;
                rowfrac = 0;
                timeUpdate = now;
                return;
            } else {
                row--;
                rowfrac = (float)(nr - Math.floor(nr));
            }
        } else if (nr >= row+1) while (nr >= row+1){// if we get here, the direction is SOUTH
            if (board.blocked(direction,row,col)){
                rowfrac = 0;
                direction = STUCK;
                timeUpdate =now;
                return;
            } else{
                row++;
                rowfrac = (float) (nr - Math.floor(nr));
            }
        } else if (nc < col)  while (nc < col) {// if we get here the direction is WEST
            if (board.blocked(direction, row, col)) {
                colfrac = 0;
                direction = STUCK;
                timeUpdate = now;
                return;
            } else {
                col--;
                colfrac = (float) (nc - Math.floor(nc));
            }
        } else if (nc >= col+1) while (nc >= col+1) { // if we get here the direction is EAST
            if (board.blocked(direction, row, col)) {
                colfrac = 0;
                direction = STUCK;
                timeUpdate = now;
                return;
            } else {
                col++;
                colfrac = (float) (nc - Math.floor(nc));
            }
        } else { //still in same square.  update fraction
            rowfrac = (float) (nr - Math.floor(nr));
            colfrac = (float) (nc - Math.floor(nc));

        }

        timeUpdate = now;

    }
    /**
     * interim version of updatePosition() just calls partiallyUpdatePosition() until
     * timeUpdate is current
     */
    void updatePosition() {
        long now = System.currentTimeMillis();
        updatePosition(now);
    }
    void updatePosition(long now) {
        for (long i = timeUpdate; i<now; i+= quantum) {
            partiallyUpdatePosition(i);
        }
    }
    //Mohammad Kabajah
	void partiallyUpdatePosition() {
		long now = System.currentTimeMillis();
		partiallyUpdatePosition(now);
	}
}
