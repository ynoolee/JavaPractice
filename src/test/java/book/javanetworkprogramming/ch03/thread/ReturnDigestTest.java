package book.javanetworkprogramming.ch03.thread;

import book.javanetworkprogramming.ch03.DatatypeConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReturnDigestTest {

    private final String[] args = new String[]{"/Users/leeWhyy/javaBack/javaPractice/src/main/java/book/javanetworkprogramming/ch03/thread/abc.txt"};

    @Nested
    @DisplayName("NPE 가 발생하는 상황 - 스레드 스케줄링 에 따라 실패할 수도 성공할 수도 있는 케이스")
    public class SchedulerDependentTest {

        @Test
        @DisplayName("스레드를 실행하는 코드와 , 스레드로부터의 결과를 출력하는 코드가 매우 근접한 경우 대부분이 NPE 가 발생한다")
        void test1() {
            for (String filename : args) {
                ReturnDigest thread = new ReturnDigest(filename);
                thread.start();
                StringBuilder result = new StringBuilder(filename);
                result.append(": ");
                byte[] digest = thread.getDigest(); // 이 시점에 해당 스레드 인스턴스의 digest 는 아직 null 일 수도 있음
                result.append(DatatypeConverter.byteToHexString(digest)); // NPE 가 발생할 수도 있다
                System.out.println(result);
            }
        }

        @Test
        @DisplayName("일련의 스레드들을 먼저 실행하고, 이후에 이 스레드들을 iterate 하면서, 그 실행 결과를 출력하는 코드 ")
        void test2() {
            ReturnDigest[] threads = new ReturnDigest[args.length];

            for (int i = 0; i < args.length; i++) {
                threads[i] = new ReturnDigest(args[i]);
                threads[i].start();
            }

            for (int i = 0; i < args.length; i++) {
                StringBuilder result = new StringBuilder(args[i]);
                result.append(": ");
                byte[] digest = threads[i].getDigest();
                result.append(DatatypeConverter.byteToHexString(digest));
                System.out.println(result);
                break;
            }

        }
    }

    @Test
    @DisplayName("폴링을 통해 스레드의 작업 결과물이 확실하게 나왔을 때 서야 , 값을 가져와 사용할 수도 있다")
    void test3() {
        ReturnDigest[] threads = new ReturnDigest[args.length];

        for (int i = 0; i < args.length; i++) {
            threads[i] = new ReturnDigest(args[i]);
            threads[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            while (true) {
                byte[] digest = threads[i].getDigest();
                System.out.println(digest);
                if (digest != null) {
                    StringBuilder result = new StringBuilder(args[i]);
                    result.append(": ");
                    result.append(DatatypeConverter.byteToHexString(digest));
                    System.out.println(result);
                    break;
                }
            }
        }
    }

    @Test
    @DisplayName("join 을 통해 다른 스레드가 종료될 때 까지 기다린 후 값을 가져와 사용할 수도 있다")
    void waitForCompletionTest() {
        ReturnDigest[] threads = new ReturnDigest[args.length];

        for (int i = 0; i < args.length; i++) {
            threads[i] = new ReturnDigest(args[i]);
            threads[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            try {
                threads[i].join();

                StringBuffer result = new StringBuffer(args[i]);
                result.append(": ");

                byte[] digest = threads[i].getDigest();

                result.append(DatatypeConverter.byteToHexString(digest));
                System.out.println(result);
            } catch (InterruptedException e) {
                System.err.println("Thread Interrupted before completion");
            }
        }
    }

    @Test
    @DisplayName("인자가 있는 join 을 호출 할 경우 join 뒤로 이어지는 코드를 바로 실행할 수 있는 상태로, 이전과 같이 NPE 가 발생할 수 있다")
    void waitUntilCompletionWitProcessingFollowingCode(){
        ReturnDigest[] threads = new ReturnDigest[args.length];
        long longRunningTime = 5000;

        for (int i = 0; i < args.length; i++) {
            threads[i] = new ReturnDigestLongRunning(args[i], longRunningTime);
            threads[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            try {
                threads[i].join(longRunningTime / 4);

                System.out.println(Thread.currentThread().getState());

                StringBuffer result = new StringBuffer(args[i]);
                result.append(": ");

                byte[] digest = threads[i].getDigest();

                Assertions.assertThatExceptionOfType(NullPointerException.class)
                        .isThrownBy(() -> result.append(DatatypeConverter.byteToHexString(digest)) );
            } catch (InterruptedException e) {
                System.err.println("Thread Interrupted before completion");
            }
        }
    }

    // 마음에 안들지만 테스트용으로 작성 - ReturnDigest 에 sleep 을 추가한 스레드
    private class ReturnDigestLongRunning extends ReturnDigest {

        private final long sleepTime;

        public ReturnDigestLongRunning(String filename, long sleepTime) {
            super(filename);
            this.sleepTime = sleepTime;
        }


        @Override
        public void run() {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }

}