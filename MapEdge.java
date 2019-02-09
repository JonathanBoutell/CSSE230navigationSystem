import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class MapEdge implements Drawable{
	String nextNode;
	String name;
	double difficulty;
	String directions;
	Color color;

	public MapEdge() {
		//comment
		this.nextNode = null;
		this.name = null;
		this.difficulty = 0;
		this.directions = null;
		color = Color.RED;
	}

	public MapEdge(String[] parameters) {
		this.name = parameters[1];
		this.nextNode = parameters[3];
		this.difficulty = Double.parseDouble(parameters[4]);
		this.directions = String.join(" ", Arrays.copyOfRange(parameters, 5, parameters.length - 1));
		findColor();
	}

	public MapEdge(String name, String nextNode, double difficulty, String directions) {
		this.name = name;
		this.nextNode = nextNode;
		this.difficulty = difficulty;
		this.directions = directions;
		findColor();
	}
	
	public void draw(Graphics g) {
		draw(g, color);
	}
	
	public void highlight(Graphics g) {
		draw(g, Color.YELLOW);
	}
	
	private void draw(Graphics g, Color c) {
		// TODO Implement Drawing
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
	
	private void findColor() {
		if(difficulty == 0)
			this.color = Color.RED;
		else if(difficulty == 1)
			this.color = Color.GREEN;
		else if(difficulty == 2)
			this.color = Color.BLUE;
		else
			this.color = Color.BLACK;
	}
	
	public String toString(){
		return this.name + ", to:" + this.nextNode;
	}
	
	
}
