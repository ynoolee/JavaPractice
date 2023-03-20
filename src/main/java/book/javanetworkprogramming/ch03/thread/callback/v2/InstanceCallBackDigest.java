package book.javanetworkprogramming.ch03.thread.callback.v2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InstanceCallBackDigest implements Runnable{

    private final String filename;

    private final InstanceCallBackDigestUserInstance callBackDigestUserInstance;

    public InstanceCallBackDigest(String filename,
        InstanceCallBackDigestUserInstance callBackDigestUserInstance) {
        this.filename = filename;
        this.callBackDigestUserInstance = callBackDigestUserInstance;
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

            callBackDigestUserInstance.receiveDigest(digest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
