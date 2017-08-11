package edu.birzeit.fall2014.encs539.id1110600.walkplanner;

/**
 * This class is made to hold the BSSID of the router and the location of the
 * router each router node contain the router unique BSSID and the location of
 * the router Unfortunately i could not get all the BSSID's of all the routers
 * in the campus and all the buildings all the BSSIDs here is from the IT
 * Building but the last one is the one in my home i added it just for testing
 * purposes
 * 
 * it would be better if this data was read from a file and stored in a hash
 * table or stored in SQL lite database it would be more efficient and better
 * and give the option to add new routers later or modify later but
 * unfortunately i didn't do it because i didn't have enough time
 * **/
public class Nodes {
	final static Node IT1 = new Node("FC:0A:81:10:A6:82", "IT Bilding");
	final static Node IT2 = new Node("FC:0A:81:52:76:B2", "IT Bilding");
	final static Node IT3 = new Node("FC:0A:81:52:6C:D2", "IT Bilding");
	final static Node IT4 = new Node("FC:0A:81:10:A6:80", "IT Bilding");
	final static Node IT5 = new Node("FC:0A:81:52:76:B0", "IT Bilding");
	final static Node home = new Node("80:1F:02:93:BD:E6", "Home");

	final static Node[] nodes = new Node[6];

	/**
	 * initialise the array that hold all the routers and their locations
	 * **/
	static void init() {
		nodes[0] = IT1;
		nodes[1] = IT2;
		nodes[2] = IT3;
		nodes[3] = IT4;
		nodes[4] = IT5;
		nodes[5] = home;

	}

	/**
	 * this method is used to give the location of a specific router based on
	 * the BSSID of it and return the location of that router
	 * **/
	static String getLocation(String BSSID) {
		init();
		for (int i = 0; i < nodes.length; i++) {
			if (BSSID != null && BSSID.equalsIgnoreCase(nodes[i].getBSSID())) {
				return nodes[i].getlocation();
			}
		}

		return null;

	}

}
