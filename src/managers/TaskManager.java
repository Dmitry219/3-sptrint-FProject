package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {

    Subtask createSubtask(Subtask subtask);//Создание tasks.Subtask

    Epic createEpic(Epic epic);//Создание tasks.Epic

    Task createTask(Task task);//Создание tasks.Task

    ArrayList<Task> getAllTasks();//Получение списка всех tasks.Task

    ArrayList<Task> getAllEpic();//Получение списка всех tasks.Epic

    ArrayList<Task> getAllSubtask();//Получение списка всех tasks.Subtask

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


    void updateEpicStatus(int idEpic) ; //Обновление статуса Epic

    void listOfAllSubtasksOfASpecificEpic(Epic epic);//Получение списка всех подзадач определённого tasks.Epic
}
