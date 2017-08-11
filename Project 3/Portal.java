package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;


/**
 * Portal is used in an array associated with each room to show exits and 
 * their destinations.
 * @author staylor
 *
 */
public class Portal {
	int word;
	Room destination;
	
	Portal(int w, Room d){
		word = w;
		destination = d;
	} 

}
