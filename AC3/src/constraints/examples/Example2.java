package constraints.examples;

import constraints.algorithm.AC3;
import constraints.algorithm.BackTrack;
import constraints.representation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Example2 {

    public static void main(String[] args) {
        Collection<Integer> domain = Arrays.asList(new Integer[]{1, 2, 3, 4});
        List<Pair<Integer, Integer>> domainD = new ArrayList<>();

        for (int i : domain)
            for (int j : domain)
                domainD.add(Pair.of(i, j));

        Variable<Integer> A = Variable.of(domain);
        Variable<Integer> B = Variable.of(domain);
        Variable<Integer> C = Variable.of(domain);
        Variable<Pair<Integer, Integer>> D = Variable.of(domainD);


        ProblemBuilder pb = new ProblemBuilder()
                .add(BinaryConstraint.of(A, D, (a, d) -> a == d.left))
                .add(BinaryConstraint.of(B, D, (b, d) -> b == d.right))
                .add(BinaryConstraint.of(A, B, (a, b) -> a < b))
                .add(BinaryConstraint.of(B, C, (b, c) -> b < c))
                .add(BinaryConstraint.of(C, D, (c, d) -> c + d.left + d.right == 6));

        Problem p = pb.build();
        new AC3().run(p);

        if (!p.isInconsistent()) {
            List<Value> assignments = new BackTrack().run(p);
            if (assignments != null) {
                System.out.println(p.toString(Arrays.asList(A, B, C), assignments));
            }
        }

    }

}
