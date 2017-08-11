package edu.birzeit.fall2014.encs539.id1110600.AdventureGame;


public class Item {
	String name;
	String msg;
        int id;
        int cases=0;
        int used = 0;
        int taken = 0;
        private static Item[] items = new Item[24*2];
        
        public Item(){
            
        }
        
        public Item(int id){
            this.id = id;
            
        }
        
        public static Item addItemById(int id,String name){
            if(items[id%100]==null){
                items[id%100]= new Item(id);
                items[id%100].name = name;
            }
            else if(items[id%100]!=null){
                int nid;
                items[id%100].cases=1;
                nid = (id%100)+24;
                items[nid]= new Item(id);
                items[nid].cases=1;
                items[nid%100].name = name;
                return items[nid];
            }
            return items[id%100];
            
        }
        public static String getItemName(int id){
            if(items[id%100].cases==0)   
            return items[id%100].name;
            else return items[id%100 + 24].name;
                        
        }
        
        
        public static Item getItemById(int id){
            if(items[id%100].cases ==0)
                return items[id%100];
            else
            return items[id%100 +24];
            
        }
        
        public static void addItemMsg(int id,String line){
            if(items[id%100].cases==0)
                items[id%100].msg=line;
            else{
                items[(id%100)+24].msg=line;
            }
        }
        public static String getItemMsg(int id){
            if(items[id%100].cases==0 || items[id%100].used==0){
                String s = items[id%100].msg;
                return s;
            }
            else{
                String s = items[(id%100)+24].msg;
                return s;
            }
        }    
        
        public static void takeItem(int id){
            if(items[id%100].cases==0){
                items[id%100].taken=1;
                return;
            }
            if (items[id%100].taken == 1)
                ;
        }
}
