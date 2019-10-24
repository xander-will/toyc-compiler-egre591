package abstractSyntax;

import java.util.List;

import abstractSyntax.Expression;

public class FunctionCall implements Expression {

	private Identifier identifier;
	private List<Expression> ap;
	
	public FunctionCall(Identifier id, List<Expression> ap) {
		this.identifier = id;
		this.ap = ap;
	}

}
