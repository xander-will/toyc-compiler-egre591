package abstractSyntax;

import java.util.List;

import output.TCoutput;

import abstractSyntax.Expression;
import abstractSyntax.Identifier;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;

public class SimpleExpression implements Expression {

	private List<Operator> op_list;
	private List<Expression> expr_list;

	public SimpleExpression(List<Operator> ol, List<Expression> el) {
		this.op_list = ol;
		this.expr_list = el;
	}

	public String generateCode() {
		// example explains why we do this a certain way
		// infix : 2 + 4 + 6 + 8
		// RPN : 2 4 + 6 + 8 +
		String s = expr_list.get(0).generateCode(); // grammar ensures there's
		s += expr_list.get(1).generateCode(); // two arguments for any s_expr
		s += op_list.get(0).generateCode();

		for (int i = 2; i < expr_list.size(); i++) {
			s += expr_list.get(i).generateCode();
			s += op_list.get(i - 1).generateCode();
		}

		return s;
	}

	public String toString() {
		String s = "expr(\n";
		PrettyPrint.indent();
		s += expr_list.get(0).generateCode();
		for (int i = 0; i < op_list.size(); i++) {
			s += op_list.get(i).generateCode();
			s += expr_list.get(i + 1).generateCode();
		}
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

}
