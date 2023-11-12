package learningJava.reactive.streams;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ObserverAndIterableTest {

    @Nested
    @DisplayName("Iterable 은 pulling 방식으로, 소스를 사용하는 쪽에서 직접 끌어다 사용한다")
    class IterableTest {

        private final List<Integer> list = List.of(1, 5);

        private final String expected = "1 5 ";

        private StringBuilder resultBuilder;

        @BeforeEach
        void setUp() {
            resultBuilder = new StringBuilder("");
        }

        @Test
        @DisplayName("Collection 으로부터 iterator 를 가져와 클라이언트가 직접 다음 데이터를 가져온다")
        void iteratorTest() {
            for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
                resultBuilder.append(it.next() + " ");
            }
            Assertions.assertThat(resultBuilder.toString()).isEqualTo(expected);
        }

        @Test
        @DisplayName("Iterable 을 구현한 Aggregate 에 대해 암묵적으로 iterator 를 사용하여 다음 데이터를 가져온다")
        void advanced_for_loop_를_사용해_암묵적으로_iterator_를_사용한다() {
            for (Integer number : list) {
                resultBuilder.append(number + " ");
            }
            Assertions.assertThat(resultBuilder.toString()).isEqualTo(expected);
        }

        @Test
        @DisplayName("익명 메서드가 하나만 존재하는 Iterable 을 lambda 형태로 사용 할 수 있다")
        void lambdaIterable() {
            Iterable<Integer> iter = () -> new Iterator<Integer>() {
                int[] arr = new int[]{1, 5};

                int idx = 0;

                @Override
                public boolean hasNext() {
                    return idx < arr.length;
                }

                @Override
                public Integer next() {
                    return arr[idx++];
                }
            };

            for (Integer number : iter) {
                resultBuilder.append(number + " ");
            }
            Assertions.assertThat(resultBuilder.toString()).isEqualTo(expected);
        }

        @Test
        @DisplayName("원본 Collection 변경 전에 생성했던 Iterator 를 사용해 Collection 접근시 예외가 발생한다")
        void addNewFail() {
            List<Integer> list = createSizeOf(3);
            Iterator<Integer> iterator = list.iterator();

            if (iterator.hasNext()) {
                iterator.next();
            }

            list.add(5);

            if (iterator.hasNext()) {
                Assertions.assertThatExceptionOfType(ConcurrentModificationException.class)
                    .isThrownBy(() -> iterator.next());
            }
        }

        @Test
        @DisplayName("Iterator 는 재사용이 불가능하다")
        void notReusable() {
            List<Integer> list = createSizeOf(3);
            Iterator<Integer> iterator = list.iterator();

            int secondIteratingCount = 0;

            for (; iterator.hasNext(); ) {
                iterator.next();
            }

            for (; iterator.hasNext(); ) {
                iterator.next();
                secondIteratingCount++;
            }

            Assertions.assertThat(secondIteratingCount).isEqualTo(0);
        }

        private List<Integer> createSizeOf(int size) {
            List<Integer> list = new ArrayList<>();
            IntStream.range(1, size + 1)
                .forEach(number -> list.add(number));

            return list;
        }
    }

    @Nested
    @DisplayName("Observable 은 push 방식으로, 데이터나 이벤트를 갖고 있는 소스(Observable)쪽에서 소스를 사용하는 측(Observer)으로 push 한다")
    class ObservableTest {

        /**
         * Java9 부터 reactive stream 으로 인해 Java 의 Observable 은 deprecated 되었음
         */

        @Test
        @DisplayName("비동기적으로 event(data) 를 생산하는 Observable 에 구독자를 추가하고 이벤트발행 시 Observer 의 update 메서드가 동일한 스레드 위에서 콜백된다 ")
        void asyncObservable() throws InterruptedException {
            System.out.printf("Main Test Thread :%s", Thread.currentThread());

            IntObserver observer = new IntObserver();
            List<IntProducer> producers = createProducers();

            ExecutorService threadPool = Executors.newFixedThreadPool(20);

            for (IntProducer producer : producers) {
                producer.addObserver(observer); // Observer 추가
                threadPool.submit(producer); // ThreadPool 에 task(Runnable) 제출
            }

            threadPool.awaitTermination(2, TimeUnit.SECONDS);

            /**
             * Observable 과 Observer 의 스레드가 동일함을 확인할 수 있다
             *
             * Main Test Thread :Thread[main,5,main]
             * Observable Push Events - Thread :Thread[pool-1-thread-3,5,main] - notiNumber: 3
             * Observable Push Events - Thread :Thread[pool-1-thread-7,5,main] - notiNumber: 7
             * Observer Thread :Thread[pool-1-thread-3,5,main],  arg: 3
             * Observable Push Events - Thread :Thread[pool-1-thread-8,5,main] - notiNumber: 8
             * Observer Thread :Thread[pool-1-thread-8,5,main],  arg: 8
             * Observable Push Events - Thread :Thread[pool-1-thread-1,5,main] - notiNumber: 1
             * Observer Thread :Thread[pool-1-thread-1,5,main],  arg: 1
             * Observable Push Events - Thread :Thread[pool-1-thread-2,5,main] - notiNumber: 2
             * Observer Thread :Thread[pool-1-thread-2,5,main],  arg: 2
             * Observable Push Events - Thread :Thread[pool-1-thread-5,5,main] - notiNumber: 5
             * Observer Thread :Thread[pool-1-thread-5,5,main],  arg: 5
             * Observable Push Events - Thread :Thread[pool-1-thread-4,5,main] - notiNumber: 4
             * Observer Thread :Thread[pool-1-thread-4,5,main],  arg: 4
             * Observable Push Events - Thread :Thread[pool-1-thread-6,5,main] - notiNumber: 6
             * Observer Thread :Thread[pool-1-thread-6,5,main],  arg: 6
             * Observer Thread :Thread[pool-1-thread-7,5,main],  arg: 7
             * Observable Push Events - Thread :Thread[pool-1-thread-9,5,main] - notiNumber: 9
             * Observer Thread :Thread[pool-1-thread-9,5,main],  arg: 9
             *
             * */
        }

        private static List<IntProducer> createProducers() {
            return IntStream.range(1, 20)
                .mapToObj(number -> Integer.valueOf(number))
                .map(IntProducer::new)
                .toList();
        }

        static class IntProducer extends Observable implements Runnable {

            private final int notiNumber;

            IntProducer(int notiNumber) {
                this.notiNumber = notiNumber;
            }

            @Override
            public void run() {
                System.out.printf("Observable Push Events - Thread :%s - notiNumber: %d",
                    Thread.currentThread(), this.notiNumber);

                setChanged();
                notifyObservers(notiNumber);
            }
        }

        static class IntObserver implements Observer {

            private int state = 0;

            @Override
            public void update(Observable o, Object arg) {
                state = (Integer) arg;
                System.out.printf("Observer Thread :%s,  arg: %s, state: %d",
                    Thread.currentThread(), arg, state);
            }
        }
    }
}
