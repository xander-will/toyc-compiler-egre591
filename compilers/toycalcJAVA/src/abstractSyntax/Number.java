package abstractSyntax;

import abstractSyntax.Expression;

public class Number implements Expression {

	private String number;
	
    public Number(String num) {
    	this.number = num;
    }

    public String toString() {
        return "num(" + number + ")\n";
    }
    
}