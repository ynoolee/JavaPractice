package book.effectiveJava.ch04;

import book.effectiveJava.item39.repeatable.ExceptionTest;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ConcurrentModificationTest {

    private List<Integer> list;

    private final int toBeRemovedElement = 11;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(toBeRemovedElement);
    }

    @Test
    public void remove_using_iterator_while_iterating_success() {
        Iterator<Integer> it = list.iterator();

        while (it.hasNext()) {
            if (it.next() == toBeRemovedElement) {
                it.remove();
            }
        }

        Assertions.assertThat(list.contains(toBeRemovedElement))
            .isFalse();
    }

    @Test
    @ExceptionTest(ConcurrentModificationException.class)
    public void access_using_other_iterator1_object_while_iterating_with_iterator2_object_fail() {
        Iterator<Integer> it1 = list.iterator();
        Iterator<Integer> it2 = list.iterator();

        while (it1.hasNext()) {
            if (it1.next() == toBeRemovedElement) {
                it1.remove();
            }
        }

        if (it2.hasNext()) {
            Assertions.assertThatExceptionOfType(ConcurrentModificationException.class)
                .isThrownBy(it2::next);
        }
    }

}
