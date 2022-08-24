package book.effectiveJava.item53;

public class Varargs {

    static int sum(int... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }

    static int min(int... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Too few arguments");
        }
        int min = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i] < min) {
                min = args[i];
            }
        }
        return min;
    }

    // 반드시 필요한 최소 인자를 추가 매개변수로 정의하여 코드를 좀 더 깔끔하게 할 수 있다
//    static int min(int firstArg, int... remainingArgs) {
//        int min = firstArg;
//        for (int arg : remainingArgs) {
//            if (arg < min) {
//                min = arg;
//            }
//        }
//        return min;
//    }
}
