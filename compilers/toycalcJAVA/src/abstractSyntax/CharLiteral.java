package abstractSyntax;

import abstractSyntax.Expression;

public class CharLiteral implements Expression {

    private String charLit;

    public CharLiteral(String c) {
        this.charLit = c;
    }

    public String toString() {
        return "charLiteral(" + charLit + ")\n";
    }

    // Code generation not needed
    public String generateCode() {
        return "";
    }
}