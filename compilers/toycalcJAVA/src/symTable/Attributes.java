package symTable;

public class Attributes {
    public static enum SemanticType {
        VARIABLE, FUNCTION
    };

    public static enum DataType {
        INT, CHAR
    };

    private SemanticType st;
    private DataType dt;
    private TCsymTable symbolTable = null;

    public Attributes(SemanticType st, DataType dt) {
        this.st = st;
        this.dt = dt;

        if (st == FUNCTION) {
            this.symbolTable = new TCsymTable();
        }
    }

    @Override
    public boolean equals(Attributes at) {
        if (this.st == at.st && this.dt == at.dt && this.symbolTable == at.symbolTable) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.st != SemanticType.FUNCTION) {
            return "(" + this.st.toString() + ", " + this.dt.toString() + ")";
        } else {
            return "(" + this.st.toString() + ", " + this.dt.toString() + ")\n" + this.symbolTable.toString();
        }
    }
}