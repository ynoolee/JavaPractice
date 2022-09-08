package book.modernjavainaction.ch16;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureDoSomethingLong {

    private void asyncDoSomethingLong() {
        System.out.println(
            "[" + Thread.currentThread().getName() + "]" + " Start generate thread pool ");
        long start = System.nanoTime();

        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println(
            "[" + Thread.currentThread().getName() + "]" + " Complete to generate thread pool : "
                + (System.nanoTime() - start));

        Future<Double> future = executorService.submit(this::runSomethingLong);
        doSomething();
        try {
            Double result = future.get(1,
                TimeUnit.SECONDS); // 현재 스레드가 대기할 최대 타임아웃 시간 설정 -> 타임아웃시 TimeOutException 발생

            System.out.println(
                "[" + Thread.currentThread().getName() + "]" + " FUTURE result received : "
                    + result);
        } catch (ExecutionException ee) {
        
        } catch (InterruptedException ie) {
            // 현재 스레드의 대기 중에 interrupt 발생
        } catch (TimeoutException te) {
            te.printStackTrace(); // Future 완료 전 타임 아웃 발생 : f
        }

        executorService.shutdown();

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
