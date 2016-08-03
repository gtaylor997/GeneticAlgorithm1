import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

public class Simulator extends Canvas implements Runnable, MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 8928103048763069612L;
	public static int WIDTH = 1200;
	public static int HEIGHT = 800;
	public static int MID_MUTATION_RATE = 1;
	public static double STRETCH_MUTATION_RATE = 0.00007;
	public static int DEATH_PROB = 2000;
	public static int MIN_CREATURES = 8;
	public static int MAX_CREATURES = 30;
	public static int FOOD_COUNT = 40;

	private ArrayList<Creature> creatures;
	public static ArrayList<Food> food;

	private int desiredFPS = 50;
	private long desiredDeltaLoop = (1000 * 1000 * 1000) / desiredFPS;
	private boolean paused;
	private static int timeStep = 0;

	// private BufferStrategy bufferStrategy;

	public Simulator() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		Utils.rand = new Random();
		creatures = new ArrayList<Creature>();
		food = new ArrayList<Food>();
		initCreatures();
	}

	private void initCreatures() {
		for (int i = 0; i < (MIN_CREATURES + MAX_CREATURES) / 2; i++) {
			creatures.add(new Creature());
		}
		for (int i = 0; i < FOOD_COUNT; i++) {
			food.add(new Food());
		}
	}

	public static Point getRandomPos() {
		return new Point((int) Math.floor(Utils.rand.nextDouble() * WIDTH),
				(int) Math.floor(Utils.rand.nextDouble() * HEIGHT));
	}

	public static double mutationFunction() {
		double r = (STRETCH_MUTATION_RATE * timeStep) + MID_MUTATION_RATE;
		r = Math.pow(Math.E, r) + 1;
		return 1 / r;
	}

	public void update() {

		// Make every creature act
		for (Creature c : creatures) {
			c.act();
		}

		// Ensure that the number of creatures does not exceed the maximum by
		// killing the creatures with the least energy
		while (creatures.size() > MAX_CREATURES) {
			Creature min = creatures.get(0);
			double energy = min.getEnergy();
			for (Creature c : creatures) {
				if (c.getEnergy() < energy) {
					min = c;
					energy = c.getEnergy();
				}
			}
			creatures.remove(min);
		}

		// If there are less creatures than the minimum then create 3 more
		// creatures
		while (creatures.size() < MIN_CREATURES) {
			creatures.add(new Creature());
		}

		int eatDistance = Creature.SIZE / 2 + Food.SIZE / 2;
		for (Creature c : creatures) {
			for (Food f : food) {
				if (Utils.getDistance(c.getPos(), f.getPos()) < eatDistance) {
					f.eat();
					c.eat();
				}
			}
		}
		timeStep++;
	}

	private void render() {
		paint(getBufferStrategy().getDrawGraphics());
	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Colors.BACKGROUND);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		for (Creature c : creatures) {
			c.draw(g2);
		}
		for (Food f : food) {
			f.draw(g2);
		}
		g.drawString(Integer.toString(timeStep), 10, 10);
		g.dispose();
		getBufferStrategy().show();
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void run() {

		boolean running = true;
		long beginLoopTime;
		long endLoopTime;
		long currentUpdateTime = System.nanoTime();
		long lastUpdateTime;
		long deltaLoop;

		while (running) {
			beginLoopTime = System.nanoTime();

			render();

			lastUpdateTime = currentUpdateTime;
			currentUpdateTime = System.nanoTime();
			update();

			while (paused) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			endLoopTime = System.nanoTime();
			deltaLoop = endLoopTime - beginLoopTime;

			if (deltaLoop > desiredDeltaLoop) {
				// Do nothing. We are already late.
			} else {
				try {
					Thread.sleep((desiredDeltaLoop - deltaLoop) / (1000 * 1000));
				} catch (InterruptedException e) {
					// Do nothing
				}
			}
		}
	}

	public BufferStrategy getBufferStrategy() {
		return super.getBufferStrategy();
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Mouse Clicked");
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		System.out.println("Mouse pressed");
		for (Creature c : creatures) {
			if (Utils.getDistance(new Point(arg0.getPoint()), c.getPos()) < Creature.SIZE) {
				c.setActive(true);
			} else {
				c.setActive(false);
			}
		}
		render();

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent arg0) {
		System.out.println("Key pressed" + Integer.toString(arg0.getKeyCode()));
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			paused = !paused;
		}

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
