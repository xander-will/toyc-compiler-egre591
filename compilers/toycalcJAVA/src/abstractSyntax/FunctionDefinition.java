package abstractSyntax;

import java.util.List;

import abstractSyntax.Definition;
import abstractSyntax.PrettyPrint;

public class FunctionDefinition extends Definition {

	Type ty;
	Identifier id;
	List<VariableDefinition> fd;
	Statement s;

	public FunctionDefinition(Type ty, Identifier id, List<VariableDefinition> fd, Statement s) {
		this.ty = ty;
		this.id = id;
		this.fd = fd;
		this.s = s;
	}

	public String toString() {
		String s = "funcDef(" + ty.toString() + id.toString() + ") (\n";
		PrettyPrint.indent();
		if (!fd.isEmpty())
		{
			s += PrettyPrint.spaces() + "args(\n";
			PrettyPrint.indent();
			for (VariableDefinition vd : fd)
				s += PrettyPrint.spaces() + vd.toString();
			PrettyPrint.outdent();
			s += PrettyPrint.spaces() + ")\n";
		}
		s += PrettyPrint.spaces() + s.toString();
		PrettyPrint.outdent();

		return s;
	}

}
