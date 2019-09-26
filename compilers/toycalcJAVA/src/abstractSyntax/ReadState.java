package abstractSyntax;

import compilers.Symbol;

public class ReadState implements Statement {

    Symbol id;

    public ReadState(Symbol s) { id = s; }

    public Symbol getSymbol() {
        return id;
    }

    public String toString() {
	return ("read("+id.toString()+")");
    }

}
