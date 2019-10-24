package abstractSyntax;

import abstractSyntax.Expression;
import abstractSyntax.PrettyPrint;

public class Expr implements Expression {

	private Operator operator;
	private Expression left;
	private Expression right;
	
	public Expr(Operator op, Expression left, Expression right) {
		this.operator = op;
		this.left = left;
		this.right = right;
	}

	public String toString() {
		String s = "expr(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + left.toString();
		s += PrettyPrint.spaces() + operator.toString();
		s += PrettyPrint.spaces() + right.toString();
		PrettyPrint.outdent();
		s += ")\n";

		return s;
	}

}
