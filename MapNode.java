import java.util.ArrayList;

public class MapNode {
	String name;
	double longitude;
	double latitude;
	double drawX;
	double drawY;
	boolean hasFirstAid;
	ArrayList<MapEdge> edges;

	public MapNode(String name, double longitude, double latitude, double drawX, double drawY, boolean firstAid) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.drawX = drawX;
		this.drawY = drawY;
		this.hasFirstAid = firstAid;
		this.edges = new ArrayList<>();
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
		returnValue += String.format("%b}", this.hasFirstAid);
		return returnValue;
	}

}
