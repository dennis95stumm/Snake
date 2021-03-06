package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.StatusbarModel;

/**
 * Statusbarview Gibt im Spiel die bestimmten Werte in der unteren Statusbar aus
 */
@SuppressWarnings("serial")
public class StatusbarView extends JPanel implements Observer {
	private JLabel playerLabel;
	private JLabel scoreLabel;
	private JLabel highscoreLabel;
	private JLabel difficultyLabel;
	private JLabel bulletCountLabel;

	/**
	 * Konstruktor der Statusbarview Die Labels werden f�r die Statuswerte
	 * erzeugt
	 */
	public StatusbarView() {
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
		setLayout(new GridLayout());
		playerLabel = new JLabel("Spieler: ");
		scoreLabel = new JLabel("Punkte: ");
		highscoreLabel = new JLabel("Highscore: ");
		difficultyLabel = new JLabel("Schwierigkeit: ");
		bulletCountLabel = new JLabel("Sch�sse: ");
		this.add(playerLabel);
		this.add(scoreLabel);
		this.add(highscoreLabel);
		this.add(bulletCountLabel);
		this.add(difficultyLabel);
	}

	@Override
	public void update(Observable o, Object arg) {
		/*
		 * Aktualisieren der Statuswerte durch die neuen Werte aus dem Model
		 */
		StatusbarModel statusbarModel = ((StatusbarModel) o);
		playerLabel.setText("Spieler: " + statusbarModel.getPlayerName());
		scoreLabel.setText("Punkte: " + statusbarModel.getScore());
		highscoreLabel.setText("Highscore: " + statusbarModel.getHighscore());
		difficultyLabel.setText("Schwierigkeit: "
				+ statusbarModel.getDifficulty().toString());
		bulletCountLabel.setText("Sch�sse: " + statusbarModel.getBulletCount());
	}
}