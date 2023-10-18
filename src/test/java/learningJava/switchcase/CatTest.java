package learningJava.switchcase;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CatTest {

    @Test
    void givenCat_thenReturnProperSound() {
        Cat cat = new SavanaCat();

        String whatSound = switch (cat.type()) {
            case KOREAN, SAVANA -> cat.meowSound();
            case RUSSIAN_BLUE -> cat.meowSound();
        };

        String whatSound2 = null;

        switch (cat.type()) {
            case SAVANA, KOREAN:
                whatSound2 = cat.meowSound();
                break;
        }
        ;

        assertThat(whatSound).isEqualTo("캭");
        assertThat(whatSound2).isEqualTo("캭");
    }

    @Test
    void 전통적인_switch_기본은_전체가_하나의_scope_라서_변수선언이_중복될수_없다() {
        Cat cat = new SavanaCat();

        switch (cat.type()) {
            // block 으로 독립적인 scope -> 이 temp 는 RUSSIAN_BLUE label 과 다름
            case KOREAN: {
                int temp = 10;
                System.out.println(temp);
                break;
            }
            // 전통적인 switch 문을 기본적으로 이렇게 사용하면 , switch scope 에 선언된 것
            case RUSSIAN_BLUE:
                int temp = 20;
                System.out.println(temp);
                break;
            // 따라서 중복선언이 됨 -> 컴파일 에러
            case SAVANA:
//                int temp = 30;
        }
    }

    @Test
    void 여러_label을_콤마로_연결_가능() {
        Cat cat = new SavanaCat();

        String whatSound = switch (cat.type()) {
            case KOREAN, SAVANA, RUSSIAN_BLUE:
                yield cat.meowSound();
        };

        assertThat(whatSound).isEqualTo("캭");
    }

    @Test
    void Java_switch_는_Kotlin_when_과_달리_여러_타입이_올_수_없다() {
        Set<String> set = Set.of("yellow", "blue");
        Set<String> set1 = Set.of("yellow", "blue");
        Set<String> set2 = Set.of("yellow", "blue");

//        switch (set) {
//            case set1 : {
//
//            }
//        }
    }

}
