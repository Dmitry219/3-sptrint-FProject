package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIDs;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIDs = new ArrayList<>();
    }
    public Epic(Integer id, String name, Status status, String description) {
        super(id, name, status, description);
        this.subtaskIDs = new ArrayList<>();
    }

    public void setIdSubtask(Subtask subtask){
        subtaskIDs.add(subtask.getId());
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "subtaskIDs=" + subtaskIDs +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}' + "\n";
    }

    public ArrayList<Integer> getSubtaskIDs() {
        return subtaskIDs;
    }

    public void setSubtaskIDs(ArrayList<Integer> subtaskIDs) {
        this.subtaskIDs = subtaskIDs;
    }
}
