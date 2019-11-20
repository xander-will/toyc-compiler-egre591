package abstractSyntax;

import compilers.AbstractSyntax;
import compilers.CodeTemplate;

import java.util.List;

import abstractSyntax.Definition;
import abstractSyntax.FunctionDefinition;
import abstractSyntax.VariableDefinition;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;

public class Program implements AbstractSyntax {

	List<Definition> dl;

	public Program(List<Definition> l) {
		dl = l;
	}

	/*
	 * Remember to add instanceof check for vardef and add generate code for vardef
	 * Add args_len to the attribute class and set it up here
	 * ((FunctionDefinitiondef).arglen())
	 */

	public String generateCode() {
		CodeTemplate ct = TCglobals.codetemplate;
		String s = ct.init() + "\n";

		for (Definition def : dl) {
			if (def instanceof FunctionDefinition) {
				TCglobals.symtable.add(def.getName(), def.getType(), "function");
				TCglobals.localsymtable = TCglobals.symtable.get(def.getName()).getSymtable();
				s += def.generateCode() + "\n";
			}
		}

		s += "; ==============================================\n" +
			 "; =========== Begin Runtime Functions ==========\n" +
			 "; ==============================================\n\n";

		s += TCglobals.codetemplate.getRuntimeFunctions();

		s += "; ==============================================\n" +
			 "; ============ End Runtime Functions ===========\n" +
			 "; ==============================================\n\n";

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
