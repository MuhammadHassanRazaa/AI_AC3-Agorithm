package constraints.representation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Problem extends LinkedList<BinaryConstraint<?, ?>> {


    protected Problem(Collection<BinaryConstraint<?, ?>> constraints) {
        super(constraints);
    }

    public boolean isInconsistent() {
        for (BinaryConstraint constraint : this) {
            if (constraint.x.isEmpty())
                return true;
            if (constraint.y.isEmpty())
                return true;
        }
        return false;
    }

    public String toString(List<Variable<?>> variables, Collection<Value> assignments) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Variable<?> variable : variables) {
            sb.append(i++).append(".) ");
            for (Value v : variable.values()) {
                sb.append("\t").append(assignments.contains(v) ? "[" + variable.getWrapped(v) + "]" : " " + variable.getWrapped(v) + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
