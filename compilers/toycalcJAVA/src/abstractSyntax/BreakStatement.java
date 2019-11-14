package abstractSyntax;

import abstractSyntax.Statement;

public class BreakStatement implements Statement {

	public String generateCode() {return"";}
	
	public String toString() {
		return "break()\n";
	}
}
