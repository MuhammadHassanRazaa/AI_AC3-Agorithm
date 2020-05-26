package constraints.algorithm;

import constraints.representation.BinaryConstraint;
import constraints.representation.Pair;
import constraints.representation.Value;
import constraints.representation.Variable;

import java.util.*;

public class AC4 {

    private static final Set<Value> EMPTY = new HashSet<>();

    public void run(Collection<BinaryConstraint<?, ?>> constraints) {

        Map<Value, Set<Value>> supports = new HashMap<>();
        Map<Pair<Value, Variable<?>>, Integer> counter = new HashMap<>();

        // init:
        LinkedList<Value> queue = new LinkedList<>();
        for (BinaryConstraint<?, ?> bc : constraints) {
            bc.supports(supports, counter);
        }

        counter.entrySet().stream()
                .filter(a -> a.getValue() == 0)
                .map(a -> a.getKey().left)
                .peek(Value::delete)
                .forEach(queue::add);

        while (!queue.isEmpty()) {
            Value m = queue.removeFirst();
            for (Value p : supports.getOrDefault(m, EMPTY)) {
                Pair ck = Pair.of(p, m.getVariable());
                int count = counter.compute(ck, (k, v) -> (v - 1));
                if (count == 0) {
                    if (p.delete()) {
                        queue.add(p);
                    }
                }
            }
        }
    }

}
