package book.javanetworkprogramming.ch03.thread.callback.v1;

import book.javanetworkprogramming.ch03.DatatypeConverter;

public class CallbackDigestUserInterface {

    public static void main(String[] args) {
        args = new String[]{"/Users/leeWhyy/javaBack/javaPractice/src/main/java/book/javanetworkprogramming/ch03/thread/abc.txt"};
        CallbackDigest[] threads = new CallbackDigest[args.length];

        for (int i = 0; i < args.length; i++) {
            CallbackDigest cb = new CallbackDigest(args[i]);
            new Thread(cb).start();
        }
    }

    // 콜백 스레드에서 이 정적 메소드를 호출 한다
    public static void receiveDigest(byte[] digest, String filename) {
        System.out.println("receiveDigest running thread : " + Thread.currentThread().getName());
        StringBuilder result = new StringBuilder(filename);
        result.append(": ");
        result.append(DatatypeConverter.byteToHexString(digest));
        System.out.println(result);
    }
}
