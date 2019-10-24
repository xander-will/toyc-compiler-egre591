package abstractSyntax;

import abstractSyntax.Expression;

public class Identifier implements Expression {

	private String name;
	
    public Identifier(String name) {
    	this.name = name;
    }

}