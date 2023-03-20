package book.javanetworkprogramming.ch03.Future;

import book.javanetworkprogramming.ch03.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileShareTask extends Thread {

    private String fileName;

    public FileShareTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            FileInputStream in = new FileInputStream(fileName);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while(din.read() != -1) ; // read entire file
            din.close();

            byte[] digest = sha.digest();
            System.out.println("input : " );
            System.out.println(DatatypeConverter.byteToHexString(digest));
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
