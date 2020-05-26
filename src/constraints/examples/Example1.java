package constraints.examples;

import constraints.algorithm.AC4;
import constraints.algorithm.BackTrack;
import constraints.representation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Example1 {

    public static void main(String[] args) {
        Collection<Integer> domain = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6});
        Variable<Integer> A = Variable.of(domain);
        Variable<Integer> B = Variable.of(domain);
        Variable<Integer> C = Variable.of(domain);
        Variable<Integer> D = Variable.of(domain);
        Variable<Integer> E = Variable.of(domain);
        Variable<Integer> F = Variable.of(domain);

        ProblemBuilder pb = new ProblemBuilder()
                .add(BinaryConstraint.of(A, C, (a, c) -> a < c + 1))
                .add(BinaryConstraint.of(C, D, (c, d) -> c < d))
                .add(BinaryConstraint.of(B, E, (b, e) -> (b == 3 * e - 1)))
                .add(BinaryConstraint.of(D, F, (d, f) -> d < f))
                .add(BinaryConstraint.of(A, B, (a, b) -> a < b))
                .add(BinaryConstraint.of(F, A, (f, a) -> f == 2 * a));

        Problem p = pb.build();

        new AC4().run(p);

        if (!p.isInconsistent()) {
            List<Value> assignments = new BackTrack().run(p);
            if (assignments != null) {
                System.out.println(p.toString(Arrays.asList(A, B, C, D, E, F), assignments));
            }
        }
    }

}
