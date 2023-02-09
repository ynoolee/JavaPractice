package learningJava.reactive.streams;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ob {

    // Iterable <---> Observable
    // (duality) : 두가지가 궁극적으로 기능은 같은데 반대방향으로 표현한 것.
    // Iterable : Pulling 방식  - iterable 에서는 it.next() 를 통해 , 즉, 소스를 쓰는 쪽에서 끌어오고 있음   -> int i = it.next();   (pull)
    // Observable : Push 방식 - 데이터나 이벤트를 갖고 있는 소스 쪽에서 push 해 주는 것                      -> notifyObservers(i)   (push)
    // DATA method(void) <---> void method(DATA) 이지만 둘은 동일한 기능을 한다
    public static void iterate(String[] args) {

        // List<T> implement Iterable<T>
        Iterable<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }

        // Iterable 구현체는 For each loop 를 사용할 수 있다
        // JAVA 의 for each 는 Iterable 타입을 받는 것이다
        for (Integer i : list) {
            System.out.println(i);
        }

        // Iterable 도 익명 메소드가 하나만 존재 -> 람다로 만들 수 있음
        // Iterator 를 생성하는 메소드를 구현만 해 주면 됨.
        // Iterator : 순회할 수 있도록 하는 도구 ( Iterable 이 가지고 있는 원소를 하나씩 순회할 때 이거를 사용해! )
        Iterable<Integer> iter = () ->
            // Iterator 구현을 위해서는 익명 클래스를 사용해야함
            new Iterator<>() {
                int i = 0;

                final static int MAX = 10;

                @Override
                public boolean hasNext() {
                    return i < MAX;
                }

                @Override
                public Integer next() {
                    return ++i;
                }
            };

        for (Integer i : iter) {
            System.out.println(i);
        }
    }


    // Java 9 부터는 reactive stream 으로 인해 JAVA 의 Observable deprecated
    // reactive stream 에서는 Publisher - Subscriber
    // Observable : 이벤트(data)를 만들어내야함. 그 데이터 만드는 애들을 실행하는 것이 필요한데, 예시를 위해 Runnable 을 구현하도록 해 보겠음. ( 비동기적으로 동작 시킬 거라 )
    // multicast 가 가능하다 ( 여러개의 옵저버에게 보낼 수 있다)
    //
    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {
            System.out.println(
                String.format("Observable Push Events - Thread :%s ", Thread.currentThread()));
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }

    static class IntObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            System.out.println(String.format("Thread :%s,  arg: %s", Thread.currentThread(), arg));
        }
    }


    public static void observer() {
        // source : event/data 를 던진다    ---> Observer(target) 에게. 따라서 Observer 는 Observable 에 등록되어 있어야 함. Observer 는 여러개 등록되어 있을 수 있다.
        IntObserver observer = new IntObserver();

        IntObservable io = new IntObservable();
        io.addObserver(observer);

        io.run();

    }

    /*
    main thread 에서 Observable 이 이벤트를 발행하고
    비동기로 실행하는 스레드들에서 발행된 이벤트를 받을 수 있도록 동작 할 수 있다.
    push 방식으로 observer pattern 을 사용하면 별개의 스레드에서 동작하는 코드를 쉽게 만들 수 있다.
    **/
    public static void executeAsync() {
        IntObserver observer = new IntObserver();

        IntObservable io = new IntObservable();
        io.addObserver(observer);

        ExecutorService es = Executors.newFixedThreadPool(10);
        es.execute(io); // 여기서 이렇게 executorService 에 Task 로 전달하려고 IntObservable 을 Runnable 을 구현하였음. 이벤트 발송 작업을 이렇게 비동기로 실행하면 -> 이벤트를 받는 Observer 들 역시 비동기로 받는다 (그런데 현재 스레드 출력해보니, 발급 스레드와 받는 스레드가 동일한 것 같다)

        System.out.println(String.format("Thread :%s", Thread.currentThread()));
        es.shutdown();

        /**
         * Thread :Thread[main,5,main]
         * Observable Push Events - Thread :Thread[pool-1-thread-1,5,main]
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 1
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 2
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 3
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 4
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 5
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 6
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 7
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 8
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 9
         * Thread :Thread[pool-1-thread-1,5,main],  arg: 10
         * */
    }


    public static void main(String[] args) {
        executeAsync();
    }

}
