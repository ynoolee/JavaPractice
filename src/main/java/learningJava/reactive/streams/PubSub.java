package learningJava.reactive.streams;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.TimeUnit;

/*
Observer pattern 의 문제점
1. complete 의 개념이 없다. - OBservable 이 데이터를 던지고 "끝" 이라는 개념이 없다. 마지막 데이터에 특별한 문자를 붙여준다. 내가 데이터를 다 보냈어 라는 완료 개념이 없다
2. Error : 예외가 발생하는 경우 -> bug 때문에 발생하면 빨리 해결해야한느 문제지만, 외적인 상황 때문에 발생하는 경우는 복구가 가능한 경우임 -> 그런데 예외가 발생하는 경우 Observable-Observer 를 사용하고 스레드를 분리하고 있는 구조에서는, 예외가 전파되는 방식, 받은 예외를 어떻게 처리해야하는가, 예외를 받았으나 retry 하거나 콜백등을 통해 다른 뭔가를 하고 싶다. 이런 부분에 대한게 이 패턴에는 녹아져 있지 않다.

이 두가지를 추가해 확장된 Observer 를 만들었다.
reactive-streams 공식문서 볼수 있다. reactive streams 에 대한 표준.
표준을 지킨 것들(ex-RxJAVA) 끼리는 상호호완성을 갖고 있다.


Publisher 와 Subscriber 가 두 가지 핵심 컴포넌트들이다

* 공식 문서에 따르면 (https://github.com/reactive-streams/reactive-streams-jvm#api-components)

Publisher.subscribe(subscriber) 의 호출에 대한 응답으로 가능한 호출 시퀀스는 다음 프로토콜을 따르도록 한다
OnSubscribe(1번) onNext*(여러 번 가능) (onError | onComplete)?(선택적으로)

즉 onSubscribe 는 subscribe 를 하는 즉시 호출해 줘야 한다.

onError 또는 onComplete 가 호출되는 순간 subscription 은 끝나는 것이다
*
event 에 대응하는 방식이다.

Reactive Streams
- 더 나은 옵저버 패턴인 Observable
- Scheduler ( 비동기적으로 또는 동시에 병렬적으로 작업 처리 )

사실 이것만 보면 Iterable 을 사용해서 serial 하게 처리할 수도 있는 거겠지만, "동시성을 갖고 복잡하게 처리하는 코드" 를 간결하게 작성할 수 있도록 하는 것이 우리가 배우는 reactive stream 이다
* */
public class PubSub {

    public static void one() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        // Observable --> Publisher
        // Observer -> Subscriber

        Iterable<Integer> it = Arrays.asList(1, 2, 3, 4, 5);

