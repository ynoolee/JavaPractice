package book.effectiveJava.ch05.item26;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GenericsTest {

    static class UserType<T> {

    }

    @Test
    void instanceof_사용시_타입소거_되기_때문에_raw_type_사용권장() {
        UserType<String> stringUserType = new UserType<>();

        Assertions.assertThat(stringUserType instanceof UserType).isTrue();
    }

    @Test
    void raw_type_List_참조변수에_Integer_타입_List_객체를_assign_한_후_String_타입_List_참조변수에_할당_시도시_throw_ClassCastException() {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List rawInts = ints;
        List<String> strs = rawInts;

        Assertions.assertThatExceptionOfType(ClassCastException.class)
            .isThrownBy(() -> System.out.println(strs.get(0)));
    }

    /**
     * // 호환성 때문에 이러한 것들을 허용해 놓았으니 의심가는 코드들에 컴파일러가 warning 을 뜨게 하고 있다 -> 개발자가 확신을 갖고 Warning 을
     * Suppress 하는 것 필요함.
     */
    @Test
    void raw_type_List_참조변수에_Integer_타입_List_객체를_assign_한_후_동일한_타입_List_참조변수에_할당_시도시_성공하나_컴파일경고가_뜨기_때문에_개발자는_확신을가진경우_SuppressedWarnings_을_가장작은단위에_어노테이트한다() {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List rawInts = ints;

        @SuppressWarnings("unchecked")
        List<Integer> strs = rawInts;
    }


    @Disabled
    @Test
    void 같은_제네릭타입_에_대한_서로다른_매개변수화_타입_은_호환되지_않는다() {
        List<String> strList = new ArrayList<>();

//        addToParameterizedTypeList(strList); // 컴파일 타임 에러
    }

    private void addToParameterizedTypeList(List<Object> objList) {

    }

    @Test
    void addToRawTypeList_에_파라마터타입_리스트를_전달해_Object_타입_원소를추가_하고_파라미터_타입_리스트에서_원소를_가져와_사용할_경우_예외가_발생할수있다() {
        List<String> strList = new ArrayList<>();

        addToRawTypeList(strList, Integer.valueOf(11));

        Assertions.assertThatExceptionOfType(ClassCastException.class)
            .isThrownBy(() -> System.out.println(strList.get(0)));
    }

    private void addToRawTypeList(List strList, Object elementToBeAdded) {
        strList.add(elementToBeAdded);
    }

    private void addToUnboundedWildCardType(List<? extends Object> list, Object elementToBeAdded) {
//        list.add(elementToBeAdded); // 컴파일 에러 발생
    }

    @Test
    void 제네릭_타입_서브타이핑() {
        List<String> strList = new ArrayList<>();
        List<Object> objList = new ArrayList<>();

        addToGenericCollection(strList, "a");
        addToGenericCollection(objList, "a");
    }

    private <T> void addToGenericCollection(Collection<T> collection, T elementToBeAdded) {
        collection.add(elementToBeAdded);
    }

    @Test
    void 제네릭_타입_서브타이핑_type_argument_가_명시된경우() {
        List<String> strList = new ArrayList<>();

        addToCollectionWithTypeArgumentGiven(strList);
    }

    private void addToCollectionWithTypeArgumentGiven(Collection<String> collection) {
    }

    @Test
    void 제네릭_상속관계는_타입_파라미터와_상관없이_extends_implements_로_맺어진_상속관계이면_생성되어_다음과_같은_다형성_사용이_가능() {
        List<Integer> s0 = new MyList<Integer, Integer>();
        List<String> s1 = new MyList<String, Integer>();
        List<String> s2 = new MyList<String, String>();
    }

    static class MyList<E, P> implements List<E> {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(E e) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public E get(int index) {
            return null;
        }

        @Override
        public E set(int index, E element) {
            return null;
        }

        @Override
        public void add(int index, E element) {

        }

        @Override
        public E remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<E> listIterator() {
            return null;
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return null;
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return null;
        }
    }

    static <T> void method(T t, List<T> list) {
    }

    @Test
    void 컴파일러가_타입추론을_못하는_경우_직접_명시_가능하다() {
        List<String> li = Collections.<String>emptyList();
    }

    @Nested
    class WildCardTest {

        static void printList(List<Object> list) {
            list.forEach(s -> System.out.println(s));
        }

        static void printWildCardList(List<?> list) {
            list.forEach(s -> System.out.println(s));
        }

        static void printBoundedWildCardList(List<? extends Comparable> list) {
            list.forEach(s -> s.compareTo(1));
        }

        @Test
        void wildcard_type_을_사용할경우_어떤_타입_파라미터를가진_List_타입이던_전달_가능하다() {
            List<String> strList = new ArrayList<>();

//            printList(strList); // 컴파일 타임 에러
            printWildCardList(strList);
        }

        @Test
        void bounded_wildcard_type_을_사용할경우_Upper_type_하위의_어떤_타입_파라미터를가진_List_타입이던_전달_가능하다() {
            List<String> strList = new ArrayList<>();

            printWildCardList(strList);
        }

        static class A {

        }

        static class B extends A {

        }

        @Test
        void bounded_wildcard_type_을_사용할경우_Upper_type_하위의_어떤_타입_파라미터를가진_List_타입이던_할당가능하다() {
            List<B> bList = new ArrayList<>();

            List<? extends A> aList = bList;
        }
    }


}
