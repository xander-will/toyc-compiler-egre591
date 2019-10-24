package abstractSyntax;

import abstractSyntax.Definition;

public class VariableDefinition extends Definition {

	private Type type;
	private Identifier id;
	
	public VariableDefinition(Type ty, Identifier id) {
		this.type = ty;
		this.id = id;
	}

	public String toString() {
		return "varDef(" + type.toString() + id.toString() + ")\n";
	}
}
