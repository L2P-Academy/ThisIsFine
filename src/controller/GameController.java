package controller;
import model.FireModel;
import view.GameView_old;

//author: HKD-DP-KW


public class GameController {

	//attributes
	private GameView_old gameView;
	private FireModel fire;
	private SoundController soundController;
	
	private int fireRow, fireCol;
	private int extinguisherRow, extinguisherCol;
	private boolean hasExtinguisher;
	private int terminalRow, terminalCol;
	private int taskStep;
	
	//constructor
	public GameController() {
		soundController = new SoundController();
	}
	
	//methods
	public void startNewGame() {
		fire = new FireModel(3, 10, 1, "server");
		fireRow = 8;
		fireCol = 22;
		extinguisherRow = 8;
		extinguisherCol = 17;
		hasExtinguisher = false;
		terminalRow = 5;
		terminalCol = 10; 
		taskStep = 0;
		
		gameView = new GameView_old();
	}
	public void playerAction(int row, int col) {
		if (row == terminalRow && col == terminalCol) {
			taskStep = taskStep + 1;
		} else if (row == extinguisherRow && col == extinguisherCol && hasExtinguisher == false) {
			hasExtinguisher = true;
		} else if (row == fireRow && col == fireCol && hasExtinguisher == true && fire.intensity > 0) {
			fire.intensity = fire.intensity -1; 
			soundController.playSfx(soundController.sfxClip);
		}
	}
}
