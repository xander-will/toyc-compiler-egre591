package abstractSyntax;

import java.util.List;

import abstractSyntax.Definition;
import abstractSyntax.PrettyPrint;

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

	public String toString() {
		String s = "funcDef(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "type = " + ty.toString();
		s += PrettyPrint.spaces() + "name = " + id.toString();
		if (!fd.isEmpty())
		{
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
