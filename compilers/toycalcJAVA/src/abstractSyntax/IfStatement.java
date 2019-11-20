package abstractSyntax;

import abstractSyntax.Statement;
import globals.TCglobals;
import abstractSyntax.PrettyPrint;

public class IfStatement implements Statement {

	private Expression condition;
	private Statement ifs;
	private Statement els;

	public IfStatement(Expression condition, Statement ifs) {
		this.condition = condition;
		this.ifs = ifs;
		this.els = null;
	}

	public IfStatement(Expression condition, Statement ifs, Statement els) {
		this.condition = condition;
		this.ifs = ifs;
		this.els = els;
	}

	public String toString() {
		String s = "if(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "cond = " + condition.toString();
		s += PrettyPrint.spaces() + "then = " + ifs.toString();
		if (els != null)
			s += PrettyPrint.spaces() + "else = " + els.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

	public String generateCode() {
		String s = condition.generateCode();
		if (els != null) {
			s += "\tifeq ELSE_" + TCglobals.conditionCount + "\n";
			s += ifs.generateCode();
			s += "\tgoto " + "ENDIF_" + TCglobals.conditionCount + "\n";
			s += "ELSE_" + TCglobals.conditionCount + ":\n";
			s += els.generateCode();
			s += "ENDIF_" + TCglobals.conditionCount++ + ":\n";
		} else {
			s += "\tifeq ENDIF_" + TCglobals.conditionCount + "\n";
			s += ifs.generateCode();
			s += "ENDIF_" + TCglobals.conditionCount++ + ":\n";
		}
		return s;
	}
}
