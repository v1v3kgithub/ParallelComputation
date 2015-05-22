package computation;

/**
 * Created by vivek on 22/05/15.
 */
public class PairOfObjects<T> {
    private final T object1;
    private final T object2;

    public PairOfObjects(T object1, T object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public T getObject1() {
        return object1;
    }

    public T getObject2() {
        return object2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairOfObjects<?> that = (PairOfObjects<?>) o;

        if (!object1.equals(that.object1)) return false;
        return object2.equals(that.object2);

    }

    @Override
    public int hashCode() {
        int result = object1.hashCode();
        result = 31 * result + object2.hashCode();
        return result;
    }
}
