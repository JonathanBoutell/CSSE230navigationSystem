
public class Main {
	
	public static final int MAP_WIDTH = 1200;
	public static final int MAP_HEIGHT = 1000;
	public static final double SCALE_FACTOR = 1/3.0;
	public static final int VERTICAL_OFFSET = 700;
	public static final int NODE_DIAMETER = 20;
	public static final int EDGE_THICKNESS = 5;
	public static final double LATITUDE_TO_FEET = .36492390;
	public static final double LONGITUDE_TO_FEET = .23522173;
	
	public static void main(String[] args) {
		new MapFrame();
	}

}