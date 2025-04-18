public class LinkedList {
    private Node front, back;
    private int size;

    public LinkedList() {

    }

    public LinkedList(LinkedList list) {
        Node current = list.getFront();
        while (current != null) {
            if (size == 0)
                front = back = new Node(current.getElement());
            else
                addLast(new Node(current.getElement()));
            current = current.getNext();
            size++;
        }
    }

    public Edge getFirst() {
        if (size == 0)
            return null;
        return front.getElement();
    }

    public Edge getLast() {
        if (size == 0)
            return null;
        return back.getElement();
    }

    public void addFirst(Node node) {
        if (size == 0)
            front = back = node;
        else {
            node.setNext(front);
            front = node;
        }
        size++;
    }

    public void addLast(Node node) {
        add(node);
    }

    public void add(Node node) {
        if (size == 0)
            front = back = node;
        else {
            back.setNext(node);
            back = node;
        }
        size++;
    }

    public void add(Node node, int index) {
        if (index == 0)
            addFirst(node);
        else if (index == size)
            add(node);
        else {
            Node current = front;
            for (int i = 0; i < index - 1; i++)
                current = current.getNext();
            node.setNext(current.getNext());
            current.setNext(node);
            size++;
        }
    }

    public boolean removeFirst() {
        if (size == 0)
            return false;
        else if (size == 1) {
            front = back = null;
            size--;
            return true;
        }
        front = front.getNext();
        size--;
        return true;
    }

    public boolean removeLast() {
        if (size == 0)
            return false;
        else if (size == 1) {
            front = back = null;
            size--;
            return true;
        }

        Node current = front;
        for (int i = 0; i < size - 2; i++)
            current = current.getNext();
        current.setNext(null);
        back = current;
        size--;
        return true;
    }

    public boolean remove(int index) {
        if (size == 0)
            return false;
        else if (index == 0)
            return removeFirst();
        else if (index == size - 1)
            return removeLast();
        Node current = front;
        for (int i = 0; i < index - 1; i++)
            current = current.getNext();
        current.setNext(current.getNext().getNext());
        size--;
        return true;
    }

    public boolean remove(Object element) {
        if (size == 0)
            return false;
        else if (element.equals(front.getElement()))
            return removeFirst();
        else if (element.equals(back.getElement()))
            return removeLast();

        Node current = front.getNext();
        Node temp = front;
        for (int i = 0; i < size; i++) {
            if (element.equals(current.getElement())) {
                temp.setNext(current.getNext());
                size--;
                return true;
            }
            current = current.getNext();
            temp = temp.getNext();
        }
        return false;
    }

    public Object get(int index) {
        if (size < 0 || index >= size)
            return null;
        else if (index == 0)
            return getFirst();
        else if (index == size - 1)
            return getLast();
        Node current = front;
        for (int i = 0; i < index; i++)
            current = current.getNext();
        return current.getElement();
    }

    public int size() {
        return size;
    }

    public Node getFront() {
        return front;
    }

    public void setFront(Node front) {
        this.front = front;
    }

    public Node getBack() {
        return back;
    }

    public void setBack(Node back) {
        this.back = back;
    }

    public void addAll(LinkedList list) {
        Node current = list.getFront();
        while (current != null) {
            addLast(new Node(current.getElement()));
            current = current.getNext();
        }
    }

}