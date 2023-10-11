public class CustomLinkedList {

    private Node head;
    private Node tail;
    private int length;

    public CustomLinkedList() {
    }

    public String append(String value) {
        Node nodeToAdd = new Node(value, null, null);
        if (isEmpty()) {
            this.head = nodeToAdd;
        } else {
            this.tail.next = nodeToAdd;
            nodeToAdd.prev = this.tail;
        }
        this.tail = nodeToAdd;
        this.length++;
        return nodeToAdd.value;
    }

    public String preprend(String value) {
        if (isEmpty()) {
            return this.append(value);
        }
        Node nodeToAdd = new Node(value, this.head, null);
        this.head.prev = nodeToAdd;
        this.head = nodeToAdd;
        this.length++;
        return nodeToAdd.value;
    }

    public String shift() {
        if (isEmpty()) {
            return null;
        }
        Node nodeToRemove = this.head;
        String valueToReturn = nodeToRemove.value;
        this.head = this.head.next;
        this.head.prev = null;
        this.length--;
        return valueToReturn;
    }

    public String pop() {
        if (isEmpty()) {
            return null;
        }

        Node currentNode = this.head;
        String valueToReturn = null;
        do {
            if (currentNode.next == null) {
                this.head = null;
                this.tail = null;
                return currentNode.value;
            }
            if (currentNode.next.next == null) {
                valueToReturn = currentNode.next.value;
                currentNode.next = null;
                this.tail = currentNode;
            }
            currentNode = currentNode.next;
        } while (currentNode != null);
        this.length--;
        return valueToReturn;
    }

    public String insert(int index, String value) {
        if (isEmpty() || index >= this.length) {
            return this.append(value);
        }
        if (index <= 0) {
            return this.preprend(value);
        }
        Node leader = this.traverseToIndex(index - 1);
        Node newNode = new Node(value, leader.next);
        leader.next = newNode;
        this.length++;
        return newNode.value;
    }

    public String remove(int index) {
        if (isEmpty()) {
            return null;
        }
        if (index <= 0) {
            return this.shift();
        }
        if (index >= this.length) {
            return this.pop();
        }
        Node leader = this.traverseToIndex(index - 1);
        Node toRemove = leader.next;
        leader.next = toRemove.next;
        this.length--;
        return toRemove.value;

    }

    public String get(int index) {
        Node toReturn = this.traverseToIndex(index);
        if (toReturn != null) {
            return toReturn.value;
        }
        return null;
    }

    private Node traverseToIndex(int index) {
        Node currentNode = this.head;
        for (int i = 0; i < this.length; i++) {
            if (i == index) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        Node currentNode = this.head;
        while (currentNode != null) {
            items.append(currentNode.value).append(", ");
            currentNode = currentNode.next;
        }
        return items.toString().substring(0, items.length() - 2);
    }

    static class Node {

        String value;
        Node next;
        Node prev;

        public Node() {

        }

        public Node(String value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public void setValue() {

        }

    }

}
