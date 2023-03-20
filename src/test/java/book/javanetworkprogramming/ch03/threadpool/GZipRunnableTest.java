package book.javanetworkprogramming.ch03.threadpool;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GZipRunnableTest {

    private static final int THREAD_COUNT = 4;
    private final String[] args = new String[]{"/Users/leeWhyy/javaBack/javaPractice/src/main/java/book/javanetworkprogramming/ch03/thread/abc.txt"};

    @Test
    @DisplayName("")
    void testWithExecutors() {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

        for(String fileName: args) {
            File f = new File(fileName);

            if(f.exists()) {
                if(f.isDirectory()) {
                    File[] files = f.listFiles();
                    for(int i = 0; i <files.length ; i++) {
                        if(!files[i].isDirectory()) {
                            GZipRunnable task = new GZipRunnable(files[i]);
                            pool.submit(task);
                        }
                    }
                } else {
                    GZipRunnable task = new GZipRunnable(f);
                    pool.submit(task);
                }
            }
        }

        pool.shutdown();
    }
}