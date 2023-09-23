package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class Task {
    protected Integer id;
    protected String name;
    protected String description;
    protected Status status;
    protected LocalDateTime startTime;
    protected int duration;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }
    public Task(Integer id, String name, Status status, String description, String time, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;

        if(!time.equals("null")){
            this.startTime = LocalDateTime.parse(time);
        }

        this.duration = duration;
    }

    public Task(String name, String description, String time, int duration) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.startTime = LocalDateTime.parse(time, formatter);
        this.duration = duration;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /*@Override
    public String toString() {
        return "tasks.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}' + "\n";
    }*/

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
