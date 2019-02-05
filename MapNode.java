import java.util.ArrayList;

import com.sun.javafx.geom.Shape;
import com.sun.prism.Graphics;
import com.sun.prism.paint.Color;

public class MapNode implements Comparable<MapNode>, Drawable{
	String name;
	double longitude;
	double latitude;
	double drawX;
	double drawY;
	boolean hasFirstAid;
	ArrayList<MapEdge> edges;
	MapNode destination;

	public MapNode(String name, double longitude, double latitude, double drawX, double drawY, boolean firstAid) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.drawX = drawX;
		this.drawY = drawY;
		this.hasFirstAid = firstAid;
		this.edges = new ArrayList<>();
	}
	
	public void draw(Graphics g) {
		//TODO finish this method
	}
	
	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public MapNode() {
		this.name = null;
		this.longitude = 0;
		this.latitude = 0;
		this.drawX = 0;
		this.drawY = 0;
		this.hasFirstAid = false;
		this.edges = new ArrayList<>();
	}
	
	public void updateDestination(MapNode newDest) {
		this.destination = newDest;
	}
	
	public MapNode destination() {
		return this.destination;
	}
	
	public double findHeuristicDistance() {
		return distanceBetween(this.destination);
	}
	
	public double distanceBetween(MapNode destination) {
		return Math.sqrt((this.longitude - destination.longitude)*(this.longitude - destination.longitude) + (this.latitude - destination.latitude)*(this.latitude - destination.latitude));
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setDrawX(double x) {
		this.drawX = x;
	}

	public void setDrawY(double y) {
		this.drawY = y;
	}

	public void setFirstAid(boolean hasFirstAid) {
		this.hasFirstAid = hasFirstAid;
	}

	public void setEdges(ArrayList<MapEdge> edges) {
		this.edges = edges;
	}

	public String getName() {
		return this.name;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getDrawX() {
		return this.drawX;
	}

	public double getDrawY() {
		return this.drawY;
	}

	public boolean getFirstAid() {
		return this.hasFirstAid;
	}

	public ArrayList<MapEdge> getEdges() {
		return this.edges;
	}

	public void addEdge(MapEdge newEdge) {
		this.edges.add(newEdge);
	}

	public String toString() {
		String returnValue = "{";
		for (MapEdge edge : this.edges) {
			returnValue += String.format("<%s => %s>\n", this.name, edge.nextNode);
		}
		returnValue += String.format("first aid = %b}", this.hasFirstAid);
		return returnValue;
	}

	@Override
	public int compareTo(MapNode o) {
		return (int) ((int) this.findHeuristicDistance() - o.findHeuristicDistance());
	}
}
