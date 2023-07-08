import managers.HistoryManager;
import managers.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

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
        Subtask subtask4 = new Subtask("Задача 4", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask5 = new Subtask("Задача 5", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask6 = new Subtask("Задача 1", "Опиание задчачи Epic2", epic2.getId());
        Subtask subtask7 = new Subtask("Задача 2", "Опиание задчачи Epic2", epic2.getId());
        Subtask subtask8 = new Subtask("Задача 3", "Опиание задчачи Epic2", epic2.getId());
        Subtask subtask9 = new Subtask("Задача 4", "Опиание задчачи Epic2", epic2.getId());
        Subtask subtask10 = new Subtask("Задача 5", "Опиание задчачи Epic2", epic2.getId());

        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        subtask3 = taskManager.createSubtask(subtask3);
        subtask4 = taskManager.createSubtask(subtask4);
        subtask5 = taskManager.createSubtask(subtask5);
        subtask6 = taskManager.createSubtask(subtask6);
        subtask7 = taskManager.createSubtask(subtask7);
        subtask8 = taskManager.createSubtask(subtask8);
        subtask9 = taskManager.createSubtask(subtask9);
        subtask10 = taskManager.createSubtask(subtask10);

        //Тестирование возвращения истории
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getSubtaskById(subtask4.getId());
        taskManager.getSubtaskById(subtask5.getId());
        taskManager.getEpicById(epic2.getId());
        taskManager.getSubtaskById(subtask6.getId());
        taskManager.getSubtaskById(subtask7.getId());
        taskManager.getSubtaskById(subtask8.getId());
        taskManager.getSubtaskById(subtask9.getId());
        taskManager.getSubtaskById(subtask10.getId());

        System.out.println(taskManager.getHistory());

    }
}
