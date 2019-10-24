package abstractSyntax;

import java.util.List;

import abstractSyntax.Statement;
import abstractSyntax.PrettyPrint;

public class CompoundStatement implements Statement {

	private List<VariableDefinition> definitionList;
	private List<Statement> statementList;
	
	public CompoundStatement(List<VariableDefinition> dl, List<Statement> sl) {
		this.definitionList = dl;
		this.statementList = sl;
	}

	public String toString() {
		if (definitionList.isEmpty() && statementList.isEmpty())
			return "blockStatement()\n";

		String s = "blockStatement(\n";
		PrettyPrint.indent();
		for (VariableDefinition d : definitionList)
			s += PrettyPrint.spaces() + d.toString();
		for (Statement s : statementList)
			s += PrettyPrint.spaces() + s.toString();
		PrettyPrint.outdent();
		s += ")\n";

		return s;
	}

}
