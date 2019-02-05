import java.util.Arrays;

import com.sun.javafx.geom.Shape;
import com.sun.prism.Graphics;
import com.sun.prism.paint.Color;

public class MapEdge implements Drawable{
	String nextNode;
	String name;
	double difficulty;
	String directions;

	public MapEdge() {
		//comment
		this.nextNode = null;
		this.name = null;
		this.difficulty = 0;
		this.directions = null;
	}

	public MapEdge(String[] parameters) {
		this.name = parameters[1];
		this.nextNode = parameters[3];
		this.difficulty = Double.parseDouble(parameters[4]);
		String.join(" ", Arrays.copyOfRange(parameters, 5, parameters.length - 1));

	}

	public MapEdge(String name, String nextNode, double difficulty, String directions) {
		this.name = name;
		this.nextNode = nextNode;
		this.difficulty = difficulty;
		this.directions = directions;
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

	public String getName() {
		return this.name;
	}

	public String getNextNode() {
		return this.nextNode;
	}

	public double getDifficulty() {
		return this.difficulty;
	}

	public String getDirections() {
		return this.directions;
	}

	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDifficulty(double diff) {
		this.difficulty = diff;
	}

	public void setDirections(String direc) {
		this.directions = direc;
	}
}
