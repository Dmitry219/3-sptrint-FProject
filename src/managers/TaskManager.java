package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    Subtask createSubtask(Subtask subtask);//Создание tasks.Subtask

    Epic createEpic(Epic epic);//Создание tasks.Epic

    Task createTask(Task task);//Создание tasks.Task

    List<Task> getAllTasks();//Получение списка всех tasks.Task

    List<Task> getAllEpic();//Получение списка всех tasks.Epic

    List<Task> getAllSubtask();//Получение списка всех tasks.Subtask

    Task getTaskById (int id);//Получение по идентификатору tasks.Task

    Epic getEpicById (int id);//Получение по идентификатору tasks.Epic

    Subtask getSubtaskById (int id);//Получение по идентификатору tasks.Subtask

    void deleteAllTasks ();//Удаление всех tasks.Task

    void deleteAllEpic ();//Удаление всех tasks.Epic

    void deleteAllSubtask ();//Удаление всех tasks.Subtask

    void deleteTaskByID (int id);//удалить по идентификатору tasks.Task

    void deleteEpicByID (int id);//удалить по идентификатору tasks.Epic

    void deleteSubtaskByID (int id);//Удалить по идентификатору tasks.Subtask

    void updateTask(Task task); //Обновление tasks.Task

    void updateEpic(Epic epic);//обновление Epic

    void updateSubtask (Subtask subtask);//обновление Subtask

    void updateEpicStatus(int idEpic); //Обновление статуса Epic

    void updateTimeEpic (int idEpic);//Обновление времени Epic

    void listOfAllSubtasksOfASpecificEpic(Epic epic);//Получение списка всех подзадач определённого tasks.Epic

    List<Task> getHistory();

    HashMap<Integer, Task> getSaveTask();

    HashMap<Integer, Subtask> getSaveSubtask();

    HashMap<Integer, Epic> getSaveEpic();
}
