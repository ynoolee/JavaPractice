package book.effectiveJava.ch12.item88;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtil {
    public static byte[] serialize(Object o) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(bao).writeObject(o);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return bao.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        try {
            return new ObjectInputStream(
                new ByteArrayInputStream(bytes)).readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
