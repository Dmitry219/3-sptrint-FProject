package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> saveTask = new HashMap<>();
    HashMap<Integer, Subtask> saveSubtask = new HashMap<>();
    HashMap<Integer, Epic> saveEpic = new HashMap<>();
    private int generateId = 0;

    public Subtask createSubtask(Subtask subtask){//Создание tasks.Subtask
        int id = generateId();
        int epicId = subtask.getEpicId();
        subtask.setId(id);
        saveSubtask.put(id, subtask);
        saveEpic.get(epicId).setIdSubtask(subtask);
        return subtask;
    }

    public Epic createEpic(Epic epic){//Создание tasks.Epic
        int id = generateId();
        epic.setId(id);
        saveEpic.put(id, epic);
        return epic;
    }

    public Task createTask(Task task){//Создание tasks.Task
        int id = generateId();
        task.setId(id);
        saveTask.put(id, task);
        return task;
    }

    public ArrayList<Task> getAllTasks(){//Получение списка всех tasks.Task
        return new ArrayList(saveTask.values());
    }

    public ArrayList<Task> getAllEpic(){//Получение списка всех tasks.Epic
        return new ArrayList(saveEpic.values());
    }

    public ArrayList<Task> getAllSubtask(){//Получение списка всех tasks.Subtask
        return new ArrayList(saveSubtask.values());
    }

    public Task getTaskById (int id){//Получение по идентификатору tasks.Task
        return saveTask.get(id);
    }

    public Epic getEpicById (int id){//Получение по идентификатору tasks.Epic
        return saveEpic.get(id);
    }

    public Subtask getSubtaskById (int id){//Получение по идентификатору tasks.Subtask
        return saveSubtask.get(id);
    }

    public void deleteAllTasks (){//Удаление всех tasks.Task
        saveTask.clear();
    }

    public void deleteAllEpic (){//Удаление всех tasks.Epic
        saveSubtask.clear();
        saveEpic.clear();
    }

    public void deleteAllSubtask (){//Удаление всех tasks.Subtask
        for(Integer epic : saveEpic.keySet()){
            saveEpic.get(epic).getSubtaskIDs().clear();
        }

        saveSubtask.clear();

    }

    public void deleteTaskByID (int id){//удалить по идентификатору tasks.Task
        saveTask.remove(id);
    }

    public void deleteEpicByID (int id){//удалить по идентификатору tasks.Epic
        for(Integer i : saveEpic.get(id).getSubtaskIDs()){
            saveSubtask.remove(i);
        }
        saveEpic.remove(id);
    }

    public void deleteSubtaskByID (int id){//Удалить по идентификатору tasks.Subtask
        if (!saveSubtask.containsKey(id)) {
            return;
        }

        int epicId = saveSubtask.get(id).getEpicId();

        saveEpic.get(epicId).getSubtaskIDs().remove((Integer) id);
        saveSubtask.remove(id);
    }

    public void updateTask(Task task) { //Обновление tasks.Task

        if (!saveTask.containsKey(task.getId())) {
            return;
        }

        saveTask.put(task.getId(), task);
    }

    public void updateEpicAndSubtask(Epic epic) { //Обновление tasks.Epic и tasks.Subtask

        if (!saveEpic.containsKey(epic.getId())){
            return;
        }

        int newCount = 0;
        int doneCount = 0;

        for(Integer i : epic.getSubtaskIDs()) {
            Subtask s = saveSubtask.get(i);
            switch (s.getStatus()) {
                case NEW:
                    newCount++;
                    break;
                case DONE:
                    doneCount++;
                    break;
                case IN_PROGRESS:
                    break;
            }
        }

            if (newCount == epic.getSubtaskIDs().size()){
                epic.setStatus(Status.NEW);
            } else if (doneCount == epic.getSubtaskIDs().size()) {
                epic.setStatus(Status.DONE);
            }else{
                epic.setStatus(Status.IN_PROGRESS);
            }
    }

    public void listOfAllSubtasksOfASpecificEpic(Epic epic){//Получение списка всех подзадач определённого tasks.Epic
        if (!saveEpic.containsKey(epic.getId())){
            return;
        }
        for(Subtask value : saveSubtask.values()){
            if(value.getEpicId() == epic.getId()){
                System.out.println(value);
            }
        }
    }

    private int generateId(){//Генерация id
        return ++generateId;
    }
}
