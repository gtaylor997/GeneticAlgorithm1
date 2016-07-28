import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GeneticAlgorithm1 extends BasicGame {

	public static int WIDTH;
	public static int HEIGHT;
	public static int MID_MUTATION_RATE = 1;
	public static double STRETCH_MUTATION_RATE = 0.00007;

	private ArrayList<Creature> creatures;
	public static ArrayList<Food> food;

	public static Random rand;
	
	private static int timeStep = 0;

	public GeneticAlgorithm1(String title) {
		super(title);

		creatures = new ArrayList<Creature>();
		initCreatures();

	}

	private void initCreatures() {
		for (Creature c : creatures) {
			c = new Creature();
		}
	}

	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub

	}

	public void update(GameContainer arg0, int arg1) throws SlickException {
		for (Creature c : creatures) {
			c.act();
		}
	}

	public static Point getRandomPos() {
		return new Point((int) Math.floor(rand.nextDouble() * WIDTH), (int) Math.floor(rand.nextDouble() * HEIGHT));
	}

	public static double mutationFunction() {
		double r = (STRETCH_MUTATION_RATE * timeStep) + MID_MUTATION_RATE;
		r = Math.pow(Math.E, r) + 1;
		return 1 / r;

	}

	public static double getDistance(Point pos, Point pos2) {
		return Math.sqrt((pos.getX()-pos2.getX())^2 + (pos.getY()-pos2.getY())^2);
	}

}
