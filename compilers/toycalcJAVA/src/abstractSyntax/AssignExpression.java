package abstractSyntax;

import output.TCoutput;

import abstractSyntax.Expression;
import abstractSyntax.Identifier;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;

public class AssignExpression implements Expression {

	private Operator operator;
	private Expression left;
	private Expression right;

	public AssignExpression(Operator op, Expression left, Expression right) {
		this.operator = op;
		this.left = left;
		this.right = right;
	}

	public String generateCode() {
		String rval = right.generateCode();
        String lval = null;
		if (left instanceof Identifier) {
			lval = ((Identifier) left).generateStore();
		} else {
			TCoutput.reportSEMANTIC_ERROR("",
					"Only variables may be assigned to, (" + left.toString() + "is an expression)");
		}
		return TCglobals.codetemplate.assignment(lval, rval);
	}

	public String toString() {
		String s = "assign(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "left = " + left.toString();
		s += PrettyPrint.spaces() + "op = " + operator.toString();
		s += PrettyPrint.spaces() + "right = " + right.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

}
