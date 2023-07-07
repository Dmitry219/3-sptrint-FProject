package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> viewedTasks = new ArrayList<>();
    static final int LIST_SIZE = 10;
    @Override
    public void add(Task task){
        if(viewedTasks.size() == LIST_SIZE){
            viewedTasks.remove(0);
        }
        viewedTasks.add(task);
    }
    @Override
    public List<Task> getHistory(){
        return viewedTasks;
    }
}
