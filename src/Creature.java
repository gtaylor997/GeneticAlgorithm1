import java.awt.Color;
import java.awt.Graphics;

public class Creature {

	public static int SIZE = 20;
	public static double SPEED = 3.0f;
	public static double ROTATION_SPEED = 1.0f;
	public static double VIEW_RANGE = 90;
	public static double VIEW_ANGLE = 45;
	public static int STATE_COUNT = 32;
	public static double ENERGY_LOSS_RATE = 0.05;
	public static int PERCEPT_COUNT = 4;
	public static int ACTION_COUNT = 3;
	public static double FOOD_ENERGY = 10;

	public static int FOOD_LEFT = 0;
	public static int FOOD_RIGHT = 1;
	public static int FOOD_NONE = 2;
	public static int FOOD_STRAIGHT = 3;

	private double energy;
	private double angle;
	private Point pos;
	private int state = 0;

	private int[][] genes = new int[STATE_COUNT][PERCEPT_COUNT];

	/**
	 * The default constructor for a new creature
	 */
	public Creature() {
		setPos(Simulator.getRandomPos());

		for (int i = 0; i < STATE_COUNT; i++) {
			for (int j = 0; j < PERCEPT_COUNT; j++) {
				genes[i][j] = Utils.rand.nextInt(((STATE_COUNT - 1) * ACTION_COUNT) + ACTION_COUNT - 1);
			}
		}
	}

	/**
	 * Move the creature
	 */
	private void move() {

		getPos().setX((int) ((getPos().getX() + (SPEED * Math.cos(Math.toRadians(angle)))) + Simulator.WIDTH)
				% Simulator.WIDTH);
		getPos().setY((int) ((getPos().getY() + (SPEED * Math.sin(Math.toRadians(angle)))) + Simulator.HEIGHT)
				% Simulator.HEIGHT);

	}

	/**
	 * Turn the creature
	 * 
	 * @param dir
	 *            True = Left, False = Right
	 */
	private void turn(boolean dir) {
		angle += dir ? ROTATION_SPEED : -ROTATION_SPEED;

		angle = angle % 360;
		if (angle > 180) {
			angle = angle - 360;
		} else if (angle < -180) {
			angle = 360 + angle;
		}
	}

	/**
	 * Perform a single action for the creature
	 */
	public void act() {
		setEnergy(getEnergy() - ENERGY_LOSS_RATE);
		int percept = getPercept();
		int gene = genes[state][percept];
		state = Math.floorDiv(gene, 3);
		int action = Math.floorMod(gene, 3);
		switch (action) {
		case 0:
			move();
			break;
		case 1:
			turn(true);
			break;
		case 2:
			turn(false);
			break;
		}
	}

	/**
	 * Get the value for the current percept
	 * 
	 * @return The value of the percept
	 */
	private int getPercept() {
		Food closest = getClosest();
		double distance = Utils.getDistance(pos, closest.getPos());
		if (distance > VIEW_RANGE) {
			return FOOD_NONE;
		}

		double foodAngle = getFoodAngle(closest);

		if (foodAngle > -VIEW_ANGLE && foodAngle < 0) {
			return FOOD_LEFT;
		}
		if (foodAngle < VIEW_ANGLE && foodAngle > 0) {
			return FOOD_RIGHT;
		}
		if (foodAngle == 0) {
			return FOOD_STRAIGHT;
		}
		return FOOD_NONE;
	}

	private double getFoodAngle(Food closest) {
		/*
		 * double straightAngle = 0; double foodAngle = 0; double dx =
		 * pos.getX() - closest.getPos().getX(); double dy = pos.getY() -
		 * closest.getPos().getY(); if (dx != 0) { straightAngle =
		 * Math.toDegrees(Math.atan(dy / dx)); } if (dx < 0) { if (dy < 0) {
		 * straightAngle = (-180 + straightAngle) % 360; foodAngle =
		 * straightAngle - angle; } else if (dy > 0) { straightAngle = (180 -
		 * straightAngle) % 360; foodAngle = straightAngle - angle; } } else if
		 * (dx > 0) { if (dy < 0) { foodAngle = straightAngle + angle; } else if
		 * (dy > 0) { foodAngle = straightAngle - angle; } } else { if (dy < 0)
		 * { foodAngle = -90 - angle; } else if (dy > 0) { foodAngle = 90 -
		 * angle; } } return foodAngle;
		 */

		double dx1 = ((SIZE / 2) * Math.cos(Math.toRadians(angle)));
		double dy1 = ((SIZE / 2) * Math.sin(Math.toRadians(angle)));
		int dx2 = closest.getPos().getX() - pos.getX();
		int dy2 = closest.getPos().getY() - pos.getY();
		double foodAngle = Math.toDegrees(Math.atan2(dx1 * dy2 - dy1 * dx2, dx1 * dx2 + dy1 * dy2));
		return foodAngle;
	}

	Food getClosest() {
		Food closest = Simulator.food.get(0);
		double distance = Utils.getDistance(closest.getPos(), getPos());
		for (Food f : Simulator.food) {
			double c = Utils.getDistance(f.getPos(), getPos());
			if (c < distance) {
				distance = c;
				closest = f;
			}
		}
		return closest;
	}

	/**
	 * Mutate the genes of the creature
	 */
	public void mutate() {
		int max = (STATE_COUNT * 3) + 2;
		for (int i = 0; i < STATE_COUNT; i++) {
			for (int j = 0; j < PERCEPT_COUNT; j++) {
				int mod = (int) (modificationFunction(max) * Simulator.mutationFunction());
				genes[i][j] += mod;
			}
		}

	}

	/**
	 * Function to scale the value of the mutation closer to 0
	 * 
	 * @return The scaled value
	 */
	public double modificationFunction(double max) {
		double r = Utils.rand.nextDouble() * 2 - 1;
		r = Math.pow(r, 19);
		r *= max;
		return r;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public void draw(Graphics g) {
		g.setColor(Colors.CREATURE);
		g.drawOval(getPos().getX() - SIZE / 2, getPos().getY() - SIZE / 2, SIZE, SIZE);

		for (Food f : Simulator.food) {
			if (Utils.getDistance(pos, f.getPos()) < VIEW_RANGE) {
				g.setColor(Colors.IN_RANGE);
				double foodAngle = getFoodAngle(f);
				if (foodAngle > -VIEW_ANGLE && foodAngle < 0) {
					g.setColor(Colors.LEFT_VIEW);
				}
				if (foodAngle < VIEW_ANGLE && foodAngle > 0) {
					g.setColor(Colors.RIGHT_VIEW);
				}
				if (foodAngle == 0) {
					g.setColor(Colors.DIRECT_VIEW);
				}
				g.drawLine(pos.getX(), pos.getY(), f.getPos().getX(), f.getPos().getY());
			}
		}

		int dx = (int) ((SIZE / 2 * Math.cos(Math.toRadians(angle))) + getPos().getX());
		int dy = (int) ((SIZE / 2 * Math.sin(Math.toRadians(angle))) + getPos().getY());
		g.setColor(Colors.CREATURE);
		g.drawLine(getPos().getX(), getPos().getY(), dx, dy);
		g.drawString(String.format("%f", angle), getPos().getX(), getPos().getY());
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public void eat() {
		energy += FOOD_ENERGY;
	}

}
