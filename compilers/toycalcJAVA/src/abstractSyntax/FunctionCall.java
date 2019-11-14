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
		String s = "funcCall(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "name = " + id.toString();
		if (!ap.isEmpty()) {
			s += PrettyPrint.spaces() + "args(\n";
			PrettyPrint.indent();
			for (Expression e : ap)
				s += PrettyPrint.spaces() + e.toString();
			PrettyPrint.outdent();
			s += PrettyPrint.spaces() + ")\n";
		}
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

	@Override
	public String generateCode() {
		return "";
	}

}
