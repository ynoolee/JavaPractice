package book.effectiveJava.item50;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PeriodTest {
    @Nested
    @DisplayName("동시성 테스트")
    public class ConcurrentTest {
        private long timeToAdd = 1000L;

        private void modifyStart(Period targetPeriod) {
            Date start = targetPeriod.start();

            start.setTime(calculatedTime(start.getTime(), timeToAdd));
        }

        private long calculatedTime(long time, long timeToAdd) {
            return time + timeToAdd;
        }

        @Test
        @DisplayName("동시에 실행 중인 여러 스레드에서 하나의 Period 를 공유할 경우 예상하지 못한 결과가 발생한다")
        void testFixedThreadPool() throws Exception {
            long startTime = 1000L;

            Period period = new Period(new Date(startTime), new Date(1500));
            Date startDate = period.start();

            runConcurrentTasks(period);

            Assertions.assertThat(period.start())
                    .isEqualTo(startDate);

            Assertions.assertThat(period.start().getTime())
                .isNotEqualTo((startTime));
        }

        private void runConcurrentTasks(Period period) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                5);

            IntStream.range(0, 10)
                .forEach(i ->
                    threadPoolExecutor.execute(this.getTask(period)));
        }


        private Runnable getTask(Period period) {
            return () -> {
                modifyStart(period);
            };
        }

        private void shutDownAndWaitUntilTerminated(ExecutorService executorService) {
            try {
                executorService.shutdown();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}