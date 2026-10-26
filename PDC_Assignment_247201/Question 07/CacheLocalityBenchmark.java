public class CacheLocalityBenchmark {
    private static final int N = 2000;
    private static final int ITERATIONS = 10;
    private static final double[][] matrix = new double[N][N];
    public static double traverseRowMajor() {
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
    public static double traverseColumnMajor() {
        double sum = 0.0;
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 1.0;
            }
        }
        for (int i = 0; i < 5; i++) {
            traverseRowMajor();
            traverseColumnMajor();
        }
        long totalRowTime = 0;
        long totalColTime = 0;
        for (int k = 0; k < ITERATIONS; k++) {
            long start = System.nanoTime();
            traverseRowMajor();
            long end = System.nanoTime();
            totalRowTime += (end - start);
            start = System.nanoTime();
            traverseColumnMajor();
            end = System.nanoTime();
            totalColTime += (end - start);
        }
        double avgRowMs = (totalRowTime / (double) ITERATIONS) / 1e6;
        double avgColMs = (totalColTime / (double) ITERATIONS) / 1e6;
        double speedup = avgColMs / avgRowMs;
        System.out.printf("Average Row-Major Execution Time: %.3f ms%n", avgRowMs);
        System.out.printf("Average Column-Major Execution Time: %.3f ms%n", avgColMs);
        System.out.printf("Speedup Ratio (Row-Major / Column-Major): %.2fx%n", speedup);
    }
}