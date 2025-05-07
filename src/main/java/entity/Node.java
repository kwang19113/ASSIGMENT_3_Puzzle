package entity;

public class Node {
    public static long globalCounter = 0;
    public Board board;
    public int cost;
    public Node parent;
    public String[] path;
    public int pathLength;
    public long order;
    public Node(Board board, int cost, Node parent, String[] path, int pathLength) {
        this.board = board;
        this.cost = cost;
        this.parent = parent;
        this.path = path;
        this.pathLength = pathLength;
        this.order = globalCounter++;
    }

    public int f() {
        return cost + board.manhattan();
    }
}