package managers;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @Override
    InMemoryTaskManager setUp(){
        taskManager = new InMemoryTaskManager();
        return taskManager;
    }
}
