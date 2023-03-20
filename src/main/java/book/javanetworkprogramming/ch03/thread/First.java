package book.javanetworkprogramming.ch03.thread;

public class First {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread("My thread");
        t.start();

        Thread.getAllStackTraces().keySet().stream()
            .forEach(k-> System.out.println("Thread :" + k));

        System.out.println("END");

        Thread otherThread = new Thread( () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Second Thread");
        otherThread.start();


        Thread.sleep(500);
        Thread.getAllStackTraces().keySet().stream()
            .forEach(k-> System.out.println("Thread :" + k));

    }

}
