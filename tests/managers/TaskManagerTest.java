package managers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import org.junit.jupiter.api.function.Executable;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;
    Task task1;
    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;
    abstract T setUp();

    @BeforeEach
    void createManager(){
        taskManager = setUp();
    }

    @BeforeEach
    void createTasks(){
        task1 = taskManager.createTask(new Task("task1", "1 Task", "01.01.2023, 00:00", 5));
        epic1 = taskManager.createEpic(new Epic("epic1", "1 Epic"));
        subtask1 = taskManager.createSubtask(new Subtask("subtask1", "1 Subtask",
                "01.01.2023, 00:10", 5, epic1.getId()));
        subtask2 = taskManager.createSubtask(new Subtask("subtask2", "2 Subtask",
                "01.01.2023, 00:20", 5, epic1.getId()));
    }

    @Test
    public void createEpicListIsNotEmpty(){//Epic не пустой
        assertFalse(taskManager.getSaveEpic().isEmpty(),"Список пустой");
    }

    @Test
    public void shouldGetTaskById(){//тест возвращение Task по id
        assertEquals(task1, taskManager.getTaskById(task1.getId()), "Задачи не равны");
    }

    @Test
    public void shouldGetEpicById(){//тест возвращение Epic по id
        assertEquals(epic1, taskManager.getEpicById(epic1.getId()), "Epic не равны");
    }

    @Test
    public void shouldGetSubtaskById(){//тест возвращение Subtask по id
        assertEquals(subtask1, taskManager.getSubtaskById(subtask1.getId()), "Subtask не равны");
    }

    @Test
    public void shouldDeleteAllTasks(){//тест удаления всех Task
        taskManager.deleteAllTasks();
        assertTrue(taskManager.getSaveTask().isEmpty(), "Список Task не удалился");
    }

    @Test
    public void shouldDeleteAllEpic(){//тест удаления всех Epic
        taskManager.deleteAllEpic();
        assertTrue(taskManager.getSaveEpic().isEmpty(), "Список Epic не удалиля");
    }

    @Test
    public void shouldDeleteAllSubtask(){//тест удаления всех Subtask
        taskManager.deleteAllSubtask();
        assertTrue(taskManager.getSaveSubtask().isEmpty(), "Список Subtask не удалился");
    }

    @Test
    public void shouldDeleteTaskByID(){//тест удаление Task по id
        taskManager.deleteTaskByID(task1.getId());
        assertFalse(taskManager.getSaveTask().containsKey(task1.getId()), "Task не удлалилась");
    }

    @Test
    public void shouldDeleteEpicByID(){//тест удаление Epic по id
        taskManager.deleteEpicByID(epic1.getId());
        assertFalse(taskManager.getSaveEpic().containsKey(epic1.getId()), "Epic не удлалилась");
    }

    @Test
    public void shouldDeleteSubtaskByID(){//тест удаление Subtask по id
        taskManager.deleteSubtaskByID(subtask1.getId());
        assertFalse(taskManager.getSaveSubtask().containsKey(subtask1.getId()), "Subtask не удлалилась");
    }

    @Test
    public void shouldUpdateTask(){//тест обновление Task
        task1.setName("Обновление имени");
        taskManager.updateTask(task1);
        assertEquals(task1,taskManager.getTaskById(task1.getId()), "Task не обновилась");
    }

    @Test
    public void shouldUpdateEpic(){//тест обновление Epic
        epic1.setName("Обновление имени");
        taskManager.updateEpic(epic1);
        assertEquals(epic1,taskManager.getEpicById(epic1.getId()), "Epic не обновилась");
    }

    @Test
    public void shouldUpdateSubtask(){//тест обновление Subtask
        subtask1.setName("Обновление имени");
        taskManager.updateSubtask(subtask1);
        assertEquals(subtask1,taskManager.getSubtaskById(subtask1.getId()), "Subtask не обновилась");
    }

    @Test
    public void shouldUpdateTimeEpic(){//тест обновление времени Epic
        assertEquals(subtask1.getStartTime(),taskManager.getEpicById(epic1.getId()).getStartTime(), "Время старта Epic не обновилась");
        assertEquals(subtask2.getEndTime(),taskManager.getEpicById(epic1.getId()).getEndTime(), "Время конца Epic не обновилась");
    }

    @Test
    public void shouldListOfAllSubtasksOfASpecificEpic(){//тест получение списка всех подзадач определённого Epic
        assertTrue(taskManager.getEpicById(epic1.getId()).getSubtaskIDs().contains(subtask1.getId()), "Subtask1 нет в списке Epic1");
        assertTrue(taskManager.getEpicById(epic1.getId()).getSubtaskIDs().contains(subtask2.getId()), "Subtask2 нет в списке Epic1");

    }

    @Test
    public void shouldValidate() {//тест нейтрализации пересекаемых задач
        subtask1.setDuration(15);
        final RuntimeException exception = assertThrows(
                RuntimeException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        taskManager.updateSubtask(subtask1);
                    }
                });
        assertEquals("Задача пересекается", exception.getMessage());
    }

}