import java.util.Random;

public class Utils {

	public static Random rand;

	public static double getDistance(Point pos, Point pos2) {
		int dx = Math.abs(pos.getX() - pos2.getX());
		int dy = Math.abs(pos.getY() - pos2.getY());
		double dx2 = Math.pow(dx, 2);
		double dy2 = Math.pow(dy, 2);
		double d = Math.sqrt(dx2 + dy2);
		return d;
	}

}
