import java.util.Random;
import java.util.stream.IntStream;

public class GameOfLife {
    final static int SIZE = 10000;
    boolean[][] cells = new boolean[SIZE][SIZE];

    public GameOfLife() {
        Random rand = new Random();
        int numOfCellsToInit = SIZE * SIZE / 2;
        for (int i = 0; i < numOfCellsToInit; i++) {
            int x, y;
            do {
                x = rand.nextInt(SIZE);
                y = rand.nextInt(SIZE);
            } while (cells[x][y]);
            cells[x][y] = true;
        }
    }

    public static void main(String[] args) {
        GameOfLife gameOfLife = new GameOfLife();
        for (int i = 0; i < 10; i++) {
            gameOfLife.printGeneration();
            gameOfLife.nextGeneration();
        }
    }

    void nextGeneration() {
        boolean[][] newCells = new boolean[SIZE][SIZE];
        IntStream.range(0, SIZE).parallel().forEach(i -> {
            IntStream.range(0, SIZE).parallel().forEach(j -> {
                int aliveNeighbours = countAliveNeighbours(i, j);
                if (cells[i][j]) {
                    newCells[i][j] = aliveNeighbours == 2 || aliveNeighbours == 3;
                } else {
                    newCells[i][j] = aliveNeighbours == 3;
                }
            });
        });
        cells = newCells;
    }

    int countAliveNeighbours(int x, int y) {
        int count = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(SIZE - 1, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(SIZE - 1, y + 1); j++) {
                count += cells[i][j] ? 1 : 0;
            }
        }
        if (cells[x][y]) {
            count--;
        }
        return count;
    }

    void printGeneration() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(cells[i][j] ? "*" : " ");
            }
            System.out.println();
        }
    }

    void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
