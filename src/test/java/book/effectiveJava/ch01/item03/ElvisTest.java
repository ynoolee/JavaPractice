package book.effectiveJava.ch01.item03;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElvisTest {

    @Test
    @DisplayName("reflection 을 통해 private 생성자를 직접 호출 할 경우, 싱글톤 객체에서는 예외를 발생한다")
    public void given_desreializedObject_whenDesrialize_thenBreakingSingletone()
        throws IOException {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("elvis.obj"))) {
            out.writeObject(Elvis.INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("elvis.obj"))) {
            Elvis elvis3 = (Elvis) in.readObject();

            Assertions.assertThat(elvis3)
                .isNotEqualTo(Elvis.INSTANCE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}