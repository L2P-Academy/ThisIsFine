package model;

// author: ted88888888, M4ntis911

public class TerminalModel {

    private TaskModel currentTask;
    private int totalAttempts;
    
    public TerminalModel(TaskModel currentTask, int totalAttempts) {
        this.currentTask = currentTask;
        this.totalAttempts = totalAttempts;
    }

    public TaskModel getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(TaskModel currentTask) {
        this.currentTask = currentTask;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }    
}