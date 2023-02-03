package learningJava.reactive;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/*
Observer pattern 의 문제점
1. complete 의 개념이 없다. - OBservable 이 데이터를 던지고 "끝" 이라는 개념이 없다. 마지막 데이터에 특별한 문자를 붙여준다. 내가 데이터를 다 보냈어 라는 완료 개념이 없다
2. Error : 예외가 발생하는 경우 -> bug 때문에 발생하면 빨리 해결해야한느 문제지만, 외적인 상황 때문에 발생하는 경우는 복구가 가능한 경우임 -> 그런데 예외가 발생하는 경우 Observable-Observer 를 사용하고 스레드를 분리하고 있는 구조에서는, 예외가 전파되는 방식, 받은 예외를 어떻게 처리해야하는가, 예외를 받았으나 retry 하거나 콜백등을 통해 다른 뭔가를 하고 싶다. 이런 부분에 대한게 이 패턴에는 녹아져 있지 않다.

이 두가지를 추가해 확장된 Observer 를 만들었다.
reactive-streams 공식문서 볼수 있다. reactive streams 에 대한 표준.
표준을 지킨 것들(ex-RxJAVA) 끼리는 상호호완성을 갖고 있다.


Publisher 와 Subscriber 가 두 가지 핵심 컴포넌트들이다

공식 문서에 따르면 (https://github.com/reactive-streams/reactive-streams-jvm#api-components)

Publisher.subscribe(subscriber) 의 호출에 대한 응답으로 가능한 호출 시퀀스는 다음 프로토콜을 따르도록 한다
OnSubscribe(1번) onNext*(여러 번 가능) (onError | onComplete)?(선택적으로)

즉 onSubscribe 는 subscribe 를 하는 즉시 호출해 줘야 한다.

event 에 대응하는 방식이다.
* */
public class PubSub {

    public static void one() {
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
                    public void request(long n) {
                        // 이렇게 하면 n 과 상관없이 onNext 를 여러번 호출하게 되는 것이고
//                        while(true) {
//                            if (i.hasNext()) {
//                                subscriber.onNext(i.next());
//                            } else {
//                                subscriber.onComplete();
//                                break;
//                            }
//                        }

                        while (n-- > 0) {
                            if (i.hasNext()) {
                                subscriber.onNext(i.next());
                            } else {
                                subscriber.onComplete();
                                break;
                            }
                        }

                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        // Subscription 을 통해 Publisher 와 계속 관계를 맺고 있을 수 있는건가?
        Subscriber<Object> subscriber = new Subscriber<>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
//                subscription.request(Long.MAX_VALUE);
                this.subscription = subscription;
                this.subscription.request(
                    1);  // 나는 1개까지 밖에 받을 수 없어 -> 그럼 1개 처리하고 나머지 4개는 어디서 받지? onNext
            }

            @Override
            public void onNext(Object item) {
                System.out.println("onNext " + item);

                this.subscription.request(1);
            }

            // Observer 와 달리 Complete 에 대한 처리가 가능함
            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        publisher.subscribe(
            subscriber); // 이 순간 onsbuscribe 호출되고 끝. 그럼 내가 데이터를 받으려면 어떻게 해야하지? 나는 데이터를 어떻게(데이터 발급속도와, 데이터를 받아 처리할 수 있는 속도가 서로 다르기 때문에 - 생성자체를 지연시킬 수도 있음)  받겠어 라는 나의 의도를 얘기해야 한다

    }

    public static void main(String[] args) {
        one();
    }
}
