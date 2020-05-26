package constraints.representation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

public class BinaryConstraint<T1, T2> {

    public final Variable<T1> x;
    public final Variable<T2> y;

    private final BiPredicate<T1, T2> condition;

    public BinaryConstraint(Variable<T1> x, Variable<T2> y, BiPredicate<T1, T2> condition) {
        this.x = x;
        this.y = y;
        this.condition = condition;
    }

    public static <T1, T2> BinaryConstraint<T1, T2> of(Variable<T1> x, Variable<T2> y, BiPredicate<T1, T2> condition) {
        return new BinaryConstraint(x, y, condition);
    }

    public static <T2, T1> BinaryConstraint<T2, T1> reverseOf(BinaryConstraint<T1, T2> constraint) {
        return BinaryConstraint.of(constraint.y, constraint.x, (a, b) -> constraint.condition.test(b, a));
    }

    public boolean test(Value a, Value b) {
        if (a.getVariable().equals(x) && b.getVariable().equals(y)) {
            return condition.test(x.getWrapped(a), y.getWrapped(b));
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean revise() {
        boolean modified = false;
        for (Value vx : x.values()) {
            boolean match = false;
            for (Value vy : y.values()) {
                if (condition.test(x.getWrapped(vx), y.getWrapped(vy))) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                vx.delete();
                modified = true;
            }
        }
        return modified;
    }

    public void supports(Map<Value, Set<Value>> support, Map<Pair<Value, Variable<?>>, Integer> count) {
        for (Value a : x.values()) {
            int total = 0;
            for (Value b : y.values()) {
                if (condition.test(x.getWrapped(a), y.getWrapped(b))) {
                    support.computeIfAbsent(b, (k) -> new HashSet<>()).add(a);
                    total++;
                }
            }
            count.put(Pair.of(a, y), total);
        }
    }

}
