public class Heap {
    private HNode[] heap;
    private int size;
    private int maxSize;

    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
        this.heap = new HNode[maxSize + 1];
    }

    private void minHeapify(int pos) {
        int smallest = pos;
        int left = leftChild(pos);
        int right = rightChild(pos);

        if (left <= size && heap[left].getCurrentCost() < heap[smallest].getCurrentCost()) {
            smallest = left;
        }

        if (right <= size && heap[right].getCurrentCost() < heap[smallest].getCurrentCost()) {
            smallest = right;
        }

        if (smallest != pos) {
            swap(pos, smallest);
            minHeapify(smallest);
        }
    }

    public void insert(HNode element) {
        if (size >= maxSize) {
            return;
        }

        heap[++size] = element;
        int current = size;

        while (current > 1 && heap[current].getCurrentCost() < heap[parent(current)].getCurrentCost()) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public HNode deleteMin() {
        if (size == 0) {
            return null;
        }
        HNode popped = heap[1];
        heap[1] = heap[size--];
        minHeapify(1);
        return popped;
    }

    public int getSize() {
        return size;
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos);
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    private void swap(int fpos, int spos) {
        HNode tmp;
        tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
}
