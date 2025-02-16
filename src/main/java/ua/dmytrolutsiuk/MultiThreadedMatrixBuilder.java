package ua.dmytrolutsiuk;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Fill the square matrix with random numbers. Place the maximum column element on the main diagonal.
 * Using multithreading.
 */
@Slf4j
public class MultiThreadedMatrixBuilder {

    private static final int MATRIX_SIZE = 50_000;
    private static final int THREADS_AMOUNT = 224;

    public static void main(String[] args) throws InterruptedException {
        int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        Thread[] threads = new Thread[THREADS_AMOUNT];

        long start = System.nanoTime();
        for (int i = 0; i < THREADS_AMOUNT; i++) {
            threads[i] = new Thread(new MatrixColumnFiller(matrix, MATRIX_SIZE, THREADS_AMOUNT, i));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long end = System.nanoTime();

        log.info("Execution time: {}ms", (end - start) / 1_000_000);
    }

    /**
     * A thread that processes a part of the matrix columns.
     * Each thread processes columns with indices: threadId, threadId + threadCount, threadId + 2*threadCount, ...
     */
    static class MatrixColumnFiller implements Runnable {
        private final int[][] matrix;
        private final int matrixSize;
        private final int threadsAmount;
        private final int threadId;
        private final Random random = new Random();

        public MatrixColumnFiller(int[][] matrix, int matrixSize, int threadsAmount, int threadId) {
            this.matrix = matrix;
            this.matrixSize = matrixSize;
            this.threadsAmount = threadsAmount;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            for (int col = threadId; col < matrixSize; col += threadsAmount) {
                int maxColumnValue = Integer.MIN_VALUE;
                for (int row = 0; row < matrixSize; row++) {
                    int randomValue = random.nextInt(1000);
                    matrix[row][col] = randomValue;
                    if (randomValue > maxColumnValue) {
                        maxColumnValue = randomValue;
                    }
                }
                matrix[col][col] = maxColumnValue;
            }
        }
    }
}
