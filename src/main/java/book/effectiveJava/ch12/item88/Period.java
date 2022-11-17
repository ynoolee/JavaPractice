package book.effectiveJava.ch12.item88;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

// Broken "immutable" time period class (Pages 231-3)
public final class Period implements Serializable {

    private final Date start;

    private final Date end;

//    /**
//     * @param  start the beginning of the period
//     * @param  end the end of the period; must not precede start
//     * @throws IllegalArgumentException if start is after end
//     * @throws NullPointerException if start or end is null
//     */
//    public Period(Date start, Date end) {
//        if (start.compareTo(end) > 0)
//            throw new IllegalArgumentException(
//                start + " after " + end);
//        this.start = start;
//        this.end   = end;
//    }
//
//    public Date start() {
//        return start;
//    }
//    public Date end() {
//        return end;
//    }

    public String toString() {
        return start + " - " + end;
    }

    // Repaired constructor - makes defensive copies of parameters (Page 232)
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(
                this.start + " after " + this.end);
        }
    }

    // 참조 값 만을 확인하기 위해 아래 코드를 추가
    public int startCode() {
        return this.start.hashCode();
    }

    public int endCode() {
        return this.end.hashCode();
    }

    // Repaired accessors - make defensive copies of internal fields (Page 233)
    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    // Remainder omitted

    @Serial
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(
                this.start + " after " + this.end);
        }
    }
}