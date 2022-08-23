package book.effectiveJava.item52;

import java.util.List;
import java.util.Set;

public class SetList {

    private final Set<Integer> set;

    private final List<Integer> list;

    public SetList(Set<Integer> set, List<Integer> list) {
        this.set = set;
        this.list = list;
    }

    public void addToSet(int numb) {
        set.add(numb);
    }

    public void addToList(int numb) {
        list.add(numb);
    }

    public void removeFromSet(int numb) {
        set.remove(numb);
    }

    public void removeFromList(int numb) {
        list.remove(numb);
    }

    public void removeFromListUsingInteger(Integer numb) {
        list.remove(numb);
    }

    public boolean isContainInSet(int numb) {
        return this.set.contains(numb);
    }

    public boolean isContainInList(int numb) {
        return this.list.contains(numb); // O(n)
    }
}