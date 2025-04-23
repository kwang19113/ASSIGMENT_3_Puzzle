class MinHeap {
    private Node[] heap = new Node[1024];
    private int size = 0;
    private final boolean isAStar;

    public MinHeap(boolean isAStar) {
        this.isAStar = isAStar;
    }

    public void push(Node node) {
        if (size == heap.length) {
            Node[] bigger = new Node[heap.length * 2];
            for (int i = 0; i < heap.length; i++) bigger[i] = heap[i];
            heap = bigger;
        }
        heap[size] = node;
        int i = size++;
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (!compare(heap[i], heap[parent])) break;
            Node temp = heap[i]; heap[i] = heap[parent]; heap[parent] = temp;
            i = parent;
        }
    }

    public Node pop() {
        if (size == 0) return null;
        Node result = heap[0];
        heap[0] = heap[--size];
        int i = 0;
        while (true) {
            int left = 2 * i + 1, right = 2 * i + 2, best = i;
            if (left < size && compare(heap[left], heap[best])) best = left;
            if (right < size && compare(heap[right], heap[best])) best = right;
            if (best == i) break;
            Node temp = heap[i]; heap[i] = heap[best]; heap[best] = temp;
            i = best;
        }
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean compare(Node a, Node b) {
    int cmp = isAStar ? Integer.compare(a.f(), b.f()) : Integer.compare(a.board.manhattan(), b.board.manhattan());
    if (cmp != 0) return cmp < 0;
    return a.order < b.order; // 👈 Tie-breaker: earlier nodes win
    }
}
