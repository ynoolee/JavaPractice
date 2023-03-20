package book.javanetworkprogramming.ch03.thread;


public class PrintObject {
    public void synchronizedPrint() {
        synchronized (System.out) {
            System.out.print("abcdefghijklmnopqrstuvwxyz");
        }
    }
    public void nonSynchronizedPrint() {
        System.out.print("abcdefghijklmnopqrstuvwxyz");
    }
}
