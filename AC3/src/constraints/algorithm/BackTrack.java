package constraints.algorithm;

import constraints.representation.BinaryConstraint;
import constraints.representation.Value;
import constraints.representation.Variable;

import java.util.*;

public class BackTrack {

    public List<Value> run(Collection<BinaryConstraint<?, ?>> constraints) {
        HashSet<Variable<?>> unassigned = new HashSet<>();
        constraints.stream().map(c -> c.x).forEach(unassigned::add);
        constraints.stream().map(c -> c.y).forEach(unassigned::add);
        LinkedList<Value> assignments = new LinkedList<>();
        return run(constraints, assignments, unassigned);
    }

    public List<Value> run(Collection<BinaryConstraint<?, ?>> constraints, LinkedList<Value> assignments, HashSet<Variable<?>> unassigned) {
        if (unassigned.isEmpty()) {
            return assignments;
        }

        Variable<?> next = select(unassigned);
        for (Value value : next.values()) {
            if (consistent(value, assignments, constraints)) {
                assignments.addLast(value);
                List<Value> solution = run(constraints, assignments, unassigned);
                if (solution != null)
                    return solution;
                assignments.removeLast();
            }
        }
        unassigned.add(next);
        return null;
    }

    public boolean consistent(Value x, List<Value> assignments, Collection<BinaryConstraint<?, ?>> constraints) {
        List<Value> inferences = new ArrayList<>();
        for (BinaryConstraint<?, ?> constraint : constraints) {
            if (constraint.x.equals(x.getVariable())) {
                for (Value y : assignments) {
                    if (constraint.y.equals(y.getVariable())) {
                        if (!constraint.test(x, y)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private Variable<?> select(Set<Variable<?>> variables) {
        Variable<?> next = variables.iterator().next();
        variables.remove(next);
        return next;
    }

}
