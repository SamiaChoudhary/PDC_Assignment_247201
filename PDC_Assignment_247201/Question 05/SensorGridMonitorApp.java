import java.util.Random;

public class SensorGridMonitorApp {
    private static final int NUM_THREADS = 4;

    public static void main(String[] args) throws InterruptedException {
        int arraySize = 50000;
        double[] telemetryData = new double[arraySize];
        Random rand = new Random(123);

        for (int i = 0; i < arraySize; i++) {
            telemetryData[i] = -20.0 + (rand.nextDouble() * 140.0);
        }

        int segmentSize = arraySize / NUM_THREADS;
        MinWorker[] minWorkers = new MinWorker[NUM_THREADS];
        MaxWorker[] maxWorkers = new MaxWorker[NUM_THREADS];
        Thread[] threads = new Thread[NUM_THREADS * 2];

        long startTime = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * segmentSize;
            int end = (i == NUM_THREADS - 1) ? arraySize : (i + 1) * segmentSize;

            minWorkers[i] = new MinWorker(telemetryData, start, end);
            maxWorkers[i] = new MaxWorker(telemetryData, start, end);

            threads[i] = new Thread(minWorkers[i]);
            threads[i + NUM_THREADS] = new Thread(maxWorkers[i]);

            threads[i].start();
            threads[i + NUM_THREADS].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        double globalMin = Double.MAX_VALUE;
        double globalMax = -Double.MAX_VALUE;

        for (int i = 0; i < NUM_THREADS; i++) {
            if (minWorkers[i].getResult() < globalMin) globalMin = minWorkers[i].getResult();
            if (maxWorkers[i].getResult() > globalMax) globalMax = maxWorkers[i].getResult();
        }

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1e6;

        System.out.printf("Global Minimum Temperature: %.2f °C%n", globalMin);
        System.out.printf("Global Maximum Vibration Peak: %.2f%n", globalMax);
        System.out.printf("Parallel Search Execution Time: %.3f ms%n", durationMs);
    }

    private static class MinWorker implements Runnable {
        private final ParallelMinSearch minSearch;
        private double result;

        public MinWorker(double[] data, int start, int end) {
            this.minSearch = new ParallelMinSearch(data, start, end);
        }

        @Override
        public void run() {
            this.result = minSearch.processGrid();
        }

        public double getResult() {
            return result;
        }
    }

    private static class MaxWorker implements Runnable {
        private final ParallelMaxSearch maxSearch;
        private double result;

        public MaxWorker(double[] data, int start, int end) {
            this.maxSearch = new ParallelMaxSearch(data, start, end);
        }

        @Override
        public void run() {
            this.result = maxSearch.processGrid();
        }

        public double getResult() {
            return result;
        }
    }
}