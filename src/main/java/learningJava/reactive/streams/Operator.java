package learningJava.reactive.streams;

import java.util.Iterator;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.BiFunction;
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


2. sum 만들어보기
 map 과는 특성이 다르다
 map 은 upstream 으로부터 데이터들이 날아오면 그 데이터들을 가공하기는 하지만 각각을 모두 다시 down 으로 보내줬는데
 sum 은 upstream 으로부터 데이터들이 날아와도 이들을 계산만 하고 있다가, 합계만 down 으로 주는 것.


 3. reduce 만들어보기

 * */
public class Operator {

    public static void main(String[] args) {

        Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10)
            .collect(Collectors.toList()));
//        Publisher<Integer> mapPub = mapPub(pub, s -> s * 10); // src 가 되는 publisher 를 가지고 , 거기서 발송되는 항목들에 대한 function 을 적용한 스트림으로 변경한다
//        Publisher<Integer> sumPub = sumPub(pub);
        // 초기 데이터가 있음. 초기데이터에 어떤 함수를 갖고 연산을 해서 , 그 결과를 가지고 두 번째 데이터 연산을 하고... 최종 데이터에 대해서도 연산 한 그 결과만을 리턴하는 것.
        Publisher<Integer> reducePub = reducePub(pub, 0, (BiFunction<Integer, Integer, Integer>)(a,b) -> a + b);

        reducePub.subscribe(logSub());
    }

    private static Publisher<Integer> reducePub(Publisher<Integer> pub, int init,
        BiFunction<Integer, Integer, Integer> f) {

        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                pub.subscribe(new DelegateSub(sub){

                    int result = init;

                    @Override
                    public void onNext(Integer i) {
                        result = f.apply(result, i);
                        System.out.println(String.format("onNext : %d", result));
                    }

                    @Override
                    public void onComplete() {
                        sub.onNext(result);
                        sub.onComplete();
                    }
                });
            }
        };
    }

    private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                pub.subscribe(new DelegateSub(sub){
                    int sum = 0;

                    @Override
                    public void onNext(Integer i) {
                        // onNext 에서는 현재 넘어온 데이터가 마지막 데이터인지를 알 방법이 없다
                        sum += i;
                        // 이렇게 한 번 더한 값을 그대로 sub.onNext(sum) 로 전달 해 버리면
                        // 이거는 sum 이 애초에 업스트림으로부터 온 데이터를 모두 더해서 그 결과 하나만을 down 으로 보내는 것과는 맞지 않게 된다
                        // 그렇다면 sub.onNext 는 어디에서 해야할까?
                    }

                    @Override
                    public void onComplete() {
                        // 바로 여기!
                        // publisher 로부터 데이터가 모두 전달하고 나면 onComplete 이 호출된다
                        // 여기라고 onNext 를 호출하면 안되거나 하지는 않다.
                        sub.onNext(sum);
                        sub.onComplete();
                    }
                });
            }
        };
    }


    private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> f) {
        return new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> sub) {
                // mapPub publisher 내부에서 새로운 Subscriber 를 추가
                // 가장 좌측의 Publisher 로부터 데이터를 중간에서 받아오는 애
                pub.subscribe(new DelegateSub(sub) {
                    @Override
                    public void onNext(Integer item) {
                        sub.onNext(f.apply(item));
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
