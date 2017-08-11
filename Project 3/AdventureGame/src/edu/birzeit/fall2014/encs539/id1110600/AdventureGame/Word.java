package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;


/**
 * Words class relates text words to word ids used in Crowther adventure
 * data file.
 * @author stephen taylor
 * September 2014
 */

import java.util.HashMap;

public class Word {
	private static Word [] words = new Word[100];
	private static HashMap<String,Word> abbrevs = new HashMap<String,Word>();
	private static int nextId = 0;
	private String text;
	int id;
	
	private Word(int id2, String value) {
		id = id2;
		text = value;
		abbrevs.put(abbreviate(value), this);
		//if (words[id] == null) words[id] = this; // maybe only one caller; it does this
	}

	public static Word getWordById(int id){
		if (words[id] != null){ return words[id]; }
		else return null;
	}
	
	public static Word getWordByName(String name){
		
		return abbrevs.get(abbreviate(name));
	}
	
	/**
	 * This is a factory method, related to the presence of word id numbers in the
	 * initialization file.
	 * @param id
	 * @param value
	 */
	public static void initWord(int id, String value){
		Word v = getWordByName(abbreviate(value));
		if (v != null && id != v.id) {
			//throw new Error("abbrev("+value+") has id "+v.id+ " and "+ id); // removed. STEPS is both 34 and 1006
		}
		while (id >= words.length){
			Word [] t = new Word[2*words.length];
			System.arraycopy(words, 0, t, 0, words.length);
			words = t;
		};
		if (v == null) v = new Word(id, value);
		if (words[id] == null) words[id] = v;
		
	}
	
	
	String getAbbrev(){
		return abbreviate(text);
	}
	
	static String abbreviate(String name){
		String abbrev;
		if (name.length() > 5) abbrev = name.substring(0,5);
		else abbrev = name;
		return abbrev;
	}

	public String getText() {
		return text;
	}		
		
		
	

}
