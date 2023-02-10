package learningJava.reactive.streams;

import java.util.Iterator;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Publisher 의 역할 : data stream 을 계속해서 만들어내는 Provider 역할
// Mono 와 Flux 역시 그런 역할인건가?
/**
 Publisher -> Data -> Subscriber
 이 과정에서 중간에 여러 operation 을 수행할 수 있다
 Java Stream 처럼 생각 해 보면 된다.

 1. map 을 해  -mapPub
 (위에서 아래로 간다 : downStream, 아래에서 위로 간다 : upStream. subscriber 가 보기에는 mapPub 도 publisher 이다
 Publisher -> Data1 -> Operator -> Data2 -> Operator2 -> Subscriber
 Publisher -> Data1 -> mapPub -> Data2  -> Subscriber
                             <- subscribe(Subscriber)
                            -> onSubscribe(s)
                            -> onNext
                            -> onNext
                            -> onComplete




 * */
public class Operator {

    public static void main(String[] args) {

        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10)
            .collect(Collectors.toList()));
        Publisher<Integer> mapPub = mapPub(pub, s -> s * 10); // src 가 되는 publisher 를 가지고 , 거기서 발송되는 항목들에 대한 function 을 적용한 스트림으로 변경한다
        Publisher<Integer> map2Pub = mapPub(mapPub, s -> s * 10); // 최종적으로 Subscriber 에서 100배가 된 값을 받을 수 있게한다
        mapPub.subscribe(logSub());

    }

    private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> f) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                // mapPub publisher 내부에서 새로운 Subscriber 를 추가
                // 가장 좌측의 Publisher 로부터 데이터를 중간에서 받아오는 애
                pub.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        sub.onSubscribe(subscription);
                    }

                    @Override
                    public void onNext(Integer item) {

                        sub.onNext(f.apply(item));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        sub.onError(throwable);
                    }

                    @Override
                    public void onComplete() {
                        sub.onComplete();
                    }
                });
            }
        };
    }

    private static Subscriber<Integer> logSub() {
        return new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println(String.format("onSubscribe:"));
                // Subscription 이 넘어오면 가장 먼저 해야 할 일 : request 를 해야 한다.
                // Subscriber 에게 Subscription 이 전달된 것 = 구독 채널이 만들어진 것.
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(String.format("onNext: %d", item));
            }

            // Publisher 측에서는 예외가 발생하더라도 예외를 throw 하는게 아니라, 반드시 Subscriber 의 onError 메소드를 통해 Throwable 타입의 객체로 넘겨줘야 한다. (우아하게~)
            @Override
            public void onError(Throwable throwable) {
                System.out.println(String.format("onError:"));

            }

            @Override
            public void onComplete() {
                System.out.println(String.format("onComplete:"));

            }
        };
    }

    private static Publisher<Integer> iterPub(Iterable<Integer> iter) {
        return new Publisher<Integer>() {
            // Publisher 가 publish 할 src 라고 생각

            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                // subscribe 가 호출되면 그 때 부터 데이터를 보내야 한다.
                // subscriber 의 구독이 일어날 때 마다 이 과정이 일어난다.
                sub.onSubscribe(new Subscription() {

                    @Override
                    public void request(long n) {
                        try {
                            // n 무시하고 다 넘기는 거로 해 보자
                            iter.forEach(s -> sub.onNext(s));
                            // ( 사실 publisher 측에서는,데이터를 하나 날리고 나면 이 구독이 cancle 되지는 않았는지도 체크 해 봐야 함 )
                            // publisher 측에서는 자신이 가진 데이터를 모두 publish 했다 하면, 반드시 onComplete, onError 를 해야 한다
                            sub.onComplete();
                        } catch (Throwable t) {
                            sub.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {
                        // subscriber 측에서 완료가 아님에도 데이터를 더이상 받고 싶지 않을 때 cancel 을 날릴 수도 있다.
                    }
                });
            }
        };
    }
}
