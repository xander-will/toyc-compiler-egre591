package abstractSyntax;

import globals.TCglobals;

public class Operator {

	private String operator;
	
    public Operator(String op) {
    	this.operator = op;
    }

    public String generateCode() {
        return TCglobals.codetemplate.operator(this.operator);
    }

    public String toString() {
        return "operator(" + operator + ")\n";
    }

}