package managers;

public class Managers {
    public static TaskManager getDefaultTask(){
        TaskManager inMemoryTaskManager = new InMemoryTaskManager();
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory(){
        HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
