package abstractSyntax;

import abstractSyntax.Statement;

public class NewlineStatement implements Statement {

    public String generateCode() {
        return "";
    }

    public String toString() {
        return "newline()\n";
    }

}
