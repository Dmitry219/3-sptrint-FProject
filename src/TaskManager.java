import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> saveTask = new HashMap<>();
    HashMap<Integer, Subtask> saveSubtask = new HashMap<>();
    HashMap<Integer, Epic> saveEpic = new HashMap<>();
    private int generateId = 0;

    public Subtask createSubtask(Subtask subtask){//Создание Subtask
        int id = generateId();
        int epicId = subtask.getEpicId();
        subtask.setId(id);
        saveSubtask.put(id, subtask);
        saveEpic.get(epicId).setIdSubtask(subtask);
        return subtask;
    }

    public Epic createEpic(Epic epic){//Создание Epic
        int id = generateId();
        epic.setId(id);
        saveEpic.put(id, epic);
        return epic;
    }

    public Task createTask(Task task){//Создание Task
        int id = generateId();
        task.setId(id);
        saveTask.put(id, task);
        return task;
    }

    public ArrayList<Task> getAllTasks(){//Получение списка всех Task
        return new ArrayList(saveTask.values());
    }

    public ArrayList<Task> getAllEpic(){//Получение списка всех Epic
        return new ArrayList(saveEpic.values());
    }

    public ArrayList<Task> getAllSubtask(){//Получение списка всех Subtask
        return new ArrayList(saveSubtask.values());
    }

    public Task getTaskById (int id){//Получение по идентификатору Task
        return saveTask.get(id);
    }

    public Epic getEpicById (int id){//Получение по идентификатору Epic
        return saveEpic.get(id);
    }

    public Subtask getSubtaskById (int id){//Получение по идентификатору Subtask
        return saveSubtask.get(id);
    }

    public void successAllTasks (){//Удаление всех Task
        saveTask.clear();
    }

    public void successAllEpic (){//Удаление всех Epic
        saveEpic.clear();
    }

    public void successAllSubtask (){//Удаление всех Subtask
        saveSubtask.clear();
    }

    public void deleteTaskByID (int id){//удалить по идентификатору Task
        saveTask.remove(id);
    }

    public void deleteEpicByID (int id){//удалить по идентификатору Epic
        ArrayList<Integer> listSub  = saveEpic.get(id).getSubtaskIDs();
        for(int i : listSub){
            saveSubtask.remove(i);
        }

        saveEpic.remove(id);
    }

    public void deleteSubtaskByID (int id){//Удалить по идентификатору Subtask
        if (!saveSubtask.containsKey(id)) {
            return;
        }

        int epicId = saveSubtask.get(id).getEpicId();

        saveEpic.get(epicId).getSubtaskIDs().remove((Integer) id);
        saveSubtask.remove(id);
    }

    public void updateTask(Task task) { //Обновление Task

        if (!saveTask.containsKey(task.getId())) {
            return;
        }

        saveTask.put(task.getId(), task);
    }

    public void updateEpicAndSubtask(Epic epic) { //Обновление Epic и Subtask

        if (!saveEpic.containsKey(epic.getId())){
            return;
        }

        int newCount = 0;
        int doneCount = 0;

        ArrayList<Integer> listSub  = saveEpic.get(epic.getId()).getSubtaskIDs();
        for(Integer i : listSub){
            for(Subtask s : saveSubtask.values()){
                if(i == s.getId()){
                    if (s.getStatus() == Status.NEW){
                        newCount++;
                    }else if(s.getStatus() == Status.DONE){
                        doneCount++;
                    }
                }

            }
        }

        if (newCount == listSub.size()){
            epic.setStatus(Status.NEW);
        } else if (doneCount == listSub.size()) {
            epic.setStatus(Status.DONE);
        }else{
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    protected void GetAListOfAllSubtasksOfASpecificEpic (Epic epic){//Получение списка всех подзадач определённого Epic
        if (!saveEpic.containsKey(epic.getId())){
            return;
        }

        ArrayList<Integer> listSub  = saveEpic.get(epic.getId()).getSubtaskIDs();
        for(Integer i : listSub) {
            for (Integer j : saveSubtask.keySet()) {
                if(i == j){
                    System.out.println(saveSubtask.get(j));
                }
            }
        }
    }

    private int generateId(){//Генерация id
        return ++generateId;
    }
}
