package managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    HistoryManager inMemoryHistoryManager;
    InMemoryTaskManager taskManager;
    Task task1;
    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;

    @BeforeEach
    void createTasks(){
        taskManager = new InMemoryTaskManager();
        task1 = taskManager.createTask(new Task("task1", "1 Task", "01.01.2023, 00:00", 5));
        epic1 = taskManager.createEpic(new Epic("epic1", "1 Epic"));
        subtask1 = taskManager.createSubtask(new Subtask("subtask1", "1 Subtask",
                "01.01.2023, 00:10", 5, epic1.getId()));
        subtask2 = taskManager.createSubtask(new Subtask("subtask2", "2 Subtask",
                "01.01.2023, 00:20", 5, epic1.getId()));
        inMemoryHistoryManager = taskManager.getHistoryManager();
    }

    @Test
    void addTrue(){//тест получения истории
        inMemoryHistoryManager.add(task1);
        Assertions.assertTrue(inMemoryHistoryManager.getHistory().contains(task1), "Задача не прошла");
    }

    @Test
    void removeFalse(){//тест удаления
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(epic1);
        inMemoryHistoryManager.add(subtask1);
        inMemoryHistoryManager.add(subtask2);
        inMemoryHistoryManager.remove(task1.getId());//удаление из начала
        assertFalse(inMemoryHistoryManager.getHistory().contains(task1), "Задча не удалилась");
        inMemoryHistoryManager.remove(subtask1.getId());//удаление из середины
        assertFalse(inMemoryHistoryManager.getHistory().contains(subtask1), "Задача не удалилась");
        inMemoryHistoryManager.remove(subtask2.getId());
        assertFalse(inMemoryHistoryManager.getHistory().contains(subtask2), "Задача не удалилась");//удалние из конца
    }

    @Test
    void getHistoryTrue(){//тест получения истории
        assertTrue(inMemoryHistoryManager.getHistory().isEmpty());
        inMemoryHistoryManager.add(task1);
        assertFalse(inMemoryHistoryManager.getHistory().isEmpty());
        assertTrue(inMemoryHistoryManager.getHistory().contains(task1));
    }
}