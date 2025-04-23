import java.io.FileWriter;
import java.io.IOException;

public class Benchmark {
    public static void main(String[] args) {
        String regularCsvFile = "solver_performance_regular.csv";
        String difficulty15CsvFile = "solver_performance_diff15.csv";

        // Regular benchmark (difficulties 1-20)
        try (FileWriter writer = new FileWriter(regularCsvFile)) {
            writer.append("Difficulty,Solver,Time(ms),Steps\n");

            for (int difficulty = 1; difficulty <= 20; difficulty++) {
                int[] boardArray = PuzzleUtils.getShuffledBoard(difficulty);
                Board start = new Board(boardArray);

                Solver[] solvers = new Solver[]{
                    new GreedySolver(),
                    new AStarSolver(),
                    new RecursiveSolver(50)
                };

                for (Solver solver : solvers) {
                    System.out.println("Difficulty " + difficulty + ", Using: " + solver.getName());
                    long startTime = System.currentTimeMillis();
                    String[] solution = solver.solve(start);
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    if (solution != null) {
                        writer.append(difficulty + "," + solver.getName() + "," + elapsedTime + "," + solution.length + "\n");
                        System.out.println("Steps: " + solution.length + ", Time: " + elapsedTime + "ms\n");
                    } else {
                        writer.append(difficulty + "," + solver.getName() + ",N/A,N/A\n");
                        System.out.println("No solution found.\n");
                    }
                }
            }
            System.out.println("Regular benchmarking complete. Results saved to " + regularCsvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Difficulty 15 benchmark (10 iterations)
        try (FileWriter writer = new FileWriter(difficulty15CsvFile)) {
            writer.append("Iteration,Solver,Time(ms),Steps\n");

            System.out.println("\nRunning 10 iterations for difficulty 15...\n");
            for (int i = 0; i < 10; i++) {
                int[] boardArray = PuzzleUtils.getShuffledBoard(15);
                Board start = new Board(boardArray);

                Solver[] solvers = new Solver[]{
                    new GreedySolver(),
                    new AStarSolver(),
                    new RecursiveSolver(50)
                };

                for (Solver solver : solvers) {
                    System.out.println("Iteration " + (i + 1) + ", Using: " + solver.getName());
                    long startTime = System.currentTimeMillis();
                    String[] solution = solver.solve(start);
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    if (solution != null) {
                        writer.append("Iteration-" + (i + 1) + "," + solver.getName() + "," + elapsedTime + "," + solution.length + "\n");
                        System.out.println("Steps: " + solution.length + ", Time: " + elapsedTime + "ms\n");
                    } else {
                        writer.append("Iteration-" + (i + 1) + "," + solver.getName() + ",N/A,N/A\n");
                        System.out.println("No solution found.\n");
                    }
                }
            }
            System.out.println("Difficulty 15 benchmarking complete. Results saved to " + difficulty15CsvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
