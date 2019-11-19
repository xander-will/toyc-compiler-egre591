package abstractSyntax;

import abstractSyntax.Expression;

import globals.TCglobals;

public class Number implements Expression {

    private String number;

    public Number(String num) {
        this.number = num;
    }

    public String generateCode() {
        return TCglobals.codetemplate.number(this.number);
    }

    public String toString() {
        return "num(" + number + ")\n";
    }

}