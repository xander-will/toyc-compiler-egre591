package abstractSyntax;

import java.util.List;

import abstractSyntax.Expression;
import abstractSyntax.PrettyPrint;

public class FunctionCall implements Expression {

	private Identifier id;
	private List<Expression> ap;
	
	public FunctionCall(Identifier id, List<Expression> ap) {
		this.id = id;
		this.ap = ap;
	}

	public String toString() {
		if (ap.isEmpty())
			return "funcCall(" + id.toString() + ")\n";
			
		String s = "funcCall(" + id.toString() + ") (\n";
		PrettyPrint.indent();
		for (Expression e : ap)
			s += PrettyPrint.spaces() + e.toString();
		PrettyPrint.outdent();
		s += ")\n";

		return s;
	}

}
