package Model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Observable;

import Controller.MainController;
import Controller.OptionsController;
import Model.Interface.Difficulty;
import Model.Interface.Direction;
import Model.Interface.IActor;
import Model.Interface.IElement;
import Model.Interface.IPlayerBone;
import Model.Interface.ISphere;

/**
 * 
 * 
 */
public class SnakeHeadModel extends Observable implements IPlayerBone {
	private Point2D.Double movement;
	private double lastMove = 0;
	private Ellipse2D.Double bounding;
	private Direction newDirection = Direction.NONE;
	private Direction direction = Direction.RIGHT;
	private IPlayerBone last;
	private int maxX;
	private int maxY;

	/**
	 * 
	 * @param gamePanelView
	 * @param x
	 * @param y
	 * @param bufferedImage
	 */
	public SnakeHeadModel(int maxX, int maxY, double x, double y, int width,
			int height) {
		this.bounding = new Ellipse2D.Double(x, y, width, height);
		movement = new Point2D.Double(x, y);
		this.maxX = maxX;
		this.maxY = maxY;
	}

	@Override
	public void actuate(double delta) {
		lastMove += delta;
		Difficulty difficuty = Difficulty.fromString(OptionsController
				.getInstance().getOption("difficulty"));
		int speed = difficuty == Difficulty.SIMPLE ? 110
				: difficuty == Difficulty.MEDIUM ? 80 : 50;
		if (lastMove > speed) {
			lastMove = 0;
			move();
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * 
	 */
	private void move() {
		if (newDirection != Direction.NONE) {
			direction = newDirection;
			newDirection = Direction.NONE;
		}

		movement.x = bounding.x;
		movement.y = bounding.y;

		switch (direction) {
		case DOWN:
			bounding.y = (bounding.y + bounding.getHeight()) % maxY;
			break;
		case UP:
			bounding.y -= bounding.getHeight();
			if (bounding.y < 0) {
				bounding.y += maxY;
			}
			break;
		case RIGHT:
			bounding.x = (bounding.x + bounding.getWidth()) % maxX;

			break;
		case LEFT:
			bounding.x -= bounding.getWidth();
			if (bounding.x < 0) {
				bounding.x += maxX;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void checkCollision(IActor actor) {
		if (bounding.intersects(actor.getBounding())
				&& !(actor instanceof IElement || actor instanceof ISphere)) {
			MainController.getInstance().gameOver();
		}
	}

	/**
	 * 
	 */
	public void increaseLength() {
		double x = last.getBounding().getX();
		double y = last.getBounding().getY();
		switch (last.getDirection()) {
		case DOWN:
			y -= bounding.getHeight();
			break;
		case UP:
			y += bounding.getHeight();
			break;
		case RIGHT:
			x -= bounding.getWidth();
			break;
		case LEFT:
			x += bounding.getWidth();
			break;
		default:
			break;
		}
		last = MainController.getInstance().createSnakeTail((int) x, (int) y,
				last);
	}

	@Override
	public Rectangle2D getBounding() {
		return this.bounding.getBounds2D();
	}

	@Override
	public Point2D.Double getMovement() {
		return movement;
	}

	/**
	 * 
	 * @param last
	 */
	public void setLast(IPlayerBone last) {
		this.last = last;
	}

	/**
	 * 
	 * @param direction
	 */
	public void rotateSnake(Direction direction) {
		if ((this.direction == Direction.RIGHT && (direction == Direction.UP || direction == Direction.DOWN))
				|| (this.direction == Direction.LEFT && (direction == Direction.UP || direction == Direction.DOWN))
				|| (this.direction == Direction.UP && (direction == Direction.RIGHT || direction == Direction.LEFT))
				|| (this.direction == Direction.DOWN && (direction == Direction.LEFT || direction == Direction.RIGHT))) {
			newDirection = direction;
		}
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}
}