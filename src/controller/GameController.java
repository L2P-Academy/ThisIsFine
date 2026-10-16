package controller;

import model.CameraModel;
import model.FireModel;
import model.GameStateModel;
import model.ItemModel;
import model.ItemRepository;
import model.PlayerModel;
import model.ServerModel;
import model.TaskModel;
import model.TaskRepository;
import model.TerminalModel;
import view.GameOverView;
import view.GameView;
import view.PauseView;
import view.TerminalView;

//author: HKD-DP-KW

public class GameController {

	//attributes
	private GameView gameView;
	private PlayerModel player;
	private FireModel fire;
	private ServerModel leftServer;
	private ServerModel rightServer;
	private TerminalModel terminal;
	private CameraModel camera;
	private GameStateModel gameState;
	private SoundController soundController;

	private int detection;
	private boolean gameOver;

	private int extinguisherCol = 1;
	private int extinguisherRow = 14;
	private int leftServerCol = 12;
	private int leftServerRow = 8;
	private int rightServerCol = 20;
	private int rightServerRow = 8;
	private int terminalCol = 15;
	private int terminalRow = 6;
	private int exitCol = 16;
	private int exitRow = 15;

	//constructor
	public GameController() {
	}

	//methods
	public void startNewGame() {
		resetItemsAndTasks();

		player = new PlayerModel(16, 8, 1, "Test Player");
		gameState = new GameStateModel(32, 16, 16, 8, "00:00:00");
		fire = new FireModel(25, 10, 1, "links");
		leftServer = new ServerModel("Server 1", 25);
		rightServer = new ServerModel("Server 2", 63);
		camera = new CameraModel(90, 5, 3, 10, 0, 31, 15);

		TaskModel firstTask = TaskRepository.getAllTasks().get(0);
		terminal = new TerminalModel(firstTask, 5);
		detection = 0;
		gameOver = false;

		ItemModel extinguisher = ItemRepository.getItemById(1);
		extinguisher.setPositionX(extinguisherCol);
		extinguisher.setPositionY(extinguisherRow);

		soundController = new SoundController();
		if (soundController.musicClip != null) {
			soundController.playMusic(soundController.musicClip);
		}

		if (gameView != null) {
			gameView.dispose();
		}
		gameView = new GameView();
		showState();
	}

	public void playerAction(int row, int col) {
		if (gameOver) {
			return;
		}

		player.setPosition(col, row);
		gameState.setPlayerX(col);
		gameState.setPlayerY(row);

		ItemModel extinguisher = ItemRepository.getItemById(1);

		if (col == extinguisherCol && row == extinguisherRow && extinguisher.isCollected() == false) {
			extinguisher.collectItem();
		} else if (col == leftServerCol && row == leftServerRow && extinguisher.isCollected()) {
			coolServer(1);
		} else if (col == rightServerCol && row == rightServerRow && extinguisher.isCollected()) {
			coolServer(2);
		} else if (col == terminalCol && row == terminalRow) {
			useTerminal();
		} else if (col == exitCol && row == exitRow) {
			leaveBuilding();
		}

		checkCamera(row, col);
	}

	public void showPause() {
		new PauseView();
	}

	private void coolServer(int serverNumber) {
		if (serverNumber == 1) {
			int heat = leftServer.getHeatLevel() - 10;
			if (heat < 0) {
				heat = 0;
			}
			leftServer.setHeatLevel(heat);
			fire.setIntensity(heat);
		} else {
			int heat = rightServer.getHeatLevel() - 10;
			if (heat < 0) {
				heat = 0;
			}
			rightServer.setHeatLevel(heat);
		}

		showState();

		if (leftServer.getHeatLevel() == 0 && rightServer.getHeatLevel() == 0) {
			markFireTaskDone();
		}
	}

	private void useTerminal() {
		TaskModel task = terminal.getCurrentTask();
		if (task.getId() == 5 || task.getId() == 7) {
			return;
		}
		if (terminal.getTotalAttempts() <= 0) {
			return;
		}

		terminal.setTotalAttempts(terminal.getTotalAttempts() - 1);
		advanceTask();
		new TerminalView();
	}

	private void leaveBuilding() {
		if (terminal.getCurrentTask().getId() == 7) {
			advanceTask();
		}
	}

	private void markFireTaskDone() {
		TaskModel current = terminal.getCurrentTask();
		if (current.getId() == 5 && current.isCompleted() == false) {
			advanceTask();
			return;
		}

		for (TaskModel task : TaskRepository.getAllTasks()) {
			if (task.getId() == 5) {
				task.setCompleted(true);
				task.setActive(false);
			}
		}
	}

	private void advanceTask() {
		TaskModel task = terminal.getCurrentTask();
		task.setCompleted(true);
		task.setActive(false);

		TaskModel next = null;
		for (TaskModel candidate : TaskRepository.getAllTasks()) {
			if (candidate.getId() > task.getId() && candidate.isCompleted() == false) {
				next = candidate;
				break;
			}
		}

		if (next != null) {
			next.setActive(true);
			terminal.setCurrentTask(next);
		}
		showState();
	}

	private void checkCamera(int row, int col) {
		if (camera.isTargetInRange(col, row) == false) {
			return;
		}

		detection = detection + camera.getDetectionPersec();
		if (detection > 100) {
			detection = 100;
		}
		gameView.setDetection(detection);

		if (detection == 100) {
			gameOver = true;
			if (soundController.musicClip != null) {
				soundController.stopMusic(soundController.musicClip);
			}
			gameView.dispose();
			new GameOverView();
		}
	}

	private void showState() {
		gameView.setTaskText(terminal.getCurrentTask().getTitle());
		gameView.setServerHeat(1, leftServer.getHeatLevel());
		gameView.setServerHeat(2, rightServer.getHeatLevel());
		gameView.setDetection(detection);

		if (terminal.getCurrentTask().isCompleted()) {
			gameView.setTaskText("Geschafft");
		}
	}

	private void resetItemsAndTasks() {
		for (ItemModel item : ItemRepository.getAllItems()) {
			item.setCollected(false);
			item.setVisible(true);
		}

		for (TaskModel task : TaskRepository.getAllTasks()) {
			task.setCompleted(false);
			task.setActive(false);
		}
		TaskRepository.getAllTasks().get(0).setActive(true);
	}
}
