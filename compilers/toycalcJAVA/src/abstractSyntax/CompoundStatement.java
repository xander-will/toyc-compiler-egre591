package abstractSyntax;

import java.util.List;
import java.util.ListIterator;

import abstractSyntax.Statement;
import abstractSyntax.PrettyPrint;
import abstractSyntax.ReturnStatement;
import abstractSyntax.IfStatement;
import abstractSyntax.WhileStatement;
import globals.TCglobals;
import output.TCoutput;

public class CompoundStatement implements Statement {

	private List<VariableDefinition> definitionList;
	private List<Statement> statementList;

	public CompoundStatement(List<VariableDefinition> dl, List<Statement> sl) {
		this.definitionList = dl;
		this.statementList = sl;
	}

	public boolean checkReturns() {
		Statement s;
		ListIterator<Statement> itr = statementList.listIterator(statementList.size());
		while (itr.hasPrevious()) {
			s = itr.previous();
			if (s instanceof ReturnStatement)
				return true;
			else if (s instanceof IfStatement) {
				if (((IfStatement)s).checkReturns())
					return true;
			}
			else if (s instanceof CompoundStatement) {
				if (((CompoundStatement)s).checkReturns())
					return true; 
			}
		}

		return false;
	}

	public String generateCode() {
		for (VariableDefinition vd : definitionList) {
			if (vd.getType().equals("char"))
				TCoutput.reportSEMANTIC_ERROR("", "Variable " + vd.getName() + " can only be type int, not char");
			else
				TCglobals.localsymtable.add(vd.getName(), vd.getType(), "variable");
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
