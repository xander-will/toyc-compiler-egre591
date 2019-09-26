package abstractSyntax;

import compilers.Token;

public class UnaryExpr implements Expression {

    Expression operand1;
    Token operator;

    public UnaryExpr(Token oper, Expression op) {
        operand1 = op; operator = oper;
    }
    
    public Token getOperator() { return operator; }    
    public Expression getOperand1() { return operand1; }    

    public String toString() {
        return ("u_expr("+operator.toString()+","+operand1.toString());
    }

}
