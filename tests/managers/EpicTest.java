package managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class EpicTest {
    TaskManager taskManager = new InMemoryTaskManager();
    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;
    @BeforeEach
    void createTasks(){
        epic1 = taskManager.createEpic(new Epic("epic1", "1 Epic"));
        subtask1 = taskManager.createSubtask(new Subtask("subtask1", "1 Subtask",
                "01.01.2023, 00:10", 5, epic1.getId()));
        subtask2 = taskManager.createSubtask(new Subtask("subtask2", "2 Subtask",
                "01.01.2023, 00:20", 5, epic1.getId()));
    }

    @Test
    public void updateEpicStatusNew(){//тест всех подзадч со статусом new
        Assertions.assertEquals(Status.NEW, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не NEW");
    }

    @Test
    public void updateEpicStatusDone(){//тест всех подзадч со статусом done
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.DONE, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не DONE");
    }

    @Test
    public void updateEpicStatusNewDone(){//тест всех подзадч со статусом new done
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не IN_PROGRESS");
    }

    @Test
    public void updateEpicStatusInProgress(){//тест всех подзадч со статусом IN_PROGRESS
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не IN_PROGRESS");
    }

    @Test
    public void updateEpicStatusDeletingSubtaskByIdNew(){//тест всех подзадч со статусом new при удалении Subtask со статусом done
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.NEW);
        taskManager.updateEpicStatus(epic1.getId());
        taskManager.deleteSubtaskByID(subtask1.getId());
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.NEW, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не NEW");
    }

    @Test
    public void updateEpicAllDeletedSubtaskNew(){//тест при удлаении всех subtask
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.NEW);
        taskManager.updateEpicStatus(epic1.getId());
        taskManager.deleteAllSubtask();
        taskManager.updateEpicStatus(epic1.getId());

        Assertions.assertEquals(Status.NEW, taskManager.getSaveEpic().get(epic1.getId()).getStatus(),
                "Статус Epic не NEW");
    }
}
