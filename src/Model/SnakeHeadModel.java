package Model;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Observable;

import Controller.MainController;
import Model.Interface.Direction;
import Model.Interface.IActor;
import Model.Interface.IConstants;
import Model.Interface.IElement;
import Model.Interface.IPlayerBone;
import View.GamePanelView;
import View.SnakeTailView;

/**
 * 
 * 
 */
public class SnakeHeadModel extends Observable implements IActor, IPlayerBone {
	private Point2D.Double movement;
	private int speed = IConstants.SNAKE_SPEED;
	private double lastMove = 0;
	private Ellipse2D.Double bounding;
	private Direction newDirection = Direction.NONE;
	private Direction direction = Direction.RIGHT;
	private IPlayerBone last;
	private GamePanelView gamePanelView;
	private Logic logic;

	/**
	 * 
	 * @param gamePanelView
	 * @param x
	 * @param y
	 * @param bufferedImage
	 */
	public SnakeHeadModel(GamePanelView gamePanelView, double x, double y,
			BufferedImage bufferedImage, Logic logic) {
		this.bounding = new Ellipse2D.Double(x, y, bufferedImage.getWidth(),
				bufferedImage.getHeight());
		movement = new Point2D.Double(0, 0);
		this.gamePanelView = gamePanelView;
		this.logic = logic;
	}

	/**
	 * 
	 */
	public void actuate(double delta) {
		lastMove += delta;
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

		movement = new Point2D.Double(bounding.x, bounding.y);

		switch (direction) {
		case DOWN:
			bounding.y = (bounding.y + bounding.getHeight()) % gamePanelView.getHeight();
			break;
		case UP:
			bounding.y -= bounding.getHeight();
			if (bounding.y < 0) {
				bounding.y += gamePanelView.getHeight();
			}
			break;
		case RIGHT:
			bounding.x = (bounding.x + bounding.getWidth()) % gamePanelView.getWidth();
			
			break;
		case LEFT:
			bounding.x -= bounding.getWidth();
			if (bounding.x < 0) {
				bounding.x += gamePanelView.getWidth();
			}
			break;
		default:
			break;
		}
//		if(bounding.x > gamePanelView.getWidth()){
//			bounding.x = 0;
//		} else if(bounding.x < 0) {
//			bounding.x = gamePanelView.getWidth();
//		}
//		
//		if(bounding.y > gamePanelView.getHeight()){
//			bounding.y = 0;
//		} else if (bounding.y < 0) {
//			bounding.y = gamePanelView.getHeight();
//		}
	}

	/**
	 * 
	 */
	public void checkCollision(IActor actor) {
		if (bounding.intersects(actor.getBounding())
				&& !(actor instanceof IElement)) {
			logic.gameOver();
		}
	}

	/**
	 * 
	 */
	public void increaseLength() {

		double x = last.getX();
		double y = last.getY();
		switch (last.getDirection()) {
		case DOWN:
			y += bounding.getHeight();
			break;
		case UP:
			y -= bounding.getHeight();
			break;
		case RIGHT:
			x += bounding.getWidth();
			break;
		case LEFT:
			x -= bounding.getWidth();
			break;
		default:
			break;
		}
		SnakeTailView newTailView = new SnakeTailView(
				IConstants.SNAKE_TAIL_PATH, x, y, gamePanelView);
		SnakeTailModel newTailModel;
		newTailModel = new SnakeTailModel(gamePanelView, x, y,
				(IPlayerBone) MainController.getInstance().getActor(last),
				newTailView.getImage());
		newTailModel.addObserver(newTailView);
		gamePanelView.addActor(newTailView);
		logic.addActor(newTailModel);
		last = newTailModel;
	}

	/**
	 * 
	 */
	public Rectangle2D getBounding() {
		return this.bounding.getBounds2D();
	}

	/**
	 * 
	 */
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
	public double getX() {
		return bounding.x;
	}

	@Override
	public double getY() {
		return bounding.y;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}
}