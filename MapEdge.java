import java.util.Arrays;

public class MapEdge {
	String nextNode;
	String name;
	double difficulty;
	String directions;
	

	MapEdge(){
		this.nextNode = null;
		this.name = null;
		this.difficulty = 0;
		this.directions = null;
	}
	
	MapEdge(String[] parameters){
		this.name = parameters[1];
		this.nextNode = parameters[3];
		this.difficulty = Double.parseDouble(parameters[4]);
		String.join(" ", Arrays.copyOfRange(parameters, 5, parameters.length - 1));
		
	}
	
	MapEdge(String name, String nextNode, double difficulty, String directions){
		this.name = name;
		this.nextNode = nextNode;
		this.difficulty = difficulty;
		this.directions = directions;
	}
	
	void setNextNode(String nextNode){
		this.nextNode = nextNode;
	}
	void setName(String name){
		this.name = name;
	}
	void setDifficulty(double diff){
		this.difficulty = diff;
	}
	void setDirections(String direc){
		this.directions = direc;
	}
	

}
