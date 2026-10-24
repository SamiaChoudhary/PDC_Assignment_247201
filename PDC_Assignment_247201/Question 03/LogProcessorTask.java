public class LogProcessorTask implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("[" + Thread.currentThread().getName() + "] Iteration " + i + 
                               " - Processing diagnostic application logs...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("LogProcessor interrupted: " + e.getMessage());
            }
        }
    }
}