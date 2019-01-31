import java.util.ArrayList;

public class MapNode {
	String name;
	double longitude;
	double latitude;
	double drawX;
	double drawY;
	boolean hasFirstAid;
	ArrayList<MapEdge> edges;
	
	MapNode(String name, double longitude, double latitude, double drawX, double drawY, boolean firstAid){
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.drawX = drawX;
		this.drawY = drawY;
		this.hasFirstAid = firstAid;
		this.edges = new ArrayList<>();
	}
	
	public void addEdge(MapEdge newEdge){
		this.edges.add(newEdge);
	}
	
	public ArrayList<MapEdge> getEdges(){
		return this.edges;
	}
	
	
	
	public String toString(){
		String returnValue = "{";
		for(MapEdge edge : this.edges){
			returnValue += String.format("<%s => %s>\n", this.name, edge.nextNode);
		}
		returnValue += String.format("%b}", this.hasFirstAid);
		return returnValue;
	}
	
	
}
