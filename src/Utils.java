import java.util.Random;

public class Utils {

	public static Random rand;

	public static double getDistance(Point pos, Point pos2) {
		return Math.sqrt((pos.getX()-pos2.getX())^2 + (pos.getY()-pos2.getY())^2);
	}
	
}
