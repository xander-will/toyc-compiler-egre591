package abstractSyntax;

import abstractSyntax.Statement;

public class NullStatement implements Statement {

    public String generateCode() {return"";}

    public String toString() {
        return "null()\n";
    }

}
