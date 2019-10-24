package abstractSyntax;

import java.util.List;

import abstractSyntax.Program;

public class ToyCProgram extends Program {

	private List<Definition> definitionList;
	
	public ToyCProgram(String s, List<Statement> l) {
		super(s, l);
	}
}
