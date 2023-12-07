public class Main {

    public static void main(String[] args) {
        ///// creating objects /////
        Task task1 = new Task("task1", "my first task", 1, "NEW");
        Task task2 = new Task("task2", "my second task", 2, "NEW");

        Subtask subtask1 = new Subtask("subtask1", "my first subtask", 1, "NEW", 1);
        Subtask subtask2 = new Subtask("subtask2", "my second subtask", 2, "NEW", 1);
        Epic epic1 = new Epic("epic1", "my first epic", 1, "NEW",
                new Integer[]{subtask1.getId(), subtask2.getId()});

        Subtask subtask3 = new Subtask("subtask3", "my third subtask", 3, "NEW", 2);
        Epic epic2 = new Epic("epic2", "my second epic", 2, "NEW",
                new Integer[]{subtask3.getId()});

        ///// creating manager & adding all objects to it /////
        Manager billGates = new Manager();
        billGates.addTask(task1);
        billGates.addTask(task2);
        billGates.addEpic(epic1);
        billGates.addSubtask(subtask1);
        billGates.addSubtask(subtask2);
        billGates.addEpic(epic2);
        billGates.addSubtask(subtask3);

        ///// printing objects using manager /////
        System.out.println(billGates.getAllTasks());
        System.out.println(billGates.getAllEpics());
        System.out.println(billGates.getAllSubtasks());
        System.out.println("////////////////////////////////////////");  // separator for check functional

        ///// changing statuses of out objects /////
        task1.setStatus("IN_PROGRESS");
        System.out.println(task1.getStatus());

        subtask3.setStatus("DONE");
        billGates.updateEpicStatus(2);  // we must call it, i.e. Epic owns only ids of subtasks,
                                                // not subtasks themself
        System.out.println(billGates.getAllEpics());
        System.out.println("////////////////////////////////////////");  // separator for check functional

        ///// removing objects from manager /////
        billGates.removeTask(2);
        billGates.removeEpic(1);
        billGates.removeSubtask(1);

        ///// final displaying data /////
        System.out.println(billGates.getAllTasks());
        System.out.println(billGates.getAllEpics());
        System.out.println(billGates.getAllSubtasks());
    }
}
