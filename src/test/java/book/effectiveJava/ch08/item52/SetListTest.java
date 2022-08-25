package book.effectiveJava.ch08.item52;

import java.util.ArrayList;
import java.util.TreeSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SetListTest {

    private final SetList setList
        = new SetList(new TreeSet<>(), new ArrayList<>());

    int[] datas = new int[]{-2, -1, -0, 1, 2};

    int[] toBeRemoved = new int[]{1, 2};

    int[] expected = new int[]{-2, -1};

    @BeforeEach
    void setUp() {
        for (int data : datas) {
            setList.addToSet(data);
            setList.addToList(data);
        }
    }

    @Nested
    @DisplayName("remove 를 호출하며 기본타입 매개변수를 전달하는 상황") // Context
    class PrimitiveArgumentTest {

        @Test
        @DisplayName("S Set 의 remove 를 호출하면, 해당 매개변수에 해당하는 원소를 제거한다")
        void removeFromSetSuccess() {
            for (int numb : toBeRemoved) {
                setList.removeFromSet(numb);

                Assertions.assertThat(
                        setList.isContainedInSet(numb)) // 제거 되어 contain 여부 확인시 false 를 리턴한다
                    .isFalse();
            }
        }

        @Test
        @DisplayName("F List 의 remove 를 호출하면, 해당 매개변수에 해당하는 인덱스를 가진 원소를 제거하여, 해당 매개변수와 동일한 원소를 제거하는 것은 실패한다")
        void removeFromSetFail() {
            for (int numb : toBeRemoved) {
                setList.removeFromList(numb);

                Assertions.assertThat(setList.isContainedInList(numb))
                    .isTrue();
            }
        }
    }

    @Nested
    @DisplayName("remove 를 호출하며 오토박싱 Integer 매개변수를 전달하는 상황")
    class WrapperTypeArgumentTest {

        @Test
        @DisplayName("S Set 의 remove 를 호출하면, 해당 매개변수에 해당하는 원소를 제거한다")
        void removeFromSetSuccess() {
            for (int numb : toBeRemoved) {
                setList.removeFromSet(Integer.valueOf(numb)); // 일부러 상황을 명시적으로 보여주기 위해 boxing 을 해 주었음 ( 호출된 메소드에서는 unboxing 이 일어남)

                Assertions.assertThat(setList.isContainedInSet(numb))
                    .isFalse();
            }
        }

        @Test
        @DisplayName("S List 의 remove 를 호출하면, 해당 매개변수에 해당하는 값을 가진 원소를 제거한다")
        void removeFromSetFail() {
            for (int numb : toBeRemoved) {
                setList.removeFromListUsingInteger(Integer.valueOf(numb)); // 일부러 상황을 명시적으로 보여주기 위해 boxing 을 해 주었음 ( 호출된 메소드에서는 래퍼타입을 사용)

                Assertions.assertThat(setList.isContainedInList(numb))
                    .isFalse();
            }
        }
    }

}