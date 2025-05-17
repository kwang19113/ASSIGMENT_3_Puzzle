package entity;

import utils.PuzzleUtils;

public class Board {
    public static final int SIZE = 4;
    public static final int BLANK = 0;
    private final int[] tiles;

    public Board(int[] tiles) {
        this.tiles = new int[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i] = tiles[i];
        }
    }

    public int[] getTiles() {
        int[] copy = new int[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            copy[i] = tiles[i];
        }
        return copy;
    }

    public boolean isGoal() {
        for (int i = 0; i < SIZE * SIZE - 1; i++) {
            if (tiles[i] != i + 1) return false;
        }
        return tiles[SIZE * SIZE - 1] == BLANK;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < SIZE * SIZE; i++) {
            int val = tiles[i];
            if (val != BLANK) {
                int goalX = (val - 1) % SIZE;
                int goalY = (val - 1) / SIZE;
                int currX = i % SIZE;
                int currY = i / SIZE;
                distance += Math.abs(goalX - currX) + Math.abs(goalY - currY);
            }
        }
        return distance;
    }

    public Board[] getNeighbors(String prevMove) {
        int[] pos = PuzzleUtils.findBlank(tiles);
        int x = pos[0], y = pos[1];
        String[] moves = PuzzleUtils.getValidMoves(x, y, prevMove);
        Board[] neighbors = new Board[moves.length];
        for (int i = 0; i < moves.length; i++) {
            int[] newTiles = getTiles();
            PuzzleUtils.makeMove(newTiles, moves[i]);
            neighbors[i] = new Board(newTiles);
        }
        return neighbors;
    }

    public void print() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                int val = tiles[y * SIZE + x];
                if (val == BLANK) {
                    System.out.print("__ ");
                } else {
                    if (val < 10) System.out.print(" " + val + " ");
                    else System.out.print(val + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) return false;
        Board other = (Board) obj;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != other.tiles[i]) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (int tile : tiles) {
            result = 31 * result + tile;
        }
        return result;
    }
}