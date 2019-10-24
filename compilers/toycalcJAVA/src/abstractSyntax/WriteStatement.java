package abstractSyntax;

import java.util.List;

import abstractSyntax.Statement;
import abstractSyntax.PrettyPrint;

public class WriteStatement implements Statement {
	
	private List<Expression> ap;

	public WriteStatement(List<Expression> ap) {
		this.ap = ap;
	}

	public String toString() {
		String s = "write(\n";
		PrettyPrint.indent();
		for (Expression e : ap)
			s += PrettyPrint.spaces() + e.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

}
