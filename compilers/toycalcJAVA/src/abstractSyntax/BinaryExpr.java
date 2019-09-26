package abstractSyntax;

import compilers.Token;

public class BinaryExpr implements Expression {

    Expression operand1, operand2;
    Token operator;

    public BinaryExpr(Token oper, Expression op1, Expression op2) {
        operand1 = op1; operand2 = op2;
        operator = oper;
    }

    public Token getOperator() { return operator; }    
    public Expression getOperand1() { return operand1; }    
    public Expression getOperand2() { return operand2; }    

    public String toString() {
       return ("b_expr("+operator.toString()+","+
            operand1.toString()+","+operand2.toString()+")"); 
    }

}
