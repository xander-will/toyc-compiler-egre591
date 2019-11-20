package abstractSyntax;

import java.util.ArrayList;
import java.util.List;

import abstractSyntax.Statement;
import globals.TCglobals;
import abstractSyntax.Identifier;
import abstractSyntax.PrettyPrint;

public class ReadStatement implements Statement {

	private List<Identifier> ids;

	public ReadStatement(ArrayList<Identifier> ids) {
		this.ids = ids;
	}

	public String toString() {
		String s = "read(\n";
		PrettyPrint.indent();
		for (Identifier id : ids)
			s += PrettyPrint.spaces() + id.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

	public String generateCode() {
		String s = "";
		for (Identifier i : ids) {
			s += TCglobals.codetemplate.read();
			s += i.generateStore();
		}
		return s;
	}
}
