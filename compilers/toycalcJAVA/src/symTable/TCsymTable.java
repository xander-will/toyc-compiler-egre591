package symTable;

import compilers.SymbolTable;

import symTable.Attributes;

import java.util.HashMap;

public class TCsymTable implements SymbolTable {

    private HashMap<String, Attributes> st;
    private int next_entry_id;

    public TCsymTable() {
        next_entry_id = 0;
        st = new HashMap<String, Attributes>();
    }

    public void add(String name, String type) {
        this.st.put(name, new Attributes(name, type, next_entry_id++));
    }

    public Attributes get(String id) {
        if (this.st.containsKey(id))
            return this.st.get(id);
        else
            return null;
    }

    public boolean containsId(String id) {
        return this.st.containsKey(id);
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
