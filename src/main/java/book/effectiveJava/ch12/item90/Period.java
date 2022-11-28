package book.effectiveJava.ch12.item90;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public final class Period implements Serializable {

    private final Date start;

    private final Date end;

    public Period(Date start, Date end) {
        // 방어적 복사
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        // 검증 코드
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(
                this.start + " after " + this.end);
        }
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    @Serial
    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    private static class SerializationProxy implements Serializable {

        private final Date start;

        private final Date end;

        @Serial
        private static final long serialVersionUID = 11111111L;

        public SerializationProxy(Period p) {
            this.start = p.start;
            this.end = p.end;
        }

        @Serial
        private Object readResolve() {
            return new Period(this.start, this.end);
        }
    }
}
