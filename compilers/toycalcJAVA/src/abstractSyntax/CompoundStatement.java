package abstractSyntax;

import java.util.List;

import abstractSyntax.Statement;

public class CompoundStatement implements Statement {

	private List<VariableDefinition> definitionList;
	private List<Statement> statementList;
	
	public CompoundStatement(List<VariableDefinition> dl, List<Statement> sl) {
		this.definitionList = dl;
		this.statementList = sl;
	}

}
