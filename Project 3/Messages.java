package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;


public class Messages {
	static Message[] messages = new Message[100];
	static int firstFree = 0;
	
	static void initMessageFragment(int id, String text){
		if (firstFree >= messages.length){
			Message[] t = new Message[2*messages.length];
			System.arraycopy(messages, 0, t, 0, firstFree);
			messages = t;
		}
		messages[firstFree++] = new Message(id,text);
	}
	
	static String speak(int id){
		String s = "";
		for (int i=0; i<firstFree; i++){
			if (messages[i].id == id){				
					s += messages[i].speak();				
			}
		}
		return s;
	}
	

}
