package constraints.representation;

public class Value {

    private Variable<?> variable;

    final int index;

    public Variable<?> getVariable() {
        return variable;
    }

    public boolean delete() {
        return variable.delete(this);
    }

    Value(Variable<?> variable, int index) {
        this.variable = variable;
        this.index = index;
    }


}
