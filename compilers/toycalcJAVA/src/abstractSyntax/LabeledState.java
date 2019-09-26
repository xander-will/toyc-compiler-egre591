package abstractSyntax;

import compilers.Symbol;
import symTable.TCsymbol;

public class LabeledState implements Statement {

    Symbol sym;
    Statement state;

    public LabeledState(Symbol symbol, Statement s) { 
        sym = symbol; state = s; 
    }

    public Symbol getSym() { return sym; }
    public Statement getState() { return state; }

    public String toString() {
	return ("labeled("+((TCsymbol)sym).getId()+","+state.toString()+")");
    }

}
