package abstractSyntax;

import compilers.LineOfCode;
import compilers.AbstractSyntax;
import java.util.List;
import java.util.Iterator;

public class Program implements AbstractSyntax {

    List<Statement> statementList;
    String name;

    public Program(String s, List<Statement> l) { 
        name = s;
        statementList = l;
    }


	public String getName() { return name; }
    public List<Statement> getStatementList() { return statementList; }

    public String toString() {
	if (statementList.isEmpty()) return "prog([])";
	String s = "prog(\n";
	PrettyPrint.indent();
        s += PrettyPrint.spaces() + name + ",\n";
        s += PrettyPrint.spaces() + "[\n";
	PrettyPrint.indent();
	Iterator itr = statementList.iterator();
	if (itr.hasNext())
	    s += PrettyPrint.spaces()+itr.next().toString();
	while (itr.hasNext())
	    s += ",\n"+PrettyPrint.spaces()+itr.next().toString();
	PrettyPrint.outdent();
	s += "\n"+PrettyPrint.spaces() + "]\n";
	PrettyPrint.outdent();
	s += PrettyPrint.spaces() + ")\n";
	return s;
    }

}