        Publisher<Object> publisher = new Publisher<>() {
            @Override
            public void subscribe(Subscriber<? super Object> subscriber) {
                Iterator<Integer> i = it.iterator();

//                즉 onSubscribe 는 subscribe 를 하는 즉시 호출해 줘야 한다.
                // 전에 본 Observer 는 그냥 addObserver 를 하면 끝이었는데
                // 여기서는 onSubscribe 를 사용해서 콜백을 하는 것이다.
                // Subscription 이 뭐지? -> Subscriber 와 Publisher 를 중간에서 연결해주는 것이다. 구독이라는 정보를 가진 객체라 보면 된다. 중계역할. Publisher - Subscriber 사이에 속도 차이가 발생할 경우에, 이를 조절해주는 것을 Reactive Streams 에서는 Subscription 을 통해 처리하고 있다.
                // Subscriber 가 Publisher 에게 "너가 가진 데이터 다 보내줘" 라고 할 수도 있고 " 나 지금 바쁘니, 일단 10개만 주고 나머진 대기하고있어" 라고 요청할 수 있다 -> request 메소드. 근데 리턴타입이 void 임. 그냥 요청을 하기 위한 용도인것이지, 요청했다고 나한테 무슨 응답을 해 달라는 거는 아님.
                // 실제로 publisher 가 subscriber 에게 데이터를 보내는 것은 onNext() 를 통한다.
                subscriber.onSubscribe(new Subscription() {

                    @Override
                    public void request(final long n) {
                        // 이렇게 하면 n 과 상관없이 onNext 를 여러번 호출하게 되는 것이고
//                        while(true) {
//                            if (i.hasNext()) {
//                                subscriber.onNext(i.next());
//                            } else {
//                                subscriber.onComplete();
//                                break;
//                            }
//                        }

                        // 진행이나 결과에 신경을 쓰지 않는다면
                        es.execute(() -> {
                                int j = 0;
                                try {
                                    while (j++ < n) {
                                        if (i.hasNext()) {
                                            subscriber.onNext(i.next());
                                        } else {
                                            subscriber.onComplete();
                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                    // Publisher 쪽에서 에러 난 경우 -> Subscriber 는 갑자기 에러가 확 날라오는게 아니라, onError 라는 것을 통해 (우아하게) 날아옴.
                                    subscriber.onError(e);
                                }

                            }

                        );

                        // 만약에 진행상황을 나중에 체크하고 싶다면, Callable 형태로 작성 + Future 로 받을 수 있다
                        // Future : 비동기 작업 결과를 담고 있는 애.
                        // 현재는 결과를 받아볼 필요가 없긴 하다. event 방식으로 날려버리니까.
                        // 그런데 Future 는 중간에 cancle 이 가능하다. 중간에 subscriber 가 cancle 을 하고 싶은 경우, Future 를 통해 interrupt 를 날리면 된다.

//                        Future<?> future = es.submit(() -> {
//                            int j = 0;
//                            try {
//                                while (j++ < n) {
//                                    if (i.hasNext()) {
//                                        subscriber.onNext(i.next());
//                                    } else {
//                                        subscriber.onComplete();
//                                        break;
//                                    }
//                                }
//                            } catch (Exception e) {
//                                // Publisher 쪽에서 에러 난 경우 -> Subscriber 는 갑자기 에러가 확 날라오는게 아니라, onError 라는 것을 통해 (우아하게) 날아옴.
//                                subscriber.onError(e);
//                            }
//
//                        });

                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        // Subscription 을 통해 Publisher 와 계속 관계를 맺고 있을 수 있는건가?
        Subscriber<Object> subscriber1 = new MySubscriber();
        Subscriber<Object> subscriber2 = new MySubscriber();

        // 이런식으로 작성하게 되면 main 스레드가 아닌 ForkJoinPool 의 스레드에서 onSubscribe() 가 호출될 것이다
        CompletableFuture.runAsync(() -> publisher.subscribe(subscriber1));
        CompletableFuture.runAsync(() -> publisher.subscribe(subscriber2));
//        publisher.subscribe(subscriber1); // 이 순간 onsbuscribe 호출되고 끝. 그럼 내가 데이터를 받으려면 어떻게 해야하지? 나는 데이터를 어떻게(데이터 발급속도와, 데이터를 받아 처리할 수 있는 속도가 서로 다르기 때문에 - 생성자체를 지연시킬 수도 있음)  받겠어 라는 나의 의도를 얘기해야 한다
//        publisher.subscribe(subscriber2);

        es.awaitTermination(3, TimeUnit.SECONDS);
        es.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        one();
    }

    private static class MySubscriber implements Subscriber {

        // 구독정보 Subscription 을 저장해두고
        private Subscription subscription;

        /*
         * onSubscribe 는 어디서 동작하는가?
         * subscribe() 를 한 스레드에서 동작한다 -> 이 경우는 main thread 가 될 것이다
         * 그리고 해당 스레드 안에서 request 를 보내게 된다 ( 당연함. onSubscribe 내부에서 subscription 에 대한 요청을 보내니까 )
         * 이 내부에서 새로운 스레드를 만들어서 그 안에서 request 를 날리도록 하는 것도 안된다. subscribe 스레드에서 request 까지 날려야 한다.
         *
         * */
        @Override
        public void onSubscribe(Subscription subscription) {
            System.out.println("Subscriber onSubscribe : " + Thread.currentThread().getName());
//                subscription.request(Long.MAX_VALUE);
            this.subscription = subscription;
            this.subscription.request(
                1);  // 나는 1개까지 밖에 받을 수 없어 -> 그럼 1개 처리하고 나머지 4개는 어디서 받지? onNext
        }

        @Override
        public void onNext(Object item) {
            System.out.println(Thread.currentThread().getName() + " onNext " + item);
            // 뭔가 복잡한 처리 - Subscriber 의 현재 상황을 계산 -> 다음 request 로 요청할 n 값 계산
            // 버퍼를 절반정도로 유지하도록 request 를 하게 코드를 작성하는 등
            // 뒤에는 스케줄러가 등장함 -> 비동기적으로 병렬적으로 작업을 수행할 수 있다.
            this.subscription.request(1);
            // onNext 에서는 Subscriber 의 상황에 따라, 다음 request 를 호출할지 말지를 결정하도록 코드를 작성할 수도있다
        }

        // Observer 와 달리 Complete 에 대한 처리가 가능함
        @Override
        public void onError(Throwable throwable) {
            // 그럼 에러를 받아보고, 현재 Subscriber 상태에 따라, 처리 가능한 것인지 등등을 판단해보고 처리 할 수 있을 것. ( 재시도를 할 수도 있고 )
            System.out.println("onError");
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete");
        }

    }
}

/* 비동기적으로 날리는 코드
    publisher 가 혼자 데이터를 다 날리니까 하는 일이 많아 보인다
    "publisher 가 데이터를 날려주는 것" 을 동시에 여러 스레드에서 수행하는 것도 가능한가?
    스펙상으로 불가능하게 되어있다.
    subscriber 는 데이터가 sequential 하게 날아올 것을 기대하고, 그것과 관련된 동시성 문제를 신경쓰지 않도록 하고 있다.
    subscribe 이후에 데이터가 날아오는 것 자체는, 한 순 간에 하나의 스레드에서만 날아올 것으로 기대하고 있으면 된다.
    subscriber 하나에 대해서 onNext 하는 것은 하나의 스레드에서 쭉 이어지도록 하였다.
    동시성과 관련된 이슈가 훨씬 많이 줄어들었다.
    물론 동시에 여러 subscriber 가 생기는 것은 가능하다. ( 이 때는 각각의 스레드로 나누어 간다 )

*/

