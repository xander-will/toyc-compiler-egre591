package abstractSyntax;

import compilers.AbstractSyntax;
import abstractSyntax.Definition;
import abstractSyntax.PrettyPrint;

public class Program implements AbstractSyntax {

    List<Definition> dl;

    public Program(List<Definition> l) {
        dl = l;
    }

    public String toString() {
		if (dl.isEmpty())
			return "prog()";
		String s = "prog(\n";
		PrettyPrint.indent();
		for (Definition d : dl)
			s += PrettyPrint.spaces() + d.toString();
		PrettyPrint.outdent();
		s += ")\n";

		return s;
    }

}
