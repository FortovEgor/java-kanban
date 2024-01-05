package manager;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;
    private int size;

    private Map<Integer, Node> idNode;

    public InMemoryHistoryManager() {
        head = null;
        tail = null;
        size = 0;
        idNode = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        while (size >= 10) {  // размер истории просмотров <= 10
            removeNode(head);
            --size;
        }
        if (idNode.containsKey(task.getId())) {  // эта задача уже просматривалась => удаляем ее из истории просмотров
            removeNode(idNode.get(task.getId()));
        }

        linkLast(task);
        if (size == 1) {
            idNode.put(task.getId(), head);
        } else {
            idNode.put(task.getId(), tail);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        Node nodeToDelete = idNode.get(id);
        removeNode(nodeToDelete);
        idNode.remove(id);
    }

    private void linkLast(Task task) {
        if (size == 0) {
            head = new Node(task);
            tail = head;
            ++size;
            return;
        }
        if (size == 1) {
            tail = new Node(task);
            head.next = tail;
            tail.prev = head;
            ++size;
            return;
        }

        // size >= 2, i.e. head & tail are on place
        tail.next = new Node(task);
        tail.next.prev = tail;
        tail = tail.next;
        ++size;
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        Node node = head;
        while (node != null) {
            tasks.add(node.task);
            node = node.next;
        }

        return tasks;
    }

    private void removeNode(Node node) {
        // assume that node либо == null, либо точно содержится в нашем списке
        if (node == null) {
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
            --size;
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
            --size;
            return;
        }

        // node где-то в середине списка; в списке >= 3 элементов
        Node node_prev = node.prev;
        Node node_next = node.next;
        node_prev.next = node_next;
        node_next.prev = node_prev;
        --size;
    }

    static class Node {
        public Node prev;
        public Task task;
        public Node next;

        public Node(Task task) {
            this.prev = null;
            this.task = task;
            this.next = null;
        }
    }
}
