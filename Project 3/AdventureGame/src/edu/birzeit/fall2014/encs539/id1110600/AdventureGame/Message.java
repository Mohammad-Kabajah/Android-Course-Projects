package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;


public class Message {
	int id;
	String fragment;
	
	public Message (int id, String fragment){
		this.id = id;
		this.fragment = fragment;
	}
	
	public String speak(){
		//System.out.println(fragment);
		return fragment;
	}

}
