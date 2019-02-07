import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class GraphConstructor {
	static String nodeHelp = "node: name latitude longitude drawX drawY firstAid";
	static String edgeHelp = "edge: name startNode endNode difficulty directions";

	public static void main(String[] args) {

		HashMap<String, MapNode> nodes = new HashMap<>();
		try {
			nodes = GraphConstructor.read();
		} catch (FileNotFoundException e) {
			nodes = new HashMap<>();
		}

		//Scanner textScanner = new Scanner(System.in); // used for console
		// input

		File file = new File("MapData.txt");
		Scanner textScanner;
		try {
			textScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return;
		}
		textScanner.nextLine();
		textScanner.nextLine();
		textScanner.nextLine();
		textScanner.nextLine();

		while (textScanner.hasNext()) {
			 //while(true){ //used for console input
			String line = textScanner.nextLine();
			if (line.trim().toLowerCase().startsWith("end")) {
				break;
			}
			analyzeInput(line.trim().toLowerCase().split(" "), nodes);
		}
		try {
			write(nodes);
		} catch (FileNotFoundException exception) {
			System.out.println("failed to write the file");
			exception.printStackTrace();
		}
		textScanner.close();

	}

	static void analyzeInput(String[] line, HashMap<String, MapNode> map) {
		System.out.println("");
		if (line[0].equals("node:")) {
			if (line.length != 7) {
				System.out.println("invalid format\n" + nodeHelp + '\n' + edgeHelp);
				return;
			}
			System.out.println("created node " + line[1]);
			map.put(line[1], new MapNode(line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3]),
					Double.parseDouble(line[4]), Double.parseDouble(line[5]), Boolean.parseBoolean(line[6])));

		} else if (line[0].equals("edge:")) {
			if (line.length < 5) {
				System.out.println("invalid format\n" + nodeHelp + '\n' + edgeHelp);
				return;
			}
			map.get(line[2]).addEdge(new MapEdge(line));

		} else if (line[0].equals("write")) {
			try {
				GraphConstructor.write(map);
			} catch (FileNotFoundException e) {
				System.out.println("Failed to write the file\n" + e);
			}
		} else if (line[0].equals("print")) {
			System.out.println(map);
		} else {
			System.out.println("invalid input: " + line.toString() + "\nplease enter one of the following\n" + nodeHelp
					+ "\n" + edgeHelp);
		}
	}

	public static void write(HashMap<String, MapNode> map) throws FileNotFoundException {
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("graph.xml")));
		encoder.writeObject(map);
		encoder.close();

	}

	public static HashMap<String, MapNode> read() throws FileNotFoundException {
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("graph.xml")));
		HashMap<String, MapNode> nodes = (HashMap<String, MapNode>) decoder.readObject();
		decoder.close();
		return nodes;
	}

}
