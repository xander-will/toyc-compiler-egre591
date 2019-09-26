package abstractSyntax;

import compilers.Symbol;

public class AssignState implements Statement {

    Symbol id;
    Expression expr;

    public AssignState(Symbol sym, Expression e) { 
        id = sym; expr = e; 
    }

    public Symbol getId() { return id; }
    public Expression getExpr() { return expr; }

    public String toString() {
	return ("assign("+id.getId()+","+expr.toString()+")");
    }

}
