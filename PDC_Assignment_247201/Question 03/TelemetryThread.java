public class TelemetryThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("[" + Thread.currentThread().getName() + "] Iteration " + i + 
                               " - Telemetry Update: CPU Utilization 42%, Memory 61% Used");
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                System.err.println("TelemetryThread interrupted: " + e.getMessage());
            }
        }
    }
}