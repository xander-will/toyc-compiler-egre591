package abstractSyntax;

import abstractSyntax.Expression;

public class StringLiteral implements Expression {

	private String stringBody;
	
    public StringLiteral(String s) {
    	this.stringBody = s;
    }

    public String toString() {
        return "stringLit(" + stringBody + ")\n";
    }
    
}