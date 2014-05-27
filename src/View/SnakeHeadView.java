package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;

import Model.Interface.Direction;
import Model.Interface.IPlayerBone;

/**
 * 
 * 
 */
public class SnakeHeadView extends SpriteView implements Observer {
	private int rotation;
	private Direction direction;

	/**
	 * 
	 * @param path
	 * @param x
	 * @param y
	 * @param gamePanelView
	 */
	public SnakeHeadView(String path, double x, double y,
			GamePanelView gamePanelView) {
		super(path, x, y, gamePanelView);
		this.direction = Direction.RIGHT;
	}

	@Override
	public void drawObjects(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;
		AffineTransform oldTransorfm = g2.getTransform();
		AffineTransform at = new AffineTransform();
		rotation = 0;
		switch (direction) {
		case UP:
			rotation = 270;
			break;
		case DOWN:
			rotation = 90;
			break;
		case LEFT:
			rotation = 180;
			break;
		}
		at.rotate(Math.toRadians(rotation), (int) x + (10), (int) y + (10));
		g2.transform(at);
		g2.drawImage(bufferedImage, (int) x, (int) y, null);
		g2.setTransform(oldTransorfm);
	}

	@Override
	public void update(Observable o, Object arg) {
		IPlayerBone head = (IPlayerBone) o;
		this.x = head.getX();
		this.y = head.getY();
		this.direction = head.getDirection();
	}
}
