package book.effectiveJava.ch01.item01;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CharacterTest {

    private final FontFactory fontFactory = new FontFactory();

    @Test
    @DisplayName("동일한 폰트 같은 크기의 서로 다른 색을 가진 캐릭터 생성시 동일한 폰트 객체를 사용한다")
    void test1() {
        char bigA = 'a';
        char smallA = 'a';

        String bigAColor = "red";
        String smallAColor = "black";
        String fontrequirements = "SandleFont 14";

        Character bigAChar = new Character(bigA, bigAColor, fontFactory.getFont(fontrequirements));
        Character smallAChar = new Character(smallA, smallAColor,
            fontFactory.getFont(fontrequirements));

        Assertions.assertThat(bigAChar.getFont())
            .isEqualTo(smallAChar.getFont());
    }
}