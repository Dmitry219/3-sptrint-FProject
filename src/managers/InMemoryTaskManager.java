package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.time.Duration;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> saveTask = new HashMap<>();
    private final Map<Integer, Subtask> saveSubtask = new HashMap<>();
    private final Map<Integer, Epic> saveEpic = new HashMap<>();
    private int generateId = 0;
    private Set<Task> priorityTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            if(o1.getStartTime() == null && o2.getStartTime() == null){
                return o1.getId().compareTo(o2.getId());
            }else if(o1.getStartTime() == null){
                return 1;
            }else if(o2.getStartTime() == null){
                return -1;
            }
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    });
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public void addTask(Task task){
        setGenerateId(task.getId());
        saveTask.put(task.getId(), task);
    }

    public void addEpic(Epic epic){
        setGenerateId(epic.getId());
        saveEpic.put(epic.getId(), epic);
    }

    public void addSubtask(Subtask subtask){
        setGenerateId(subtask.getId());
        int epicId = subtask.getEpicId();
        saveSubtask.put(subtask.getId(), subtask);
        saveEpic.get(epicId).setIdSubtask(subtask);
    }

    @Override
    public Task createTask(Task task){//Создание tasks.Task
        int id = generateId();
        task.setId(id);
        saveTask.put(id, task);
        validate(task);
        priorityTasks.add(task);
        return task;
    }

    @Override
    public Subtask createSubtask(Subtask subtask){//Создание tasks.Subtask
        int id = generateId();
        int epicId = subtask.getEpicId();
        subtask.setId(id);
        saveSubtask.put(id, subtask);
        saveEpic.get(epicId).setIdSubtask(subtask);
        updateTimeEpic(epicId);
        validate(subtask);
        priorityTasks.add(subtask);
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
        priorityTasks.remove(saveTask.get(id));
        historyManager.remove(id);
        saveTask.remove(id);
    }

    @Override
    public void deleteEpicByID (int id){//удалить по идентификатору Epic
        for(Integer i : saveEpic.get(id).getSubtaskIDs()){
            historyManager.remove(i);
            saveSubtask.remove(i);
        }
        historyManager.remove(id);
        saveEpic.remove(id);
    }

    @Override
    public void deleteSubtaskByID (int id){//Удалить по идентификатору Subtask
        if (!saveSubtask.containsKey(id)) {
            return;
        }

        int idEpic = saveSubtask.get(id).getEpicId();

        saveEpic.get(idEpic).getSubtaskIDs().remove((Integer) id);
        updateEpicStatus(idEpic);
        historyManager.remove(id);
        updateTimeEpic(idEpic);
        priorityTasks.remove(saveSubtask.get(id));
        saveSubtask.remove(id);
    }

    @Override
    public void updateTask(Task task) { //Обновление tasks.Task

        if (!saveTask.containsKey(task.getId())) {
            return;
        }

        saveTask.put(task.getId(), task);
        validate(task);
        priorityTasks.add(task);
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
        updateTimeEpic(idEpic);
        validate(subtask);
        priorityTasks.add(subtask);
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
    public void updateTimeEpic(int idEpic) {//обновление времени Epic
        Epic epic = saveEpic.get(idEpic);
        if (!saveEpic.containsKey(epic.getId())){
            return;
        }

        List<Subtask> subtasks = epic.getSubtaskIDs()
                .stream()
                .map(saveSubtask::get)
                .filter(subtask -> subtask.getStartTime() != null && subtask.getDuration() > 0)
                .collect(Collectors.toList());
        if(!subtasks.isEmpty()){
            LocalDateTime start = subtasks
                    .stream()
                    .map(subtask -> subtask.getStartTime())
                    .min(LocalDateTime::compareTo)
                    .get();

            LocalDateTime end = subtasks
                    .stream()
                    .map(subtask -> subtask.getEndTime())
                    .max(LocalDateTime::compareTo)
                    .get();
            epic.setStartTime(start);
            epic.setEndTime(end);
            saveEpic.get(idEpic).setDuration((int)Duration.between(start, end).toMinutes());
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

    public HistoryManager getHistoryManager (){
        return historyManager;
    }

    public void setGenerateId(int id) {
        if (id > generateId){
            generateId = id;
        }
    }

    private int generateId(){//Генерация id
        return ++generateId;
    }

    public Map<Integer, Task> getSaveTask() {
        return saveTask;
    }

    public Map<Integer, Subtask> getSaveSubtask() {
        return saveSubtask;
    }

    public Map<Integer, Epic> getSaveEpic() {
        return saveEpic;
    }

    public Set<Task> getPriorityTasks() {
        return priorityTasks;
    }

    private void validate(Task task){//Нейтрализация пересекаемых задач
        if (task.getStartTime() == null){
            return;
        }

        int result = priorityTasks.stream()
                .filter(it -> it.getStartTime() != null)
                .filter(it -> it.getId() != task.getId() && it.getStartTime() != task.getStartTime() && it.getDuration() != task.getDuration())
                .map(it -> {
                    if(task.getStartTime().isBefore(it.getEndTime()) && task.getEndTime().isAfter(it.getStartTime())){
                        return 1;
                    }
                    return 0;
                }).reduce(Integer::sum)
                .orElse(0);
        if(result > 0){
            throw new RuntimeException("Задача пересекается");
        }
    }
}
