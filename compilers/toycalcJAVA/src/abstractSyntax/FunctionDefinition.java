package abstractSyntax;

import java.util.List;

import abstractSyntax.Definition;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;
import output.TCoutput;

public class FunctionDefinition extends Definition {

	Type ty;
	Identifier id;
	List<VariableDefinition> fd;
	Statement st;
	int var_num;

	public FunctionDefinition(Type ty, Identifier id, List<VariableDefinition> fd, Statement s, int var_num) {
		this.ty = ty;
		this.id = id;
		this.fd = fd;
		this.st = s;
		this.var_num = var_num;
	}

	public String getName() {
		return id.getName();
	}

	public String getType() {
		return ty.getType();
	}

	public int getNumVars() {
		return var_num;
	}

	public int getNumArgs() {
		return fd.size();
	}

	public String generateCode() {
		String s = "";

		for (VariableDefinition ve : fd)
			TCglobals.localsymtable.add(ve.getName(), ve.getType(), "variable");
		String body = st.generateCode();

		String name = id.getName();
		if (name.equals("main")) {
			s += TCglobals.codetemplate.main(body, var_num);
			if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
				TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);
			return s;
		}

		s += TCglobals.codetemplate.function(name, getNumArgs(), body, var_num);
		if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
			TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);
		return s;
	}

	public String toString() {
		String s = "funcDef(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "type = " + ty.toString();
		s += PrettyPrint.spaces() + "name = " + id.toString();
		if (!fd.isEmpty()) {
			s += PrettyPrint.spaces() + "args(\n";
			PrettyPrint.indent();
			for (VariableDefinition vd : fd)
				s += PrettyPrint.spaces() + vd.toString();
			PrettyPrint.outdent();
			s += PrettyPrint.spaces() + ")\n";
		}
		s += PrettyPrint.spaces() + "code = " + st.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

}
