package symTable;

import java.util.HashMap;

import compilers.AttributesInterface;
import compilers.SymbolTable;

public class Attributes implements AttributesInterface {
    public static enum SemanticType {
        VARIABLE, FUNCTION
    };

    private static HashMap<String, SemanticType> st_map;
    static {
        st_map = new HashMap<>();
        st_map.put("variable", SemanticType.VARIABLE);
        st_map.put("function", SemanticType.FUNCTION);
    }

    public static enum DataType {
        INT, CHAR
    };

    private static HashMap<String, DataType> dt_map;
    static {
        dt_map = new HashMap<>();
        dt_map.put("int", DataType.INT);
        dt_map.put("char", DataType.CHAR);
    }

    private SemanticType st;
    private DataType dt;
    private Integer id;
    private TCsymTable symbolTable = null;
    private int arg_num = 0;

    public Attributes(String dt, String st, int id) {
        this.st = st_map.get(st);
        this.dt = dt_map.get(dt);
        this.id = id;

        if (st == "function") {
            this.symbolTable = new TCsymTable();
        }
    }

    public Attributes(String dt, String st, int id, int arg_num) {
        this.st = st_map.get(st);
        this.dt = dt_map.get(dt);
        this.id = id;
        this.arg_num = arg_num;

        if (st == "function") {
            this.symbolTable = new TCsymTable();
        }
    }

    public boolean equals(Attributes at) {
        if (this.st == at.st && this.dt == at.dt && this.symbolTable == at.symbolTable) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SymbolTable getSymtable() {
        return symbolTable;
    }

    public int getID() {
        return id;
    }

    public int getArgNum() {
        return arg_num;
    }

    public String toString() {
        if (this.st != SemanticType.FUNCTION) {
            return "(" + id.toString() + ", " + this.st.toString() + ", " + this.dt.toString() + ")";
        } else {
            String s = "(" + this.st.toString() + ", " + this.dt.toString() + ")\n";
            for (String i : this.symbolTable.toString().split("\\R"))
                s += "\t" + i + "\n";
            return s;
        }
    }
}