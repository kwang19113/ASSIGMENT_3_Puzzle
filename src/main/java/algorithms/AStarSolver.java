package algorithms;

import entity.Board;
import entity.MinHeap;
import entity.Node;
import entity.VisitedSet;
import utils.PuzzleUtils;

public class AStarSolver implements Solver {
    @Override
    public String getName() {
        return "A* Search";
    }

    @Override
    public String[] solve(Board start) {
        MinHeap queue = new MinHeap(true);
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
            Board[] neighbors = current.board.getNeighbors(lastMove);

            for (int i = 0; i < neighbors.length; i++) {
                if (visited.contains(neighbors[i])) continue;

                String[] newPath = new String[current.pathLength + 1];
                for (int j = 0; j < current.pathLength; j++) newPath[j] = current.path[j];
                newPath[current.pathLength] = PuzzleUtils.getMoveFromBoards(current.board.getTiles(), neighbors[i].getTiles());

                Node next = new Node(neighbors[i], current.cost + 1, current, newPath, current.pathLength + 1);
                queue.push(next);
                nodesExpanded++;
            }
        }

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("\rno solution found (after " + (elapsed / 1000.0) + " seconds). Nodes expanded: " + nodesExpanded);
        return null;
    }
}
