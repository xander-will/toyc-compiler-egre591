package abstractSyntax;

public class WriteState implements Statement {

    Expression expr;

    public WriteState(Expression e) { expr = e; }

    public Expression getExpr() {
        return expr;
    }

    public String toString() {
	return ("write("+expr.toString()+")");
    }

}
