package managers;

import exception.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager{
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    private void save() {//сохранение задач в файл
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            bufferedWriter.write("id,type,name,status,description,startTime,duration,epic\n");
            for (Task task : getAllTasks()) {
                bufferedWriter.write(toString(task));
            }
            for (Task task : getAllEpic()) {
                bufferedWriter.write(toString(task));
            }
            for (Task task : getAllSubtask()) {
                bufferedWriter.write(toString(task));
            }
            bufferedWriter.newLine();

                bufferedWriter.write(historyToString(getHistoryManager()));

        }catch (IOException e){
            throw new ManagerSaveException("Ошибка сохранения!");
        }
    }

    public static String historyToString(HistoryManager manager){//парсинг истории в строку
        ArrayList<String> idsTask = new ArrayList<>();
        for (Task task : manager.getHistory()){
            idsTask.add(String.valueOf(task.getId()));
        }
        return String.join(",", idsTask);
    }

    public String toString(Task task){//парсинг задач в строку
        int id = task.getId();
        TaskTypes taskType = TaskTypes.TASK;
        Status status = task.getStatus();
        LocalDateTime startTime = task.getStartTime();
        Integer duration = task.getDuration();
        String name = task.getName();
        String description = task.getDescription();
        int epicId ;

        if (task.getClass().equals(Epic.class)) {
            taskType = TaskTypes.EPIC;
        } else if (task.getClass().equals(Subtask.class)) {
            taskType = TaskTypes.SUBTASK;
            Subtask subtask = (Subtask) task;
            epicId = subtask.getEpicId();
            return ""  + id + "," + taskType + "," + name + "," + status + "," + description
                    + "," + startTime + "," + duration +  "," + epicId + "\n";
        }
        return "" + id + "," + taskType + "," + name + "," + status + "," + description + "," + startTime
                + "," + duration + "\n";
    }

    public FileBackedTasksManager loadFromFile(File file) {//восстановление данных
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try {
            ArrayList<String> listTasks = new ArrayList<>(Files.readAllLines(file.toPath()));
            for (int i = 1; i <= listTasks.size(); i++) {
                if(!listTasks.get(i).isEmpty()){
                    taskDistribution(fromString(listTasks.get(i)));
                } else {
                    //if(!listTasks.get(i).isEmpty()){
                        HistoryManager historyManager = getHistoryManager();
                        List<Integer> idTasks = historyFromString(listTasks.get(listTasks.size()-1));
                        i = listTasks.size();
                        for (Integer idTask : idTasks) {
                            Task task = searchTask(idTask);
                            if (task != null){
                                historyManager.add(task);
                            }
                        }
                    //}
                }
            }
        }catch (IOException e){
            throw new ManagerSaveException("Ошибка чтения!");
        }
        return fileBackedTasksManager;
    }

    public Task searchTask (int id){// поиск задач
        HashMap<Integer, Task> mapTask = getSaveTask();
        HashMap<Integer, Epic> mapEpic = getSaveEpic();
        HashMap<Integer, Subtask> mapSubtask = getSaveSubtask();
        if(mapTask.containsKey(id)){
            return mapTask.get(id);
        }else if(mapEpic.containsKey(id)){
            return mapEpic.get(id);
        }else{
            return mapSubtask.get(id);
        }
    }

    public void taskDistribution (Task task){// распределение задач
        if (task.getClass() == Task.class){
            addTask(task);
        }else if (task.getClass() == Epic.class){
            addEpic((Epic) task);
        }else{
            addSubtask((Subtask) task);
        }
    }

    public Task fromString (String value){//парсинг строки в задачу
        String[] split = value.split(",");
        if(split[1].equals(String.valueOf(TaskTypes.TASK))){
            return new Task(Integer.parseInt(split[0]),split[2],Status.valueOf(split[3]), split[4],
                    split[5], Integer.parseInt(split[6]));
        } else if (split[1].equals(String.valueOf(TaskTypes.EPIC))) {
            return new Epic(Integer.parseInt(split[0]),split[2],Status.valueOf(split[3]), split[4],
                    split[5], Integer.parseInt(split[6]));
        }else {
            return new Subtask(Integer.parseInt(split[0]),split[2],Status.valueOf(split[3]),
                    split[4], split[5], Integer.parseInt(split[6]), Integer.parseInt(split[7]));
        }
    }

    static List<Integer> historyFromString(String value){//парсинг строки в id задач
        String[] split = value.split(",");
        ArrayList<Integer> idsTasks = new ArrayList<>();
        if(!value.isEmpty()){
            for (String s : split) {
                idsTasks.add(Integer.parseInt(s));
            }
        }
        return idsTasks;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        Subtask s = super.createSubtask(subtask);
        save();
        return s;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic e = super.createEpic(epic);
        save();
        return e;
    }

    @Override
    public Task createTask(Task task) {
        Task t = super.createTask(task);
        save();
        return t;
    }

    @Override
    public Task getTaskById(int id) {
        Task t = super.getTaskById(id);
        save();
        return t;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic e = super.getEpicById(id);
        save();
        return e;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask s = super.getSubtaskById(id);
        save();
        return s;
    }

    @Override
    public void deleteTaskByID(int id) {
        super.deleteTaskByID(id);
        save();
    }

    @Override
    public void deleteEpicByID(int id) {
        super.deleteEpicByID(id);
        save();
    }

    @Override
    public void deleteSubtaskByID(int id) {
        super.deleteSubtaskByID(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    public static void main(String[] args) {
        File file = new File("resourses\\resours.csv");


        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
/*
        Task task1 = new Task("Task 1", "Опиание задчачи", "03.01.2023, 00:05", 5);
        Task task2 = new Task("Task 2", "Опиание задчачи");

        Epic epic1 = new Epic("Epic 1", "Опиание задчачи Epic1");
        Epic epic2 = new Epic("Задача 2", "Опиание задчачи Epic2");
        task1 = fileBackedTasksManager.createTask(task1);
        task2 = fileBackedTasksManager.createTask(task2);

//        System.out.println(task1.getStartTime());
//        System.out.println(task1.getEndTime());
//        System.out.println("-------------------");
//        System.out.println(fileBackedTasksManager.getTaskById(task1.getId()));



        epic1 = fileBackedTasksManager.createEpic(epic1);
        epic2 = fileBackedTasksManager.createEpic(epic2);

        Subtask subtask1 = new Subtask("Sub 1", "Опиание задчачи Epic1",
                "03.03.2023, 00:20" , 5 ,epic1.getId());
        Subtask subtask2 = new Subtask("Sub 2", "Опиание задчачи Epic1",
                "03.03.2023, 00:10" , 5 , epic1.getId());
        Subtask subtask3 = new Subtask("Sub 3", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask4 = new Subtask("Sub 4", "Опиание задчачи Epic1",
                "04.03.2023, 00:11" , 5 , epic1.getId());

        subtask1 = fileBackedTasksManager.createSubtask(subtask1);
        subtask2 = fileBackedTasksManager.createSubtask(subtask2);
        subtask3 = fileBackedTasksManager.createSubtask(subtask3);
        subtask4 = fileBackedTasksManager.createSubtask(subtask4);

//        System.out.println(epic1.getStartTime());
//        System.out.println(epic1.getEndTime());
        //System.out.println("-------------------");
        //System.out.println(fileBackedTasksManager.getEpicById(epic1.getId()));

//        System.out.println(subtask1.getStartTime());
//        System.out.println(subtask1.getEndTime());
        //System.out.println("-------------------");
        //System.out.println(fileBackedTasksManager.getSubtaskById((subtask1.getId())));
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(fileBackedTasksManager.getPriorityTasks());
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");



        fileBackedTasksManager.save();

//        System.out.println(fileBackedTasksManager.getSaveTask());
//        System.out.println(fileBackedTasksManager.getSaveEpic());
//        System.out.println(fileBackedTasksManager.getSaveSubtask());
//        System.out.println("\n");
//        System.out.println(fileBackedTasksManager.getHistory() + "\n");
*/
        Task task1 = new Task("Task 1", "Опиание задчачи", "03.01.2023, 00:05", 5);
        fileBackedTasksManager.createTask(task1);
        //fileBackedTasksManager.getTaskById(task1.getId());
        fileBackedTasksManager.loadFromFile(file);
        System.out.println(fileBackedTasksManager.getSaveTask());
        //System.out.println(fileBackedTasksManager.getSaveEpic());
        //System.out.println(fileBackedTasksManager.getSaveSubtask());


/*
        fileBackedTasksManager.getTaskById(task1.getId());
        fileBackedTasksManager.getEpicById(epic1.getId());
        fileBackedTasksManager.getSubtaskById(subtask1.getId());
        fileBackedTasksManager.getSubtaskById(subtask2.getId());
        fileBackedTasksManager.getSubtaskById(subtask3.getId());
        fileBackedTasksManager.getTaskById(task1.getId());
        fileBackedTasksManager.getSubtaskById(subtask1.getId());
        fileBackedTasksManager.getEpicById(epic1.getId());

        fileBackedTasksManager.getEpicById(epic2.getId());
        fileBackedTasksManager.getTaskById(task2.getId());

        System.out.println(fileBackedTasksManager.getHistory() + "\n");
        //fileBackedTasksManager.deleteEpicByID(epic1.getId());
        System.out.println(fileBackedTasksManager.getHistory());

        fileBackedTasksManager.deleteTaskByID(task1.getId());
        System.out.println(fileBackedTasksManager.getHistory());
*/

    }
}
