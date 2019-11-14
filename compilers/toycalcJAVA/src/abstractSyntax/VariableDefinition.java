package abstractSyntax;

import abstractSyntax.Definition;
import abstractSyntax.PrettyPrint;

public class VariableDefinition extends Definition {

	private Type type;
	private Identifier id;
	
	public VariableDefinition(Type ty, Identifier id) {
		this.type = ty;
		this.id = id;
	}

	public String getName() {
		return id.getName();
	}

	public String toString() {
		String s = "varDef(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "type = " + type.toString();
		s += PrettyPrint.spaces() + "id = " + id.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";
		return s;
	}
}
