package algorithms;

import entity.Board;

public interface Solver {
    String[] solve(Board start);
    String getName();
}