package abstractSyntax;

import java.util.List;

import abstractSyntax.Expression;
import abstractSyntax.SimpleExpression;

public class RelopExpression implements Expression {

	private List<Expression> seList;
	
	public RelopExpression(List<Expression> seList) {
		this.seList = seList;
	}

}
