package abstractSyntax;

import output.TCoutput;

import abstractSyntax.Expression;
import abstractSyntax.Identifier;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;

public class Expr implements Expression {

	private Operator operator;
	private Expression left;
	private Expression right;

	public Expr(Operator op, Expression left, Expression right) {
		this.operator = op;
		this.left = left;
		this.right = right;
	}

	public String generateAssign() {
		String s = right.generateCode();
		if (left instanceof Identifier) {
			s += ((Identifier) left).generateStore();
		} else {
			TCoutput.reportSEMANTIC_ERROR("",
					"Only variables may be assigned to, (" + left.toString() + "is an expression)");
		}
		return s;
	}

	public String generateCode() {
		if (operator.toString().equals("operator(=)\n")) {
			return generateAssign();
		}
		String s = left.generateCode();
		s += right.generateCode();
		s += operator.generateCode();
		return s;
	}

	public String toString() {
		String s = "expr(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "left = " + left.toString();
		s += PrettyPrint.spaces() + "op = " + operator.toString();
		s += PrettyPrint.spaces() + "right = " + right.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

}
