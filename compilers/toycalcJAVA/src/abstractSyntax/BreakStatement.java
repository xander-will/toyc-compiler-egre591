package abstractSyntax;

import abstractSyntax.Statement;

public class BreakStatement implements Statement {

	// Code not needed for break
	public String generateCode() {
		return "";
	}

	public String toString() {
		return "break()\n";
	}
}
