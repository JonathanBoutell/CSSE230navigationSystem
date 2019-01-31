import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class GraphConstructor {
	static String nodeHelp = "node: name latitude longitude drawX drawY firstAid";
	static String edgeHelp = "edge: startNode name endNode difficulty";
	
	
	
	
	
	public static void main(String[] args){
		
		HashMap<String, MapNode> nodes = new HashMap<>();
		try{
			nodes = GraphConstructor.read();
		}
		catch (FileNotFoundException e) {
			nodes = new HashMap<>();
		}
		
		
		
		Scanner textScanner = new Scanner(System.in);
		
		while(true){
			System.out.println("enter a command\n");
			String line = textScanner.nextLine();
			if(line.trim().toLowerCase().startsWith("end")){
				break;
			}
			analyzeInput(line.trim().toLowerCase().split(" "), nodes);
		}
		
		
	}
	
	
	static void analyzeInput(String[] line, HashMap<String, MapNode> map){
		if(line[0].equals("node:")){
			
		}
		else if(line[0].equals("edge:")){
			
		}
		else if (line[0].equals("write")){
			try{
			GraphConstructor.write(map);
			}
			catch (FileNotFoundException e){
				System.out.println("Failed to write the file" + e);
			}
		}
		else{
			System.out.println("invalid input, please enter one of the following\n" + 
		nodeHelp + "\n" + edgeHelp);
		}
	}
	
	
	
	
	
	
	
	
	public static void write(HashMap<String, MapNode> map) throws FileNotFoundException{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("graph.xml")));
		encoder.writeObject(map);
		encoder.close();
		
	}
	
	public static HashMap<String, MapNode> read() throws FileNotFoundException{
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("graph.xml")));
		HashMap<String, MapNode> nodes = (HashMap<String, MapNode>) decoder.readObject();
		decoder.close();
		return nodes;
	}
	
	
	

}
