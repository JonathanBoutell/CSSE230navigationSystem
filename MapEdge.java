import java.util.Arrays;

public class MapEdge {
	String nextNode;
	String name;
	double difficulty;
	String directions;
	

	MapEdge(String[] parameters){
		this.name = parameters[1];
		this.nextNode = parameters[3];
		this.difficulty = Double.parseDouble(parameters[4]);
		String.join(" ", Arrays.copyOfRange(parameters, 5, parameters.length - 1));
		
	}
	

}
