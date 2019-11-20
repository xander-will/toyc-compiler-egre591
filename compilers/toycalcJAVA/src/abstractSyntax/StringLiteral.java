package abstractSyntax;

import abstractSyntax.Expression;

import globals.TCglobals;


public class StringLiteral implements Expression {

    private String stringBody;

    public StringLiteral(String s) {
        this.stringBody = s;
    }

    public String toString() {
        return "stringLit(" + stringBody + ")\n";
    }

    public String generateCode() {
        return TCglobals.codetemplate.stringLit(stringBody);
    }
}