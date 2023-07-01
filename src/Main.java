import managers.TaskManager;
import tasks.Epic;
import managers.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Опиание задчачи");
        Task task2 = new Task("Задача 2", "Опиание задчачи");

        Epic epic1 = new Epic("Задача 1", "Опиание задчачи Epic1");
        Epic epic2 = new Epic("Задача 2", "Опиание задчачи Epic2");

        task1 = taskManager.createTask(task1);
        task1.setName("Задача 1.2");

        task2 = taskManager.createTask(task2);

        epic1 = taskManager.createEpic(epic1);
        epic2 = taskManager.createEpic(epic2);

        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println(taskManager.getEpicById(epic2.getId()));
        System.out.println("\n" + taskManager.getAllEpic() + "\n");

        Subtask subtask1 = new Subtask("Задача 1", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask2 = new Subtask("Задача 2", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask3 = new Subtask("Задача 3", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask4 = new Subtask("Задача 4", "Опиание задчачи Epic1", epic1.getId());
        Subtask subtask5 = new Subtask("Задача 5", "Опиание задчачи Epic1", epic1.getId());

        Subtask subtask6 = new Subtask("Задача 1", "Опиание задчачи Epic2",
                 epic2.getId());

        subtask1 = taskManager.createSubtask(subtask1);
        subtask2 = taskManager.createSubtask(subtask2);
        subtask3 = taskManager.createSubtask(subtask3);
        subtask4 = taskManager.createSubtask(subtask4);
        subtask5 = taskManager.createSubtask(subtask5);
        subtask6 = taskManager.createSubtask(subtask6);

        System.out.println("2.1");

        System.out.println("\n" + taskManager.getAllTasks() + "\n");
        System.out.println("\n" + taskManager.getAllEpic() + "\n");
        System.out.println("\n" + taskManager.getAllSubtask() + "\n");

        System.out.println("2.2");
        taskManager.successAllTasks();
        System.out.println("\n" + taskManager.getAllTasks() + "\n");
        taskManager.successAllEpic();
        System.out.println("\n" + taskManager.getAllEpic() + "\n");
        taskManager.successAllSubtask();
        System.out.println("\n" + taskManager.getAllSubtask() + "\n");
        System.out.println("\n" + taskManager.getAllEpic() + "\n");


        System.out.println("2.3");

        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println(taskManager.getTaskById(task2.getId()));

        System.out.println(taskManager.getEpicById(epic1.getId()));
        System.out.println(taskManager.getEpicById(epic2.getId()));

        System.out.println(taskManager.getSubtaskById(subtask1.getId()));
        System.out.println(taskManager.getSubtaskById(subtask2.getId()));
        System.out.println(taskManager.getSubtaskById(subtask3.getId()));
        System.out.println(taskManager.getSubtaskById(subtask4.getId()));
        System.out.println(taskManager.getSubtaskById(subtask5.getId()));
        System.out.println(taskManager.getSubtaskById(subtask6.getId()));

        System.out.println("2.5");

        task1.setName("AAAAAAAAAAAAAAA");
        task1.setDescription("WWWWWWWWWWWW");
        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        System.out.println("\n" + taskManager.getAllTasks() + "\n");

        System.out.println("2.6");
        System.out.println("\n" + taskManager.getAllSubtask() + "\n");
        taskManager.deleteEpicByID(epic1.getId());
        System.out.println("\n" + taskManager.getAllEpic() + "\n");
        System.out.println("\n" + taskManager.getAllSubtask() + "\n");
        taskManager.deleteSubtaskByID(20);
        taskManager.deleteSubtaskByID(subtask2.getId());
        System.out.println("\n" + taskManager.getAllEpic() + "\n");
        System.out.println("\n" + taskManager.getAllSubtask() + "\n");
        taskManager.deleteTaskByID(task2.getId());
        System.out.println("\n" + taskManager.getAllTasks() + "\n");

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);
        subtask4.setStatus(Status.DONE);
        subtask5.setStatus(Status.DONE);
        subtask6.setStatus(Status.DONE);

        System.out.println("\n" + taskManager.getAllEpic() + "\n");
        epic1.setName("EEEEEEEEEEEEEEEEEEE");
        epic1.setDescription("RRRRRRRRRRRRR");
        System.out.println("\n" + taskManager.getAllEpic() + "\n");
        taskManager.updateEpicAndSubtask(epic1);
        System.out.println("\n" + taskManager.getAllEpic() + "\n");


        //taskManager.listOfAllSubtasksOfASpecificEpic(epic1);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.NEW);
        subtask3.setStatus(Status.DONE);
        subtask4.setStatus(Status.DONE);
        subtask5.setStatus(Status.DONE);
        subtask6.setStatus(Status.DONE);
        taskManager.updateEpicAndSubtask(epic1);
        System.out.println("\n" + taskManager.getAllEpic() + "\n");
        subtask1.setStatus(managers.Status.IN_PROGRESS);
        subtask2.setStatus(managers.Status.NEW);
        subtask3.setStatus(managers.Status.DONE);
        subtask4.setStatus(managers.Status.DONE);
        subtask5.setStatus(managers.Status.DONE);
        subtask6.setStatus(managers.Status.DONE);
        taskManager.updateEpicAndSubtask(epic1);
        System.out.println("\n" + taskManager.getAllEpic() + "\n");

        taskManager.deleteSubtaskByID(subtask2.getId());
        taskManager.updateEpicAndSubtask(epic1);
        System.out.println("\n" + taskManager.getAllEpic() + "\n");

        Subtask subtask8 = new Subtask("Задача 5", "Опиание задчачи Epic1", epic1.getId());
        subtask8 = taskManager.createSubtask(subtask8);
        taskManager.updateEpicAndSubtask(epic1);
        System.out.println("\n" + taskManager.getAllEpic() + "\n");
    }
}
