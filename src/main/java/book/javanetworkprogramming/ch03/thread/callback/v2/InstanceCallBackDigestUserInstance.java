package book.javanetworkprogramming.ch03.thread.callback.v2;

import java.util.Arrays;

public class InstanceCallBackDigestUserInstance {
    private final String filename;
    private byte[] digest;

    public InstanceCallBackDigestUserInstance(String filename) {
        this.filename = filename;
    }

    public void calculateDigest() {
        InstanceCallBackDigest instanceCallBackDigest = new InstanceCallBackDigest(filename, this);
        Thread thread = new Thread(instanceCallBackDigest);
        thread.start();
    }

    public void receiveDigest(byte[] digest) {
        System.out.println("receiveDigest running thread : " + Thread.currentThread().getName());
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(
            "InstanceCallBackDigestUserInstance{");
        sb.append("filename='").append(filename).append('\'');
        sb.append(", digest=").append(Arrays.toString(digest));
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        for (String filename: args) {
            InstanceCallBackDigestUserInstance instance =
                new InstanceCallBackDigestUserInstance(filename);
            instance.calculateDigest();
        }
    }
}
