package abstractSyntax;

public class Operator {

	private String operator;
	
    public Operator(String op) {
    	this.operator = op;
    }

    public String toString() {
        return "operator(" + operator + ")\n";
    }

}