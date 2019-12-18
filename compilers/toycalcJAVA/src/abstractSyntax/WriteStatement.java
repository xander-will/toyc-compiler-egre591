package abstractSyntax;

import java.util.List;

import abstractSyntax.Statement;
import compilers.CodeTemplate;
import globals.TCglobals;
import output.TCoutput;
import abstractSyntax.PrettyPrint;

import output.TCoutput;

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

	public String generateCode() {
		String s = "";
		for (Expression e : ap) {
			s += e.generateCode();
			if (e instanceof Number || e instanceof Identifier || e instanceof FunctionCall || e instanceof Expression)
				s += TCglobals.codetemplate.write("int");
			else
				s += TCglobals.codetemplate.write("string");
		}

		if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
			TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

		return s;
	}

}
