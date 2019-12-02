package symTable;

import compilers.SymbolTable;
import output.TCoutput;

import symTable.Attributes;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class TCsymTable implements SymbolTable {

    private HashMap<String, Attributes> st;
    private int next_entry_id;

    public TCsymTable() {
        next_entry_id = 0;
        st = new HashMap<String, Attributes>();
    }

    public void add(String name, String data_type, String sem_type) {
        if (!containsID(name))
            this.st.put(name, new Attributes(data_type, sem_type, next_entry_id++));
        else {
            TCoutput.reportSEMANTIC_ERROR("", data_type + " " + name + " has already been defined in this scope");
        }
    }

    public void add(String name, String data_type, String sem_type, int arg_num) {
        if (!containsID(name))
            this.st.put(name, new Attributes(data_type, sem_type, next_entry_id++, arg_num));
        else {
            TCoutput.reportSEMANTIC_ERROR("[SYMTABLE]", data_type + " " + name + " has already been defined in this scope");
        }
    }

    public boolean containsID(String id) {
        return this.st.containsKey(id);
    }

    public Attributes get(String id) {
        return this.st.get(id);
    }

    public ArrayList<String> getGlobals() {
        // put code to scrape names of all global variables and return them
        ArrayList<String> globals = new ArrayList<String>();
        
        for (String s : this.st.keySet()) {

            if (st.get(s).getSemanticType().toString().equals("VARIABLE")) {
                globals.add(s);
            }
            else if (st.get(s).getSemanticType().equals("FUNCTION")) {
                break;
            }
        }

        return globals;
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
}
