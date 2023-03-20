package book.javanetworkprogramming.ch03.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrintObjectTest {

    @Test
    @DisplayName("Sys.out 인 PrintStream 내부의 연산들은 내부적으로 synchronized 되어 동시 실행에도 출력이 섞이지 않는다")
    public void test() throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(100);
        PrintObject printObject = new PrintObject();

        for (int i = 0; i < 1000; i++) {
            ex.submit(() -> printObject.synchronizedPrint());
        }

        ex.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}