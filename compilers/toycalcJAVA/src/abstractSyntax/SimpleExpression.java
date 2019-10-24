package abstractSyntax;

import java.util.List;

import abstractSyntax.Statement;

public class SimpleExpression implements Expression {

	private List<Expression> termList;
	
	public SimpleExpression(List<Expression> termList) {
		this.termList = termList;
	}

}
