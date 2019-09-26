package abstractSyntax;

import compilers.Token;

public class GotoState implements Statement {

    Token label;

    public GotoState(Token t) { label = t; }

    public Token getLabel() {
        return label;
    }

    public String toString() {
	return ("goto("+label.toString()+")");
    }

}
