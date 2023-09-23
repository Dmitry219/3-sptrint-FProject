package managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    File file = new File("resourses\\resours.csv");

    @Override
    FileBackedTasksManager setUp(){
        taskManager = new FileBackedTasksManager(file);
        return taskManager;
    }

    @Test
    public void testFileUpload (){ //тест загрузка файла
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.loadFromFile(file);

        Assertions.assertEquals(task1.getClass(), taskManager.getSaveTask().get(task1.getId()).getClass());
        Assertions.assertEquals(epic1.getClass(), taskManager.getSaveEpic().get(epic1.getId()).getClass());
        Assertions.assertEquals(subtask1.getClass(), taskManager.getSaveSubtask().get(subtask1.getId()).getClass());
    }
}