package learningJava.switchcase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnhancedSwitchTest {

    private enum Type {
        CAT,
        DOG,
        BIRD
    }

    @Test
    @DisplayName("기존의 switch 문은 매칭되는 case 에서 종료하기 위한 break 가 없으면 다음 case 또한 실행한다")
    void need_breakStatement() {
        Type type = Type.CAT;
        int checkIntValue = 0;

        switch (type) {
            case CAT:
                checkIntValue = 1;
            case DOG:
                checkIntValue = 2;
            case BIRD:
                checkIntValue = 3;
        }

        Assertions.assertThat(checkIntValue)
            .isNotEqualTo(1);
    }

    @Test
    @DisplayName("향상된 switch 문은 매칭되는 case 에서 종료하기 위한 break 를 하지 않아도 종료한다")
    void no_breakStatement() {
        Type type = Type.CAT;
        int checkIntValue = 0;

        switch (type) {
            case CAT -> checkIntValue = 1;
            case DOG -> checkIntValue = 2;
            case BIRD -> checkIntValue = 3;
        }

        Assertions.assertThat(checkIntValue)
            .isEqualTo(1);
    }
}
