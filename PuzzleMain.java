
// PuzzleMain.java
public class PuzzleMain {
    public static void main(String[] args) {
        ///GENERATE A RANDOM BOARD OR INPUT A BOARD
        int[] randomArray = PuzzleUtils.getShuffledBoard(13);
        int[] inputArray = new int[] {
            1, 8, 6, 4,
            10, 12, 14, 15,
            3, 7, 5, 2,
            11, 0, 9, 13
        };
        Board start = new Board(inputArray);

        //INITIALIZE SOLVERS
        Solver[] solvers = new Solver[]{new GreedySolver(), new AStarSolver(), new RecursiveSolver(500)};

        for (int i = 0; i < solvers.length; i++) {
            Solver solver = solvers[i];
            System.out.println("Using: " + solver.getName());
            String[] solution = solver.solve(start);

            if (solution != null) {
                System.out.println("Steps: " + solution.length);
                for (int j = 0; j < solution.length; j++) {
                    System.out.print(solution[j]);
                    if (j != solution.length - 1) System.out.print(" -> ");
                }
                System.out.println("\n");
            } else {
                System.out.println("No solution found.\n");
            }
        }
    }
}