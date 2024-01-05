package manager;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;

    private Map<Integer, Node> idNode;

    public InMemoryHistoryManager() {
        head = null;
        tail = null;
        idNode = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (idNode.containsKey(task.getId())) {  // эта задача уже просматривалась => удаляем ее из истории просмотров
            removeNodeFromList(idNode.get(task.getId()));
            idNode.remove(task.getId());
        }

        while (idNode.size() >= 10) {  // ограничение на размер истории просмотров
            idNode.remove(head.task.getId());
            removeNodeFromList(head);
        }

        linkLast(task);
        idNode.put(task.getId(), tail);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        int size_before = getTasks().size();

        Node nodeToDelete = idNode.get(id);
        removeNodeFromList(nodeToDelete);
        idNode.remove(id);


        int size_after = getTasks().size();
    }

    private void linkLast(Task task) {
        if (idNode.isEmpty()) {
            head = new Node(task);
            tail = head;
            return;
        }
        if (idNode.size() == 1) {
            tail = new Node(task);
            head.next = tail;
            tail.prev = head;
            return;
        }

        // size >= 2, i.e. head & tail are on place
        tail.next = new Node(task);
        tail.next.prev = tail;
        tail = tail.next;
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        Node node = head;
        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }

        return tasks;  // size = 7, хотя size(hashMap) = 6
    }

    private void removeNodeFromList(Node node) {
        if (node == null || !idNode.containsKey(node.task.getId())) {
            return;
        }
        if (node == head) {
            if (head.next != null) {  // в списке как минимум 2 элемента
                head.next.prev = null;
                head = head.next;
            } else {  //  в списке 1 элемент
                head = null;
                tail = null;
            }
            return;
        }
        if (node == tail) {
            if (tail.prev != null) {  // в списке есть хотя бы 2 элемента
                tail.prev.next = null;
                tail = tail.prev;
            } else {  // в списке 1 элемент
                tail = null;
                head = null;
            }
            return;
        }

        // node где-то в середине списка; в списке >= 3 элементов
        Node node_prev = node.prev;
        Node node_next = node.next;
        node_prev.next = node_next;
        node_next.prev = node_prev;
    }

    private static class Node {
        public Node prev;
        public Task task;
        public Node next;

        public Node(Task task) {
            prev = null;
            this.task = task;
            next = null;
        }
    }
}
