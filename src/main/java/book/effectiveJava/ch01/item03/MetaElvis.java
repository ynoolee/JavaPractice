package book.effectiveJava.ch01.item03;

public class MetaElvis<T> {
    private static final MetaElvis<Object> INSTANCE = new MetaElvis<>();

    private MetaElvis() {}

    @SuppressWarnings("unchecked")
    public static <T> MetaElvis<T> getInstance() { return (MetaElvis<T>) INSTANCE;}
}
