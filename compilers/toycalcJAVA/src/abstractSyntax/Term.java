package abstractSyntax;

import java.util.List;

import abstractSyntax.Expression;

public class Term implements Expression {

	private List<Expression> primList;
	
	public Term(List<Expression> primList) {
		this.primList = primList;
	}

}
