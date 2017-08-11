package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;

import java.io.IOException;
import java.io.InputStream;

public class Adventure {
	boolean allSetUp = false;
	static Messages messages = new Messages();
	
	

	Room current;
	String [] carrying ;
	int carryingIndex=-1;

/**
 * Read an initialization file and set up the game
 * @param filename
 */
	
	String readLine(InputStream fr){
		String s = "";
		try {
			char c = (char) fr.read();
			while(c!='\n'){
			s+=c;
			c =(char) fr.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
	}
	
	void setup(InputStream fr){
		carrying = new String[23];
		try {
		
			
		while (true){// read various components
			String compNum = readLine(fr);
			switch (Integer.parseInt(compNum)){
			case 0: break;
			case 1:		// long room descriptions
				while (true){
					String []line = readLine(fr).split("\t");
					int id = Integer.parseInt(line[0]);
					if (id == -1) break; 
					Room r = Room.getRoomById(id);
					r.addLongDescriptionLine(line[1]);
				}
				
				break;
			case 2:		// long room descriptions
				while (true){
					String []line =readLine(fr).split("\t");
					int id = Integer.parseInt(line[0]);
					if (id == -1) break; 
					Room r = Room.getRoomById(id);
					r.addShortDescriptionLine(line[1]);
				}
				break;
			case 3:		// transitions <start room id> <end room id> <word id>+ 
				while (true){
					String []line = readLine(fr).split("\t");
					int id = Integer.parseInt(line[0]);
					if (id == -1) break; 
					Room r = Room.getRoomById(id);
					Room s = Room.getRoomById( Integer.parseInt(line[1]));
					for (int wix=2; wix<line.length; wix++){
						r.addPortal( Integer.parseInt(line[wix]), s);
					}
				}
				break;
			case 4:		// vocabulary: <wid> <5-letter-word-prefix>
				while (true){
					String []line = readLine(fr).split("\t");
					int id = Integer.parseInt(line[0]);
					if (id == -1) break; 
					Word.initWord(id, line[1]);
				}
				break;
			case 5:		// <special-state>*100+room \t message
				while (true){
					String []line = readLine(fr).split("\t");
					int id = Integer.parseInt(line[0]);
					if (id == -1) break; 
                                        int x = Integer.parseInt(line[0]);
                                        x = x%100;
                                        x+=1000;
                                        Word w = Word.getWordById(x);
                                        if(w==null)
                                            continue;
                                        
                                        Item i = Item.addItemById(id,w.getText()+"");
                                        i.addItemMsg(id, line[1]);
                                        
                                        
					// should do something to initialize, but don't know what yet
				}
				break;
			case 6: // messages: <id>  \t <message>
				while (true) {
					String[] line = readLine(fr).split("\t");
					int id = Integer.parseInt(line[0]);
					if (id == -1)
						break;
					Messages.initMessageFragment(id,line[1]);
				}
				break;
                        case 7: //fill items in the rooms
                                while(true){
                                        String[] line = readLine(fr).split("\t");
					int id = Integer.parseInt(line[0]);
					if (id == -1)
						break;
                                        Room r = Room.getRoomById(id);
                                        for(int i = 1 ;i < line.length;i++){
                                            r.addItems(Integer.parseInt(line[i]));
                                        }
					
                                }
                        }
			if (Integer.parseInt(compNum) == 0) break;
		}
		fr.close();
		
		// place one of the 23 objects
		allSetUp = true;
		} catch (Exception x){
			x.printStackTrace();
		}
		//finally { fr.close(); }

	}

	/**
	 * play the adventure game
	 * @return
	 */
	String play(Command  cmd){
		String s = "";
		Command c = cmd;
		if((c.first.id>2000 && c.first.id<3000) && (c.second!=null ) && (c.second.id >2000 && c.second.id<3000))
			s = speak(61);
		else if((c.first.id>2000 && c.first.id<3000)&& (c.second!=null ) && (c.second.id >1000 && c.second.id<2000)){
			s = vact(c);
			}
		if(c.first!=null)
			s += "\n" +act(c);
		
		return s;
	}
	
	String ready(){
		if (!allSetUp) throw new Error("oops! not set up!");
		String s = speak(1);
		current = Room.getRoomById(1);
		s += "\n" + current.longDescription;
		return s;
	}
	private String act(Command c) {
		// TODO Auto-generated method stub
		String s = "";
		Room follows = current.getNextRoomByDoor(c.first.id);
		if (follows != null){
			current = follows;
			s = current.describeLong();
			
			return s;
		}
		return s;
		
	}
        
        private String vact(Command c) {
        	String s = "";
            if(haveItem(Item.getItemName(c.second.id))==false){
                Item.takeItem(c.second.id);
                additem(c.second.id);
                s = speak(54);
            }
            
            else  s = speak(24);
            
            
            return s ;
           }
        
        boolean haveItem(String s){
            if(carryingIndex==-1){
                for (int i = 0 ; i < carrying.length;i++)
                   carrying[i]=new String();
               carryingIndex++;
            }
            for (int i = 0 ; i < carrying.length;i++){
                if(carrying[i].equals(s))
                    return true;
            }
            return false;
        }
        void additem(int id){
           String name = Item.getItemName(id);
           if(carryingIndex==-1)
           {
               for (int i = 0 ; i < carrying.length;i++)
                   carrying[i]=new String();
               carryingIndex++;
           }
           else {
               carrying[carryingIndex++]=name;
           }
        }
        
	
	/**
	 * pronounce messages by number
	 * 
	 */
	public static String speak(int id){
		 String s = Messages.speak(id);
		 return s;
	}


}
