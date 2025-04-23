public class RecursiveSolver implements Solver {
    private int maxDepth;
    private String[] resultPath = null;
    private boolean solved = false;

    public RecursiveSolver(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public String getName() {
        return "Recursive Solver (depth=" + maxDepth + ")";
    }

    @Override
    public String[] solve(Board start) {
        long startTime = System.currentTimeMillis();
        for (int depth = 1; depth <= this.maxDepth; depth++) {
            this.solved = false;
            this.resultPath = null;

            VisitedSet visited = new VisitedSet();
            attempt(start, new String[depth + 1], 0, depth, null, visited);

            if (solved) {
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println("Solved in " + elapsed / 1000.0 + " seconds at depth " + depth);
                return resultPath;
            }
        }

        System.out.println("No solution found.");
        return null;
    }


    private void attempt(Board board, String[] path, int pathLen, int depthRemaining, String prevMove, VisitedSet visited) {
        if (solved || depthRemaining < 0) return;
        if (visited.contains(board)) return;

        if (board.isGoal()) {
            solved = true;
            resultPath = new String[pathLen];
            for (int i = 0; i < pathLen; i++) resultPath[i] = path[i];
            return;
        }

        visited.add(board);
        Board[] neighbors = board.getNeighbors(prevMove);
        for (Board neighbor : neighbors) {
            if (visited.contains(neighbor)) continue;

            String move = PuzzleUtils.getMoveFromBoards(board.getTiles(), neighbor.getTiles());
            path[pathLen] = move;

            attempt(neighbor, path, pathLen + 1, depthRemaining - 1, move, visited);

            if (solved) return;
        }
    }
}
