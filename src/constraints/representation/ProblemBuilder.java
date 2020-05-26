package constraints.representation;

import java.util.ArrayList;

public class ProblemBuilder {

    ArrayList<BinaryConstraint<?, ?>> constraints = new ArrayList<>();

    public ProblemBuilder add(BinaryConstraint<?, ?> constraint) {
        constraints.add(constraint);
        constraints.add(BinaryConstraint.reverseOf(constraint));
        return this;
    }

    public Problem build() {
        return new Problem(constraints);
    }

}
