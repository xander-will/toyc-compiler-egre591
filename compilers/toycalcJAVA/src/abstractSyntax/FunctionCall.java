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
		if (!TCglobals.symtable.containsID(id.getName()))
			TCoutput.reportSEMANTIC_ERROR("", id.getName() + " has not been declared");

		String args = "";
		for (Expression e : ap)
			args += e.generateCode();

		return TCglobals.codetemplate.call(id.getName(), args);
	}

}
