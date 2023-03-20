package book.javanetworkprogramming.ch03.thread.callback.v1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CallbackDigest implements Runnable {

    private final String filename;

    public CallbackDigest(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream fin = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(fin, sha);
            while (din.read() != -1) ; // read entire file
            din.close();
            byte[] digest = sha.digest();

            System.out.println(
                "CallbackDigest running thread : " + Thread.currentThread().getName());

            // 작업을 수행하는 측에서 직접 처리가 끝나면 "메인 프로그램에서 미리 정해놓은 메소드를 호출"
            // 현재 스레드에서 CallbackDigestUserInterface 클래스에 위치한 메소드를 호출
            CallbackDigestUserInterface.receiveDigest(digest, filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
