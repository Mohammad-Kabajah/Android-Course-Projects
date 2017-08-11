package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;

import java.io.InputStreamReader;


public class Command {
	Word first;
	Word second;
	boolean twoWords;
	


	/**
	 * I'll later add second constructor for two-word commands
	 * @param first
	 */
	Command(Word first){
		this.first = first;
		this.second = null;
		this.twoWords = false;	
	}
        Command(Word first,Word second){
                this.first = first;
		this.second = second;
		this.twoWords = true;	
        }
}