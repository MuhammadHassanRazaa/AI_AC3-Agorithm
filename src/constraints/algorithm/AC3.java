package constraints.algorithm;

import constraints.representation.BinaryConstraint;

import java.util.ArrayList;
import java.util.Collection;

public class AC3 {

    public void run(Collection<BinaryConstraint<?, ?>> constraints) {

        ArrayList<BinaryConstraint<?, ?>> workingSet = new ArrayList<>(constraints);

        while (!workingSet.isEmpty()) {

            BinaryConstraint<?, ?> constraint = workingSet.remove(0);

            if (constraint.revise()) {
                if (constraint.x.isEmpty())
                    return;
                for (BinaryConstraint<?, ?> c : constraints) {
                    if (c.y.equals(constraint.x)) {
                        workingSet.add(c);
                    }
                }
            }

        }
    }
}
