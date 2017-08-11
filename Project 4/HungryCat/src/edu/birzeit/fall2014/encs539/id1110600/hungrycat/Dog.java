package edu.birzeit.fall2014.encs539.id1110600.hungrycat;

import java.util.Random;
/**
 * Created by Mohammad Kabajah on 08/11/2014.
 * This class implements the dog.
 * The dog shows its position on the board
 * can easily check for collisions.
 */

public class Dog extends Drifter{
	static Random random = new Random();
	public Dog(BoardView b) {
		super(b, random.nextInt(10), random.nextInt(10), Drifter.STUCK);
	}

    @Override 
    void partiallyUpdatePosition(){
    	board.rooms[row][col] &= (0xff ^ BoardView.dogHere);
        super.partiallyUpdatePosition();
        board.rooms[row][col] |= BoardView.dogHere;

    }
}
