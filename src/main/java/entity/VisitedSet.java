package entity;

public class VisitedSet {
    private static final int INITIAL_CAPACITY = 1 << 18; // 262,144
    private static final int LOAD_FACTOR = 2; // max 50% full

    private long[] hashes;
    private int[][] boards;
    private boolean[] used;
    private int capacity;
    private int size;

    public VisitedSet() {
        capacity = INITIAL_CAPACITY;
        hashes = new long[capacity];
        boards = new int[capacity][16];
        used = new boolean[capacity];
        size = 0;
    }

    public void add(Board board) {
        if (size * LOAD_FACTOR >= capacity) resize();
        long h = hashBoard(board.getTiles());
        int index = (int) (h & (capacity - 1));
        while (used[index]) {
            index = (index + 1) & (capacity - 1);
        }
        hashes[index] = h;
        int[] tiles = board.getTiles();
        for (int i = 0; i < 16; i++) boards[index][i] = tiles[i];
        used[index] = true;
        size++;
    }

    public boolean contains(Board board) {
        long h = hashBoard(board.getTiles());
        int index = (int) (h & (capacity - 1));
        while (used[index]) {
            if (hashes[index] == h && same(boards[index], board.getTiles())) return true;
            index = (index + 1) & (capacity - 1);
        }
        return false;
    }

    private boolean same(int[] a, int[] b) {
        for (int i = 0; i < 16; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    private long hashBoard(int[] tiles) {
        long h = 17;
        for (int t : tiles) h = h * 31 + t;
        return h;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        long[] oldHashes = hashes;
        int[][] oldBoards = boards;
        boolean[] oldUsed = used;

        hashes = new long[newCapacity];
        boards = new int[newCapacity][16];
        used = new boolean[newCapacity];
        capacity = newCapacity;
        size = 0;

        for (int i = 0; i < oldHashes.length; i++) {
            if (!oldUsed[i]) continue;
            long h = oldHashes[i];
            int index = (int) (h & (capacity - 1));
            while (used[index]) index = (index + 1) & (capacity - 1);
            hashes[index] = h;
            for (int j = 0; j < 16; j++) boards[index][j] = oldBoards[i][j];
            used[index] = true;
            size++;
        }
    }
}
