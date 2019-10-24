package abstractSyntax;

import java.util.List;

import abstractSyntax.Statement;

public class WriteStatement implements Statement {
	
	private List<Expression> ap;

	public WriteStatement(List<Expression> ap) {
		this.ap = ap;
	}

}
