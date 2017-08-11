package edu.birzeit.fall2014.encs539.id1110600.walkplanner;

/**
 * This class is used to create an object for each router containing the BSSID
 * of the router and the location of that router
 * **/
public class Node {
	private String BSSID = "00:00:00:00:00:00";
	private String location;

	public Node(String BSSID, String location) {
		this.BSSID = BSSID;
		this.location = location;
	}

	String getBSSID() {
		return BSSID;
	}

	String getlocation() {
		return location;
	}
}
