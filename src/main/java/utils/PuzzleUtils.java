package utils; 
public class PuzzleUtils {
    public static final String UP = "up", DOWN = "down", LEFT = "left", RIGHT = "right";

    public static int[] findBlank(int[] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) return new int[]{i % 4, i / 4};
        }
        return null;
    }

    public static void makeMove(int[] board, String move) {
        int[] pos = findBlank(board);
        int x = pos[0], y = pos[1];
        int blankIdx = y * 4 + x;
        int swapIdx = -1;
        if (move.equals(UP)) swapIdx = (y + 1) * 4 + x;
        else if (move.equals(DOWN)) swapIdx = (y - 1) * 4 + x;
        else if (move.equals(LEFT)) swapIdx = y * 4 + (x + 1);
        else if (move.equals(RIGHT)) swapIdx = y * 4 + (x - 1);
        int tmp = board[blankIdx];
        board[blankIdx] = board[swapIdx];
        board[swapIdx] = tmp;
    }

    public static String getReverseMove(String move) {
        if (move.equals(UP)) return DOWN;
        if (move.equals(DOWN)) return UP;
        if (move.equals(LEFT)) return RIGHT;
        if (move.equals(RIGHT)) return LEFT;
        return null;
    }

    public static String[] getValidMoves(int x, int y, String prevMove) {
        String[] allMoves = new String[]{UP, LEFT, DOWN, RIGHT};
        String[] valid = new String[4];
        int count = 0;
        if (y < 3) valid[count++] = UP;
        if (x < 3) valid[count++] = LEFT;
        if (y > 0) valid[count++] = DOWN;
        if (x > 0) valid[count++] = RIGHT;
        String[] filtered = new String[count];
        int idx = 0;
        for (int i = 0; i < count; i++) {
            if (prevMove == null || !getReverseMove(prevMove).equals(valid[i])) {
                filtered[idx++] = valid[i];
            }
        }
        String[] result = new String[idx];
        for (int i = 0; i < idx; i++) result[i] = filtered[i];
        return result;
    }

    public static int[] getShuffledBoard(int steps) {
        int[] board = new int[16];
        for (int i = 1; i < 16; i++) board[i - 1] = i;
        board[15] = 0;

        java.util.Random rand = new java.util.Random();
        String lastMove = null;
        for (int i = 0; i < steps; i++) {
            int[] pos = findBlank(board);
            int x = pos[0], y = pos[1];
            String[] moves = getValidMoves(x, y, lastMove);
            String move = moves[rand.nextInt(moves.length)];
            makeMove(board, move);
            lastMove = move;
        }
        return board;
    }
    public static int[] getShuffledBoard(int steps, long seed) {
    int[] board = new int[16];
    for (int i = 1; i < 16; i++) board[i - 1] = i;
    board[15] = 0;

    String lastMove = null;
    for (int i = 0; i < steps; i++) {
        int[] pos = findBlank(board);
        int x = pos[0], y = pos[1];
        String[] moves = getValidMoves(x, y, lastMove);
        
        seed = (seed * 6364136223846793005L + 1) & 0xFFFFFFFFFFFFL;
        int randIndex = (int)(seed % moves.length);

        String move = moves[randIndex];
        makeMove(board, move);
        lastMove = move;
    }
    return board;
}
    public static String getMoveFromBoards(int[] from, int[] to) {
        int fromBlank = -1, toBlank = -1;
        for (int i = 0; i < 16; i++) {
            if (from[i] == 0) fromBlank = i;
            if (to[i] == 0) toBlank = i;
        }
        int fx = fromBlank % 4, fy = fromBlank / 4;
        int tx = toBlank % 4, ty = toBlank / 4;
    
        if (fx == tx && fy + 1 == ty) return "up";
        if (fx == tx && fy - 1 == ty) return "down";
        if (fy == ty && fx + 1 == tx) return "left";
        if (fy == ty && fx - 1 == tx) return "right";
        return null;
    }
}
