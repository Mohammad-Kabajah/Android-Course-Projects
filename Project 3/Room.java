package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;


/**
 * The Room class is designed to correspond to a location in the
 * Adventure game, perhaps a chamber in the cavern, perhaps a spot on the road.
 * Each location has a different Room instance.
 * @author staylor
 *
 */
public class Room {
	static Room [] rooms = new Room[10];  // used to implement getRoomById
	
	int id;	// id's start with 1; there is no room zero, in keeping with Fortran
	String longDescription = ""; // initialized from file
	String shortDescription = ""; // as above
	Portal [] adjacent = new Portal[2]; // list of word-ids and rooms they reach
	int freePortal = 0;
	//int [] itemIds = new int[24]; // the current data file has objects 1001-1023 only.
        int [] itemIds ;
	public Room(int id) {
		this.id = id;
	}
	public void addLongDescriptionLine(String line){
		longDescription += "\n" + line;
	}
	public void addShortDescriptionLine(String line){
		shortDescription += "\n" + line;
	}
	
	
	/**
	 * getRoomById either returns an existing Room instance, or creates one,
	 * giving it the appropriate id.
	 * [that is, it is both a factory method and useful for lookup]
	 * @param id
	 * @return
	 */
	static Room getRoomById(int id){
		while (id >= rooms.length){ // extend array
			Room [] t = new Room[2*rooms.length];
			System.arraycopy(rooms,0,t,0,rooms.length);
			rooms = t;
		}
		if (rooms[id] == null){
			rooms[id] = new Room(id); 
		}
		return rooms[id];
	}
	public Room getNextRoomByDoor(int id2) {
		for (int i =0; i<freePortal; i++){
			Portal p = adjacent[i];
			if (p.word == id2){
				return p.destination;
			}
		}
		return null;
	}
	
	/**
	 * addPortal is used in initialization to add new exits to the room.
	 * @param wid
	 * @param s
	 */
	public void addPortal(int wid, Room s) {
		if (freePortal >= adjacent.length){
			Portal [] t = new Portal[2*adjacent.length];
			System.arraycopy(adjacent, 0, t, 0, freePortal);
			adjacent = t;
		}
		adjacent[freePortal++] = new Portal(wid,s);		
	}
	public String describeLong() {
		//System.out.println(longDescription);
		String s = longDescription;
		s += mentionItems(); 
		return s;
		
	}
	// I'm not sure this honors the fortran ...
        
        public void addItems(int id){
           
            if(itemIds==null){
                itemIds= new int[1];
                itemIds[0]=0;
            }
            for(int i = 0;i<=itemIds.length;i++){
                if(i>itemIds.length-1){
                    int [] t = new int[2*itemIds.length];
                    System.arraycopy(itemIds,0,t,0,itemIds.length);
                    itemIds = t;
                }
                    
                if(itemIds[i]==0){
                    itemIds[i]=id;
                    return;
                }
                else  if(itemIds[i]!=0)
                    continue;
            }
        }
        
	private String mentionItems() {
		String s= "";
            if(itemIds != null)
		for (int i=0; i<itemIds.length; i++){
			if (itemIds[i] != 0){
				Word w = Word.getWordById(itemIds[i]);
				//System.out.println("There is a "+ w.getText() + "here"); // this should use text resources..
                                 s += Item.getItemMsg(Item.getItemById(itemIds[i]).id);
                                //System.out.println(s);
                                s+="\n";
			}
		}
            return s;
		
	}
}
