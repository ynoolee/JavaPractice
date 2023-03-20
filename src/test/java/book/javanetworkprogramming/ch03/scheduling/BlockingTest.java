package book.javanetworkprogramming.ch03.scheduling;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlockingTest {

    private final List<Integer> sharedData = new ArrayList<>(List.of(11,12,13));

    @Test
    @DisplayName("락을 점유하고 sleep 을 호출한 스레드에 의해,동일한 객체에 대한 배타적 락을 획득하려는 스레드가 블락된다")
    void blockOtherThreadWhenEnteringSleepStateInSynchronizedBlock() {
        Thread lockOwnerThread = new Thread(() -> {
            synchronized (sharedData) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread blockedThread = new Thread(() -> {
            synchronizedPrint(1);
            while(true) ;
        });

        lockOwnerThread.start();
        blockedThread.start();

        Assertions.assertThat(lockOwnerThread.getState()).isEqualTo(State.TIMED_WAITING);
        Assertions.assertThat(blockedThread.getState()).isEqualTo(State.BLOCKED);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assertions.assertThat(lockOwnerThread.getState()).isEqualTo(State.TERMINATED);
        Assertions.assertThat(blockedThread.getState()).isEqualTo(State.RUNNABLE);

    }

    @Test
    @DisplayName("락을 점유하고 sleep 을 호출한 스레드 이후, 다른 스레드에서 동일한 객체에 대한 wait()")

    private void synchronizedPrint(int idx) {
        synchronized (sharedData) {
            System.out.println(sharedData.get(idx));
        }
    }

}
