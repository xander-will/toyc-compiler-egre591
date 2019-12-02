package abstractSyntax;

import compilers.AbstractSyntax;
import compilers.CodeTemplate;

import java.util.List;

import abstractSyntax.Definition;
import abstractSyntax.FunctionDefinition;
import abstractSyntax.VariableDefinition;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;
import output.TCoutput;

public class Program implements AbstractSyntax {

	List<Definition> dl;

	public Program(List<Definition> l) {
		dl = l;
	}

	public void checkReturns() {
		System.err.println("program");
		for (Definition def : dl)
			if (def instanceof FunctionDefinition)
				if (!((FunctionDefinition)def).checkReturns())
					TCoutput.reportSEMANTIC_ERROR("", "Function " + ((FunctionDefinition)def).getName() + " does not ensure a return on all possible code paths.");
			
	}

	public String generateCode() {
		CodeTemplate ct = TCglobals.codetemplate;
		String init, t, s;
		init = t = s = "";

		for (Definition def : dl) {
			if (def instanceof FunctionDefinition) {
				TCglobals.symtable.add(def.getName(), def.getType(), "function",
						((FunctionDefinition) def).getNumArgs());
				TCglobals.localsymtable = TCglobals.symtable.get(def.getName()).getSymtable();
				t += def.generateCode() + "\n";
			} else {
				TCglobals.symtable.add(def.getName(), def.getType(), "variable");
			}
		}

		init = ct.init() + "\n";
		
		s += init + t + TCglobals.codetemplate.runtime();

		if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
			TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

		return s;
	}

	public String toString() {
		if (dl.isEmpty())
			return "prog()";
		String s = "prog(\n";
		PrettyPrint.indent();
		for (Definition d : dl)
			s += PrettyPrint.spaces() + d.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

}
