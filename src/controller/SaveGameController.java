package controller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class SaveGameController {
	
	private static final String SAVE_FILE = "savegame.txt";




public void saveGame (int playerRow, int playerCol) {
	String saveData = playerRow +"," + playerCol;	

try {
	Files.writeString(Path.of(SAVE_FILE), saveData);	
} catch (IOException e) {
    e.printStackTrace();
}
}

public String loadGame() {
try {
	return Files.readString(Path.of(SAVE_FILE));
}catch (IOException e) {
	e.printStackTrace();
	return null;
}

}
}