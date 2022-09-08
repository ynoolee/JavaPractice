package book.modernjavainaction.ch16;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FutureDoSomethingLong {

    private void asyncDoSomethingLong() {
        System.out.println(
            "[" + Thread.currentThread().getName() + "]" + " Start generate thread pool ");
        long start = System.nanoTime();

        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println(
            "[" + Thread.currentThread().getName() + "]" + " Complete to generate thread pool : "
                + (System.nanoTime() - start));

        CompletableFuture.supplyAsync(this::runSomethingLong)
            .thenAccept(numb -> System.out.println(
                "[" + Thread.currentThread().getName() + "]" + " FUTURE result received : "
                    + numb));

        doSomething();

        try {
            if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                executorService.shutdownNow();

                Thread.getAllStackTraces().keySet()
                    .forEach(thread -> System.out.println(thread.getName()));
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }


    }

    private void doSomething() {
        System.out.println("[" + Thread.currentThread().getName() + "]" + "DO Something!!");
    }

    private double runSomethingLong() {
        try {
            System.out.println(
                "[" + Thread.currentThread().getName() + "]" + "Start DO SomethingLong!!");

            Thread.sleep(1500);

            System.out.println(
                "[" + Thread.currentThread().getName() + "]" + "Complete DO SomethingLong!!");
            return 1.2;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 2.2;
    }

    public static void main(String[] args) {
        FutureDoSomethingLong main = new FutureDoSomethingLong();
        main.asyncDoSomethingLong();
    }
}
