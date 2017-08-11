package edu.birzeit.fall2014.encs539.id1110600.hungrycat;

/**
 * Created by staylor on 10/7/14.
 * This class implements the cat.
 * The cat shows its position on the board, so that code (not yet written)
 * can easily check for collisions.
 */
public class Cat extends  Drifter {
    public Cat(BoardView b){
        super (b, 0, 0, Drifter.STUCK);
    }

    /**
     * partiallyUpdatePosition() may :
     *  1) move a short distance inside a room
     *  2) return about to exit the room
     *  3) pass into a room
     * After any of these actions, it returns, marking the timeUpdate field
     * with the time at which its last simulated action occurred.
     * Hence we always come out of
     * partiallyUpdatePosition before changing rooms, in order to check to see if other
     * Drifters (the dogs, of course, not yet coded) will come into the room before we
     * leave it.
     * The Cat version of partiallyUpdatePosition() marks the position of the cat with
     * a bit in the room, and removes any mice present.
     */
    @Override 
    void partiallyUpdatePosition(){
        board.rooms[row][col] &= (0xff ^ BoardView.catHere);
        super.partiallyUpdatePosition();
        board.rooms[row][col] |= BoardView.catHere;
        /**
         * Mohammad Kabajah
         * here we check if there is a mouse in the room and if there is one
         * we remove it from the room and add the counter of the mice collected
         * **/
        if((board.rooms[row][col]&BoardView.mouseHere)!=0){
        	board.rooms[row][col] &= (0xff ^BoardView.mouseHere); // the cat gets the mouse, if present
        	PacCat.count++;
        }
    }
}
