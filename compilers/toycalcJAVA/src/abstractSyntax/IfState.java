package abstractSyntax;

import compilers.Token;

public class IfState implements Statement {

    Expression condition;
    Token jumplabel;

    public IfState(Expression e, Token t) { 
        condition = e;
        jumplabel = t;
    }

    public Expression getCondition() {
        return condition;
    }

    public Token getLabel() {
        return jumplabel;
    }

    public String toString() {
	return ("if("+condition.toString()+","+
                jumplabel.toString()+")");
    }

}
