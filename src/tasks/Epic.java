package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIDs;
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIDs = new ArrayList<>();
    }
    public Epic(Integer id, String name, Status status, String description, String time, int duration) {
        super(id, name, status, description, time, duration);
        this.subtaskIDs = new ArrayList<>();
    }

    public Epic(String name, String description, String time, int duration) {
        super(name, description, time, duration);
        this.subtaskIDs = new ArrayList<>();
    }

    public void setIdSubtask(Subtask subtask){
        subtaskIDs.add(subtask.getId());
    }

    /* @Override
    public String toString() {
        return "tasks.Epic{" +
                "subtaskIDs=" + subtaskIDs +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}' + "\n";
    }*/

    public ArrayList<Integer> getSubtaskIDs() {
        return subtaskIDs;
    }

    public void setSubtaskIDs(ArrayList<Integer> subtaskIDs) {
        this.subtaskIDs = subtaskIDs;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIDs=" + subtaskIDs +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
