package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> saveTask = new HashMap<>();
    private final HashMap<Integer, Subtask> saveSubtask = new HashMap<>();
    private final HashMap<Integer, Epic> saveEpic = new HashMap<>();
    private int generateId = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public Subtask createSubtask(Subtask subtask){//Создание tasks.Subtask
        int id = generateId();
        int epicId = subtask.getEpicId();
        subtask.setId(id);
        saveSubtask.put(id, subtask);
        saveEpic.get(epicId).setIdSubtask(subtask);
        return subtask;
    }

    @Override
    public Epic createEpic(Epic epic){//Создание tasks.Epic
        int id = generateId();
        epic.setId(id);
        saveEpic.put(id, epic);
        return epic;
    }

    @Override
    public Task createTask(Task task){//Создание tasks.Task
        int id = generateId();
        task.setId(id);
        saveTask.put(id, task);
        return task;
    }

    @Override
    public List<Task> getAllTasks(){//Получение списка всех tasks.Task
        return new ArrayList(saveTask.values());
    }

    @Override
    public List<Task> getAllEpic(){//Получение списка всех tasks.Epic
        return new ArrayList(saveEpic.values());
    }

    @Override
    public List<Task> getAllSubtask(){//Получение списка всех tasks.Subtask
        return new ArrayList(saveSubtask.values());
    }

    @Override
    public Task getTaskById (int id){//Получение по идентификатору tasks.Task
        historyManager.add(saveTask.get(id));
        return saveTask.get(id);
    }

    @Override
    public Epic getEpicById (int id){//Получение по идентификатору tasks.Epic
        historyManager.add(saveEpic.get(id));
        return saveEpic.get(id);
    }

    @Override
    public Subtask getSubtaskById (int id){//Получение по идентификатору tasks.Subtask
        historyManager.add(saveSubtask.get(id));
        return saveSubtask.get(id);
    }

    @Override
    public void deleteAllTasks (){//Удаление всех tasks.Task
        saveTask.clear();
    }

    @Override
    public void deleteAllEpic (){//Удаление всех tasks.Epic
        saveSubtask.clear();
        saveEpic.clear();
    }

    @Override
    public void deleteAllSubtask (){//Удаление всех tasks.Subtask
        for(Integer epic : saveEpic.keySet()){
            saveEpic.get(epic).getSubtaskIDs().clear();
        }

        saveSubtask.clear();


    }

    @Override
    public void deleteTaskByID (int id){//удалить по идентификатору Task
        saveTask.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpicByID (int id){//удалить по идентификатору Epic
        for(Integer i : saveEpic.get(id).getSubtaskIDs()){
            saveSubtask.remove(i);
            historyManager.remove(i);
        }
        saveEpic.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtaskByID (int id){//Удалить по идентификатору Subtask
        if (!saveSubtask.containsKey(id)) {
            return;
        }

        int idEpic = saveSubtask.get(id).getEpicId();

        saveEpic.get(idEpic).getSubtaskIDs().remove((Integer) id);
        saveSubtask.remove(id);
        updateEpicStatus(idEpic);
        historyManager.remove(id);
    }

    @Override
    public void updateTask(Task task) { //Обновление tasks.Task

        if (!saveTask.containsKey(task.getId())) {
            return;
        }

        saveTask.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic){//обновление Epic
        Epic newEpic = saveEpic.get(epic.getId());
        newEpic.setName(epic.getName());
        newEpic.setDescription(epic.getDescription());
    }

    @Override
    public void updateSubtask (Subtask subtask){//обновление Subtask
        int id = subtask.getId();
        int idEpic = subtask.getEpicId();
        Subtask subtask1 = saveSubtask.get(id);
        if (subtask1 == null){
            return;
        }

        Epic epic = saveEpic.get(idEpic);
        if (epic == null){
            return;
        }

        saveSubtask.put(id, subtask);
        updateEpicStatus(idEpic);
    }

    @Override
    public void updateEpicStatus(int idEpic) { //Обновление статуса Epic
        Epic epic = saveEpic.get(idEpic);
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

    @Override
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

    @Override
    public List<Task> getHistory(){
       return new ArrayList<>(historyManager.getHistory());
    }

    private int generateId(){//Генерация id
        return ++generateId;
    }
}
