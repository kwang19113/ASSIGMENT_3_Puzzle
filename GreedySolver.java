public class GreedySolver implements Solver {
    public String getName() {
        return "Greedy Best-First Search";
    }

    private String getReverseMove(String move) {
        switch (move) {
            case "up": return "down";
            case "down": return "up";
            case "left": return "right";
            case "right": return "left";
            default: return null;
        }
    }

    public String[] solve(Board start) {
        MinHeap queue = new MinHeap(false); // false = min-heap
        long startTime = System.currentTimeMillis();
        int nodesExpanded = 0;
        int frameCount = 0;

        VisitedSet visited = new VisitedSet();
        queue.push(new Node(start, 0, null, new String[0], 0));

        while (!queue.isEmpty()) {
            if (frameCount++ % 1000 == 0) {
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.print("\rsolving" + ".".repeat((frameCount / 1000) % 3 + 1) +
                                 " (" + (elapsed / 1000.0) + "s)");
            }

            Node current = queue.pop();

            if (visited.contains(current.board)) continue;
            visited.add(current.board);

            if (current.board.isGoal()) {
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println("\rsolved in " + (elapsed / 1000.0) + " seconds. Nodes expanded: " + nodesExpanded);
                String[] result = new String[current.pathLength];
                for (int i = 0; i < current.pathLength; i++) result[i] = current.path[i];
                return result;
            }

            String lastMove = current.pathLength == 0 ? null : current.path[current.pathLength - 1];
            String reverseMove = lastMove == null ? null : getReverseMove(lastMove);
            Board[] neighbors = current.board.getNeighbors(lastMove);

            for (Board neighbor : neighbors) {
                if (visited.contains(neighbor)) continue;

                String move = PuzzleUtils.getMoveFromBoards(current.board.getTiles(), neighbor.getTiles());
                if (move != null && move.equals(reverseMove)) continue; // 🚫 prune reverse move

                String[] newPath = new String[current.pathLength + 1];
                System.arraycopy(current.path, 0, newPath, 0, current.pathLength);
                newPath[current.pathLength] = move;

                int heuristic = neighbor.manhattan();
                Node next = new Node(neighbor, heuristic, current, newPath, current.pathLength + 1);
                queue.push(next);
                nodesExpanded++;
            }
        }

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("\rno solution found (after " + (elapsed / 1000.0) + " seconds). Nodes expanded: " + nodesExpanded);
        return null;
    }
}
