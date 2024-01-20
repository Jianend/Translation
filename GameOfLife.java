import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameOfLife {
    static final int SIZE = 1000;
    boolean[][] cells = new boolean[SIZE][SIZE];

    public GameOfLife() {
        Random rand = new Random();
        IntStream.range(0, SIZE * SIZE / 2).forEach(i -> {
            int x, y;
            do {
                x = rand.nextInt(SIZE);
                y = rand.nextInt(SIZE);
            } while (cells[x][y]);
            cells[x][y] = true;
        });
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        IntStream.range(0, 10).forEach(i -> {
            game.nextGeneration();
        });
    }

    void nextGeneration() {
        boolean[][] newCells = new boolean[SIZE][SIZE];
        IntStream.range(0, SIZE).parallel().forEach(i -> IntStream.range(0, SIZE).forEach(j -> {
            int aliveNeighbours = countAliveNeighbours(i, j);
            newCells[i][j] = cells[i][j] ? aliveNeighbours == 2 || aliveNeighbours == 3 : aliveNeighbours == 3;
        }));
        cells = newCells;
    }

    int countAliveNeighbours(int x, int y) {
        return (int) IntStream.rangeClosed(Math.max(0, x - 1), Math.min(SIZE - 1, x + 1))
                .mapToObj(i -> IntStream.rangeClosed(Math.max(0, y - 1), Math.min(SIZE - 1, y + 1))
                        .mapToObj(j -> new int[] { i, j }))
                .flatMap(array -> array)
                .filter(pos -> cells[pos[0]][pos[1]])
                .count() - (cells[x][y] ? 1 : 0);
    }
}
