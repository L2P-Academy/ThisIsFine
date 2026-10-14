package model;
// author: Clowd
import java.util.List;
import java.util.ArrayList;

public class TaskRepository {
	private static final List <TaskModel> TASKS = new ArrayList<>();
	

static {
	TASKS.add(new TaskModel("Server Finden", "Finde den Server im Rechenzentrum", 1, true, false));
	TASKS.add(new TaskModel("Firewall umgehen", "Umgehe die Firewall", 2, false, false));
	TASKS.add(new TaskModel("Tastaur finden", "finde die Tastatur", 3, false, false));
	TASKS.add(new TaskModel("Serversicherheit umgehen", "Umgehe die Serversicherheit", 4, false, false));
	TASKS.add(new TaskModel("Feuer Löschen", "Lösche das Feuer", 5, false, false));
	TASKS.add(new TaskModel("Daten stehlen", "Stehle die Daten", 6, false, false));
	TASKS.add(new TaskModel("Rechenzentrum verlassen", "verlasse das Rechenzentrum", 7, false, false));

}
public static List<TaskModel> getAllTasks() {
	return TASKS;
	
}


	
}
