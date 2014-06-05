package Model;

import java.util.Observable;

import Controller.OptionsController;
import Model.Interface.Difficulty;
import Properties.Player;

/**
 * 
 * 
 */
public class StatusbarModel extends Observable {
	private Player player;

	/**
	 * 
	 */
	public StatusbarModel(Player player) {
		this.player = player;
	}

	/**
	 * 
	 * @return
	 */
	public String getPlayerName() {
		return player.getPlayerName();
	}

	/**
	 * 
	 * @return
	 */
	public int getScore() {
		return player.getScore();
	}

	/**
	 * 
	 * @return
	 */
	public int getHighscore() {
		return player.getHighscore();
	}

	/**
	 * 
	 * @return
	 */
	public int getBulletCount() {
		return player.getBulletCount();
	}

	/**
	 * 
	 * @return
	 */
	public Difficulty getDifficulty() {
		return Difficulty.fromString(OptionsController.getInstance().getOption(
				"difficulty"));
	}

	/**
	 * 
	 */
	public void updateStatus() {
		setChanged();
		notifyObservers();
	}
}