import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class InventoryCatalogManager {
    private static final int NUM_THREADS = 10;
    private static final int OPERATIONS_PER_THREAD = 50000;
    private static final int TOTAL_OPERATIONS = NUM_THREADS * OPERATIONS_PER_THREAD;
    public void benchmarkSynchronizedMap() throws InterruptedException {
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        initMap(syncMap);
        long startTime = System.nanoTime();
        runWorkerPool(syncMap);
        long endTime = System.nanoTime();
        double timeInSeconds = (endTime - startTime) / 1e9;
        double throughput = TOTAL_OPERATIONS / timeInSeconds;
        System.out.println("=== SynchronizedMap Benchmark ===");
        System.out.printf("Execution Time: %.4f s%n", timeInSeconds);
        System.out.printf("Throughput: %.2f ops/sec%n%n", throughput);
    }
    public void benchmarkConcurrentMap() throws InterruptedException {
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        initMap(concurrentMap);
        long startTime = System.nanoTime();
        runWorkerPool(concurrentMap);
        long endTime = System.nanoTime();
        double timeInSeconds = (endTime - startTime) / 1e9;
        double throughput = TOTAL_OPERATIONS / timeInSeconds;
        System.out.println("=== ConcurrentHashMap Benchmark ===");
        System.out.printf("Execution Time: %.4f s%n", timeInSeconds);
        System.out.printf("Throughput: %.2f ops/sec%n%n", throughput);
    }
    private void initMap(Map<String, Integer> map) {
        for (int i = 0; i < 1000; i++) {
            map.put("PROD_" + i, 100);
        }
    }
    private void runWorkerPool(Map<String, Integer> map) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    String id = "PROD_" + (j % 1000);
                    Integer stock = map.get(id);
                    if (stock != null) {
                        map.put(id, stock + 1);
                    }}
            });}
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
    public static void main(String[] args) throws InterruptedException {
        InventoryCatalogManager manager = new InventoryCatalogManager();
        manager.benchmarkSynchronizedMap();
        manager.benchmarkConcurrentMap();
    }
}
