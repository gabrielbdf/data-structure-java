import java.util.Iterator;

public class CustomHashTable implements Iterable<CustomHashTable.Node> {

    private NodeRow[] data;
    private int size;

    public CustomHashTable() {
        this.data = new NodeRow[5];
    }

    public int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash + key.charAt(i)) % this.data.length;
        }
        return hash;
    }

    public Node set(String key, String value) {
        int address = this.hash(key);
        Node node = new Node(key, value);
        NodeRow nodeRow;
        if (this.data[address] != null) {
            nodeRow = this.data[address];
            nodeRow.addNode(node);
        } else {
            nodeRow = new NodeRow(address, node);
        }
        this.data[address] = nodeRow;
        this.size++;
        return node;
    }

    public String get(String key) {
        Node node = this.getNode(key);
        return node != null ? node.value : null;
    }

    public Node getNode(String key) {
        int address = this.hash(key);
        NodeRow nodeRowAddress = this.data[address];
        if (nodeRowAddress != null) {
            for (int i = 0; i < nodeRowAddress.nodes.length; i++) {
                if (nodeRowAddress.nodes[i].key.equals(key)) {
                    return nodeRowAddress.nodes[i];
                }
            }
        }
        return null;
    }

    public int remove(String key) {
        int address = this.hash(key);
        NodeRow nodeRowAddress = this.data[address];
        if (nodeRowAddress != null) {
            for (int i = 0; i < nodeRowAddress.nodes.length; i++) {
                if (nodeRowAddress.nodes[i].key.equals(key)) {
                    nodeRowAddress.removeNode(i);
                    if (nodeRowAddress.size == 0) {
                        this.data[address] = null;
                    } else {
                        this.data[address] = nodeRowAddress;
                    }
                    this.size--;
                    return 1;
                }
            }
        }

        return 0;
    }

    public int size() {
        return this.size;
    }

    public String[] keys() {
        String[] allKeys = new String[this.size];
        int currentIndex = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.data[i] != null) {
                for (int j = 0; j < this.data[i].size; j++) {
                    String currentKey = this.data[i].nodes[j].key;
                    if (currentKey != null) {
                        allKeys[currentIndex] = currentKey;
                        currentIndex++;
                    }
                }
            }
        }
        return allKeys;
    }

    static class Node {

        String key;
        String value;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    static class NodeRow {
        int address;
        int size;
        Node[] nodes;

        NodeRow(int address, Node node) {
            this.size = 1;
            this.address = address;
            this.nodes = new Node[] { node };
        }

        public void addNode(Node node) {
            int newCapacity = this.nodes.length + 1;
            Node[] newNodes = new Node[newCapacity];
            System.arraycopy(this.nodes, 0, newNodes, 0, this.nodes.length);
            newNodes[newCapacity - 1] = node;
            this.size++;
            this.nodes = newNodes;
        }

        public void removeNode(int index) {
            int newCapacity = this.nodes.length - 1;
            Node[] newNodes = new Node[newCapacity];
            for (int i = index; i < this.nodes.length - 1; i++) {
                this.nodes[i] = this.nodes[i + 1];
            }
            for (int i = 0; i < newCapacity; i++) {
                newNodes[i] = this.nodes[i];
            }
            this.size = newCapacity;
            this.nodes = newNodes;
        }
    }

    @Override
    public Iterator<CustomHashTable.Node> iterator() {
        return new CustomHashTableIterator(this.keys());
    }

    private class CustomHashTableIterator implements Iterator<CustomHashTable.Node> {

        private int currentIndex;
        private String[] keys;

        CustomHashTableIterator(String[] keys) {
            this.keys = keys;
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return this.currentIndex < size;
        }

        @Override
        public CustomHashTable.Node next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return getNode(this.keys[currentIndex++]);
        }

    }

}