package book.modernjavainaction.ch15;

import book.modernjavainaction.ch15.ThreadExample.Result;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MultiCoreTest {

    private final int x = 10000;

    int f(int x) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return IntStream.range(1, x + 1)
            .reduce(Integer::sum)
            .orElse(0);
    }

    int g(int x) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return IntStream.range(x * (-1), 0)
            .reduce(Integer::sum)
            .orElse(0);
    }

    @Nested
    class ThreadTest {

        @Test
        @DisplayName("멀티 코어의 활용 - 멀티 코어 활용을 위해 Task f 와 Task g 를 별도의 스레드에서 실행하도록 한다.")
        void givenTasks_WhenRunningEachTaskUsingExplicitThread_ThenExpectedResult()
            throws InterruptedException {

            Result results = new Result();

            // 명시적인 스레드 생성 및 실행으로 인해 코드가 깔끔하지 못하다
            Thread t1 = new Thread(() -> results.setLeft(f(x)));
            Thread t2 = new Thread(() -> results.setRight(g(x)));

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            int result = results.left() + results.right();

            Assertions.assertThat(result)
                .isEqualTo(0);
        }

        @Test
        @DisplayName("하나의 스레드에서 Task f 와 Task g 를 실행할 경우 멀티코어를 활용하지 못해 더 오랜 시간이 걸린다")
        void givenTasks_WhenRunningEachTaskUsingSameThread() {
            Result results = new Result();

            results.setLeft(f(x));
            results.setRight(g(x));

            int result = results.left() + results.right();

            Assertions.assertThat(result)
                .isEqualTo(0);
        }
    }

    @Nested
    class ThreadPoolTest {

        @Test
        @DisplayName("멀티 코어의 활용 - Future 를 사용해 멀티코어 활용 ThreadTest 코드를 더 단순화 할 수 있다")
        void givenTasks_whenRunningEachTaskUsingThreadPool()
            throws ExecutionException, InterruptedException {
            ExecutorService executorService = Executors.newFixedThreadPool(2);

            Future<Integer> y = executorService.submit(() -> f(x));
            Future<Integer> z = executorService.submit(() -> g(x));

            int result = y.get() + z.get();

            executorService.shutdown();

            Assertions.assertThat(result)
                .isEqualTo(0);
        }
    }

}
