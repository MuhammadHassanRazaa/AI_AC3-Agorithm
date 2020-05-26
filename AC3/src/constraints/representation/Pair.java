package constraints.representation;

public class Pair<T, U> {

    public final T left;
    public final U right;

    private Pair(T t, U u) {
        this.left = t;
        this.right = u;
    }

    @Override
    public int hashCode() {
        return left.hashCode() * 13 + right.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Pair<?, ?> ? equals((Pair<?, ?>) obj) : false;
    }

    public boolean equals(Pair<?, ?> pair) {
        return left.equals(pair.left) && right.equals(pair.right);
    }

    public static <T, U> Pair<T, U> of(T t, U u) {
        return new Pair(t, u);
    }


}
