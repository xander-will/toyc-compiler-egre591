package symTable;

import compilers.Symbol;
import compilers.SymbolTable;
import symTable.Attributes;

import java.util.HashMap;
import java.util.Map;

public class TCsymTable {

    private HashMap<String, Attributes> st;

    public TCsymTable() {
        st = new HashMap<String, Attributes>();
    }

    public void add(String id, Attributes attr) {
        this.st.put(id, attr);
    }

    public Attributes get(String id) {
        if (this.st.containsKey(id))
            return this.st.get(id);
        else
            return null;
    }

    public boolean containsId(String id) {
        if (this.st.containsKey(id))
            return true;
        else
            return false;
    }

    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        String string = "";
        for (String s : this.st.keySet()) {
            string += "[" + s + "] : " + this.st.get(s).toString() + "\n";
        }

        return string;
    }

    // public class Symbol {
    // private String id;
    // private Attributes attributes;

    // public Symbol(String id, Attributes attributes) {
    // this.id = id;
    // this.attributes = attributes;
    // }
    // }
}
