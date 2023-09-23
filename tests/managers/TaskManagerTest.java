package managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

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
    public void testEpicList(){//Epic не пустой
        Assertions.assertFalse(taskManager.getSaveEpic().isEmpty(),"Список пустой");
    }

    @Test
    public void testEpicStatusNew(){//тест всех подзадч со статусом new
        Assertions.assertEquals(Status.NEW, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не NEW");
    }

    @Test
    public void testEpicStatusDone(){//тест всех подзадч со статусом done
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.DONE, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не DONE");
    }

    @Test
    public void testEpicStatusNewDone(){//тест всех подзадч со статусом new done
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не IN_PROGRESS");
    }

    @Test
    public void testEpicStatusIn_progress(){//тест всех подзадч со статусом IN_PROGRESS
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не IN_PROGRESS");
    }

    @Test
    public void testUpdateEpicStatusWhenDeletingSubtaskWithStatusDone(){//тест всех подзадч со статусом new при удалении Subtask со статусом done
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.NEW);
        taskManager.updateEpicStatus(epic1.getId());
        taskManager.deleteSubtaskByID(subtask1.getId());
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.NEW, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не NEW");
    }

    @Test
    public void testStatusUpdateEpicWhenAllAreDeletedSubtask(){//тест при удлаении всех subtask
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.NEW);
        taskManager.updateEpicStatus(epic1.getId());
        taskManager.deleteAllSubtask();
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.NEW, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не NEW");
    }

}