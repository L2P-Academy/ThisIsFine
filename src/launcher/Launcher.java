package launcher;

import view.MenuView;

import view.TerminalView;

import controller.SoundController;

public class Launcher {

	public static void main(String[] args) {
		new MenuView(new SoundController());
	}

}
