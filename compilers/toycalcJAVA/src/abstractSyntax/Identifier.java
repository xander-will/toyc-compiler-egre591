package abstractSyntax;

import abstractSyntax.Expression;

public class Identifier implements Expression {

	private String name;
	
    public Identifier(String name) {
    	this.name = name;
    }

    public String toString() {
        return "id(" + name + ")\n";
    }
}