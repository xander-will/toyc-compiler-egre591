package abstractSyntax;

import compilers.Token;

public class SimpleExpr implements Expression {

    Token expr;

    public SimpleExpr(Token t) { expr = t; }

    public Token getExpr() { return expr; }    

    public String toString() {
        return ("s_expr("+expr.getLexeme()+")");
    }

}
