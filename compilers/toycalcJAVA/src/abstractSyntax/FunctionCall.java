package abstractSyntax;

import java.util.List;

import abstractSyntax.Expression;
import abstractSyntax.PrettyPrint;

import output.TCoutput;
import globals.TCglobals;

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

	public String generateCode() {
		String s = "";
		if (!TCglobals.symtable.containsID(id.getName()))
			TCoutput.reportSEMANTIC_ERROR("", id.getName() + " has not been declared");
		if (TCglobals.symtable.get(id.getName()).getArgNum() != ap.size())
			TCoutput.reportSEMANTIC_ERROR("", id.getName() + " called with the wrong number of args");

		String args = "";
		for (Expression e : ap)
			args += e.generateCode();

		s = TCglobals.codetemplate.call(id.getName(), args);

		if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
			TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

		return s;
	}

}
