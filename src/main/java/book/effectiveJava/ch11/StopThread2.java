package book.effectiveJava.ch11;

import java.util.concurrent.TimeUnit;

public class StopThread2 {
    private static boolean stopRequested;

    // write
    private static synchronized void requestStop() {
        stopRequested= true;
    }

    // read
    private static synchronized boolean stopRequested() {
        return stopRequested;
    }

    public static void main(String[] args)
        throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested()) {
                i++;
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
