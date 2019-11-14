package abstractSyntax;

import java.util.List;

import abstractSyntax.Definition;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;

public class FunctionDefinition extends Definition {

	Type ty;
	Identifier id;
	List<VariableDefinition> fd;
	Statement st;

	public FunctionDefinition(Type ty, Identifier id, List<VariableDefinition> fd, Statement s) {
		this.ty = ty;
		this.id = id;
		this.fd = fd;
		this.st = s;
	}

	public String getName() {
		return id.getName();
	}

	public String getType() {
		return ty.getType();
	}

	public String generateCode() {
		String name = id.getName();
		String args = "[Ljava/lang/String;";
		String body = st.generateCode();

		return TCglobals.codetemplate.function(name, args, body);
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
