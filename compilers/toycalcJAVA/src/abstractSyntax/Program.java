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

	public String generateCode() {
		CodeTemplate ct = TCglobals.codetemplate;
		String s = ct.init();

		for (Definition def : dl) {
			if (def instanceof FunctionDefinition) {
				TCglobals.symtable.add(def.getName(), "function");
				TCglobals.localsymtable = TCglobals.symtable.get(def.getName()).getSymtable();
				s += def.generateCode() + "\n";
			}
		}

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
