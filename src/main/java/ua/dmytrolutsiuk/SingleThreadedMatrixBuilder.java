package ua.dmytrolutsiuk;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Fill the square matrix with random numbers. Place the maximum column element on the main diagonal.
 * Without using multithreading.
 */
@Slf4j
public class SingleThreadedMatrixBuilder {

    private static final int MATRIX_SIZE = 50_000;

    public static void main(String[] args) {
        var random = new Random();
        int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];

        long start = System.nanoTime();
        for (int i = 0; i < MATRIX_SIZE; i++) {
            int maxColumnValue = Integer.MIN_VALUE;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                int randomValue = random.nextInt(1000);
                matrix[j][i] = randomValue;
                if (randomValue > maxColumnValue) {
                    maxColumnValue = randomValue;
                }
            }
            matrix[i][i] = maxColumnValue;
        }
        long end = System.nanoTime();

        log.info("Execution time: {}ms", (end - start) / 1_000_000);
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}