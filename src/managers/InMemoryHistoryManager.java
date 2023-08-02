package managers;

import tasks.Node;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList<Task> viewedTasks = new CustomLinkedList<>();
    private final Map<Integer, Node<Task>> addNewTasks = new HashMap<>();

    @Override
    public void add(Task task){
        final Node<Task> newNode = new Node<>(task);
        if (addNewTasks.containsKey(task.getId())) {
            viewedTasks.removeNode(addNewTasks.get(task.getId()));
            addNewTasks.remove(task.getId());
        }
        viewedTasks.linkLast(newNode);
        addNewTasks.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
            viewedTasks.removeNode(addNewTasks.get(id));
            addNewTasks.remove(id);
    }

    @Override
    public List<Task> getHistory(){
        return viewedTasks.getTasks();
    }

    static class CustomLinkedList<T>{
        private Node<T> head;
        private Node<T> tail;

        public void linkLast (Node<T> element) {
            element.prev = tail;
            tail = element;
            if (element.prev == null) {
                head = element;
            }else {
                Node<T> oldTail = element.prev;
                oldTail.next = element;
            }
        }
        public ArrayList<T> getTasks (){
            ArrayList<T> getTask = new ArrayList<>();
            Node<T> newElement = head;
            while(newElement.next != null){
                getTask.add(newElement.data);
                newElement = newElement.next;
            }
            getTask.add(newElement.data);
            return getTask;
        }
        public void removeNode (Node<T> node){
            if (node.prev == null && node.next == null){
                head = null;
                tail = null;
            }else if(node.prev == null){
                head = node.next;
                head.prev = null;
            }else if (node.next == null){
                tail = node.prev;
                tail.next = null;
            }else{
                // Вопрос для ревью какой ваирант лучше ?????
                //1 вариант.
//                Node<T> prevElment = node.prev;
//                Node<T> nextElment = node.next;
//                nextElment.prev = prevElment;
//                prevElment.next = nextElment;
                //2 вариант.
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
    }

}
