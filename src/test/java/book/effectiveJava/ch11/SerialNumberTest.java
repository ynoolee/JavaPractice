package book.effectiveJava.ch11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SerialNumberTest {
    private static volatile int serialNumber1 = 0;
    private static volatile AtomicLong nextSerialNumber = new AtomicLong();

    public static int generateSerialNumber1() {
        return serialNumber1++;
    }
    public static long generateSerialNumber2() {
        return nextSerialNumber.getAndIncrement();
    }

    @Test
    @DisplayName("AtomicInteger 인 volatile 설정된 시리얼 넘버에 대하여, 0 부터 시작하여 x 번 만큼 serialNumber 를 통해 수를 얻어오는 스레가 동시에 다수 실행되는 경우더라도, 다음 번 serialNumber 는 x 임을 기대한다")
    void givenVolatileAtomicInteger_whenIncrement_thenSuccess() throws InterruptedException {
        int x = 1000;

        ExecutorService threadPoolExecutor =
            Executors.newFixedThreadPool(10);

        IntStream.range(0,x)
                .forEach(i ->
                    threadPoolExecutor.submit(SerialNumberTest::generateSerialNumber2));

        Thread.sleep(2000);

        Assertions.assertThat(generateSerialNumber2())
            .isEqualTo(x);
    }

    @Test
    @DisplayName("일반 int 인 volatile 설정된 시리얼 넘버에 대하여, 0 부터 시작하여 x 번 만큼 serialNumber 를 통해 수를 얻어오는 스레가 동시에 다수 실행되는 경우, 다음 번 serialNumber 는 x 가 아닐 수도 있다")
    void givenVolatileInteger_whenIncrement_thenFail () throws InterruptedException {
        int x = 1000;

        ExecutorService threadPoolExecutor =
            Executors.newFixedThreadPool(10);

        IntStream.range(0,x)
            .forEach(i ->
                threadPoolExecutor.submit(SerialNumberTest::generateSerialNumber1));

        Thread.sleep(2000);

        Assertions.assertThat(generateSerialNumber1())
            .isNotEqualTo(x);
    }
}