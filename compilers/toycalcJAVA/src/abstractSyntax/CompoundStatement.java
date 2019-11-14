package abstractSyntax;

import java.util.List;

import abstractSyntax.Statement;
import abstractSyntax.PrettyPrint;
import globals.TCglobals;

public class CompoundStatement implements Statement {

	private List<VariableDefinition> definitionList;
	private List<Statement> statementList;

	public CompoundStatement(List<VariableDefinition> dl, List<Statement> sl) {
		this.definitionList = dl;
		this.statementList = sl;
	}

	public String generateCode() {
		for (VariableDefinition vd : definitionList) {
			TCglobals.localsymtable.add(vd.getName(), "variable");
		}
		String s = "";
		for (Statement st : statementList) {
			s += st.generateCode();
		}

		return s;
	}

	public String toString() {
		if (definitionList.isEmpty() && statementList.isEmpty())
			return "blockStatement()\n";

		String s = "blockStatement(\n";
		PrettyPrint.indent();
		for (VariableDefinition d : definitionList)
			s += PrettyPrint.spaces() + d.toString();
		for (Statement st : statementList)
			s += PrettyPrint.spaces() + st.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

}
