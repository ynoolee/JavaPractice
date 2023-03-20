package book.javanetworkprogramming.ch03.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

public class LogFile {
    private Writer out;

    public LogFile(File f) throws IOException {
        FileWriter fw = new FileWriter(f);
        this.out = new BufferedWriter(fw);
    }

    public void writeEntry(String message) throws IOException {
        Date date = new Date();
        out.write(date.toString());
        out.write('\t');
        out.write(message);
        out.write("\r\n");
    }

    public void close() throws IOException {
        out.flush();
        out.close();
    }
}
