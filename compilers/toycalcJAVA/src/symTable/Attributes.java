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
        dt_map.put("int", SemanticType.INT);
        dt_map.put("char", SemanticType.CHAR);
    }

    private SemanticType st;
    private DataType dt;
    private int id;
    private TCsymTable symbolTable = null;

    public Attributes(String st, String dt, int id) {
        this.st = st_map.get(st);
        this.dt = dt_map.get(dt);
        this.id = id;

        if (this.st == SemanticType.FUNCTION) {
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

    public SymbolTable getSymTable() {
        return symbolTable;
    }

    public int getID() {
        return id;
    }

    public String toString() {
        if (this.st != SemanticType.FUNCTION) {
            return "(" + this.st.toString() + ", " + this.dt.toString() + ")";
        } else {
            return "(" + this.st.toString() + ", " + this.dt.toString() + ")\n" + this.symbolTable.toString();
        }
    }
}