import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import algorithms.AStarSolver;
import algorithms.GreedySolver;
import algorithms.Solver;
import entity.Board;
import utils.PuzzleUtils;


public class Benchmark {
    public static void main(String[] args) {
        String regularCsvFile = "solver_performance_regular.csv";
        int trials = 5;
        int maxDifficulty = 80;

        try (FileWriter writer = new FileWriter(regularCsvFile)) {
            writer.append("Difficulty,Trial,Solver,Time(ms),Steps,NodesExpanded,MemoryUsed(bytes),Seed,BoardState\n");

            for (int difficulty = 1; difficulty <= maxDifficulty; difficulty++) {
                for (int trial = 1; trial <= trials; trial++) {
                    long seed = System.currentTimeMillis();
                    int[] boardArray = PuzzleUtils.getShuffledBoard(difficulty, seed);
                    Board start = new Board(boardArray);

                    Solver[] solvers = new Solver[]{
                        new GreedySolver(),
                        new AStarSolver(),
                    };

                    for (Solver solver : solvers) {
                        System.out.println("Difficulty " + difficulty + ", Trial " + trial + ", Using: " + solver.getName());

                        Runtime runtime = Runtime.getRuntime();
                        runtime.gc();
                        long beforeUsedMem = runtime.totalMemory() - runtime.freeMemory();

                        long startTime = System.nanoTime();
                        String[] solution = solver.solve(start);
                        long elapsedTimeNs = System.nanoTime() - startTime;
                        long elapsedTimeMs = elapsedTimeNs / 1_000_000;

                        long afterUsedMem = runtime.totalMemory() - runtime.freeMemory();
                        long usedMem = afterUsedMem - beforeUsedMem;

                        int nodesExpanded = solver.getNodesExpanded();  

                        String boardState = Arrays.toString(boardArray);

                        if (solution != null) {
                            writer.append(difficulty + "," + trial + "," + solver.getName() + "," +
                                    elapsedTimeMs + "," + solution.length + "," + nodesExpanded + "," +
                                    usedMem + "," + seed + ",\"" + boardState + "\"\n");
                            System.out.println("Steps: " + solution.length + ", Time: " + elapsedTimeMs + "ms, Nodes: " + nodesExpanded + ", Mem: " + usedMem + " bytes\n");
                        } else {
                            writer.append(difficulty + "," + trial + "," + solver.getName() + ",N/A,N/A," + nodesExpanded + "," +
                                    usedMem + "," + seed + ",\"" + boardState + "\"\n");
                            System.out.println("No solution found. Nodes: " + nodesExpanded + ", Mem: " + usedMem + " bytes\n");
                        }
                    }
                }
            }

            System.out.println("Benchmarking complete. Results saved to " + regularCsvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
