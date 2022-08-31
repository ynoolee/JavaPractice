package book.effectiveJava.item58;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class AdvancedFor {

    public void iterate(List<?> list) {
        for(Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
            Object next = iterator.next();
            System.out.println(next);
        }
    }

    public void iterateByFor(List<?> list) {
        for (int i = 0 ; i < list.size(); i++) {
            System.out.println(list.get(i++)); // 실수로 i++ 를 해버리면 원하던 출력(실제 개수만큼 출력하기) 을 할 수 없게 됨

        }
    }
    public List<Pair> exceedSize() {
        List<Pair> pairs = new ArrayList<>();
        for(Iterator<OUTER> outer = outers.iterator(); outer.hasNext();) {
            for(Iterator<INNER> inner = inners.iterator(); inner.hasNext();) {
                pairs.add(new Pair(outer.next(), inner.next()));
            }
        }

        return pairs;
    }

    public List<Pair> exceedSizeV2() {
        List<Pair> pairs = new ArrayList<>();
        for(Iterator<OUTER> outer = outers.iterator(); outer.hasNext();) {
            OUTER curOuter = outer.next();
            for(Iterator<INNER> inner = inners.iterator(); inner.hasNext();) {
                pairs.add(new Pair(curOuter, inner.next()));
            }
        }

        return pairs;
    }

    public List<Pair> exceedSizeV3() {
        List<Pair> pairs = new ArrayList<>();
        for (OUTER outer : outers) {
            for (INNER inner : inners) {
                pairs.add(new Pair(outer, inner));
            }
        }

        return pairs;
    }

    public void removeBy(List<Pair> pairs, Predicate<Pair> predicate) {
        pairs.removeIf(predicate);
    }

    enum OUTER {A,B}
    enum INNER {ONE, TWO, THREE }

    static List<OUTER> outers = Arrays.asList(OUTER.values());
    static List<INNER> inners = Arrays.asList(INNER.values());

    public static class Pair {
        private final OUTER outer;
        private final INNER inner;

        public Pair(OUTER outer, INNER inner) {
            this.outer = outer;
            this.inner = inner;
        }

        public OUTER getOuter() {
            return outer;
        }

        public INNER getInner() {
            return inner;
        }
    }
    public static void main(String[] args) {
        AdvancedFor main = new AdvancedFor();
        main.iterate(List.of(1,2,3));
        main.iterateByFor(List.of(1,2,3)); // 1 3
//        main.exceedSize(); //  NoSuchElementException
        System.out.println(main.exceedSizeV3().size());

    }


}
