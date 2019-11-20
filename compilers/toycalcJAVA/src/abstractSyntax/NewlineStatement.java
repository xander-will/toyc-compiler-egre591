package abstractSyntax;

import abstractSyntax.Statement;

public class NewlineStatement implements Statement {

    public String generateCode() {
        return stringLit("\n") + write("string");
    }

    public String toString() {
        return "newline()\n";
    }

}
