
/**
 * A class to represent a point on the screen
 * @author george
 *
 */
public class Point {
 
	private int x;
	private int y;
	
	/**
	 * Default constructor for the point
	 * @param x The x position of the point
	 * @param y The y position of the point
	 */
	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public Point(java.awt.Point point) {
		this.setX((int) point.getX());
		this.setY((int) point.getY());
	}

	/**
	 * Get the y position of the point
	 * @return The y value
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the y position of the point
	 * @param y The new y value
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the x position of the point
	 * @return The x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set the x position of the point
	 * @param x The new x value
	 */
	public void setX(int x) {
		this.x = x;
	}	
	
}
