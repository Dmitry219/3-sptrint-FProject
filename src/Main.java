import managers.HistoryManager;
import managers.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        //TaskManager taskManager = Managers.getDefault();
/*

        Task task1 = new Task("Задача 1", "Опиание задчачи");
        Task task2 = new Task("Задача 2", "Опиание задчачи");

        Epic epic1 = new Epic("Задача 1", "Опиание задчачи Epic1");
        Epic epic2 = new Epic("Задача 2", "Опиание задчачи Epic2");

        task1 = taskManager.createTask(task1);
        task1.setName("Задача 1.2");

        task2 = taskManager.createTask(task2);

        epic1 = taskManager.createEpic(epic1);
        epic2 = taskManager.createEpic(epic2);

        Subtask subtask1 = new Subtask("Задача 1", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask2 = new Subtask("Задача 2", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask3 = new Subtask("Задача 3", "Опиание задчачи Epic1", epic1.getId());



        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        subtask3 = taskManager.createSubtask(subtask3);


        //Тестирование возвращения истории
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getTaskById(task1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getEpicById(epic1.getId());

        taskManager.getEpicById(epic2.getId());

        System.out.println(taskManager.getHistory() + "\n");
        taskManager.deleteEpicByID(epic1.getId());
        System.out.println(taskManager.getHistory());

        taskManager.deleteTaskByID(task1.getId());
        System.out.println(taskManager.getHistory());

 */
    }
}
