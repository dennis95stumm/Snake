package Model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

import Model.Interface.IActor;
import Model.Interface.IConstants;
import Model.Interface.IElement;
import View.GamePanelView;

/**
 * 
 * 
 */
public class AppleModel extends Observable implements IActor, IElement {
	private boolean appleAlive = true;
	private Rectangle2D.Double bounding;
	private GamePanelView gamePanelView;

	/**
	 * 
	 * @param gamePanelView
	 * @param bufferedImage
	 */
	public AppleModel(GamePanelView gamePanelView, BufferedImage bufferedImage) {
		this.bounding = new Rectangle2D.Double(0, 0, bufferedImage.getWidth(),
				bufferedImage.getHeight());
		this.gamePanelView = gamePanelView;
		moveApple();
	}

	/**
	 * 
	 */
	public void moveApple() {
		Random random = new Random();
		int x = random.nextInt((int) (gamePanelView.getWidth() - bounding.getWidth()));
		int y = random.nextInt((int) (gamePanelView.getHeight() - bounding.getHeight()));
		bounding.x = (int) (x - (x % bounding.getWidth()));
		bounding.y = (int) (y - (y % bounding.getHeight()));
	}

	/**
	 * 
	 */
	public Rectangle2D getBounding() {
		return bounding;
	}

	/**
	 * 
	 */
	public void actuate(double delta) {
		if (!appleAlive) {
			appleAlive = true;
			moveApple();
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * 
	 */
	public void checkCollision(IActor actor) {
		if (bounding.intersects(actor.getBounding())
				&& actor instanceof SnakeHeadModel) {
			appleAlive = false;
			((SnakeHeadModel) actor).increaseLength();
		}
	}

	@Override
	public boolean checkPosition(Point point) {
		// TODO Auto-generated method stub
		return false;
	}
}
