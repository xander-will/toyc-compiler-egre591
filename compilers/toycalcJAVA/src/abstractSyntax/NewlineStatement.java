package abstractSyntax;

import abstractSyntax.Statement;

import globals.TCglobals;

public class NewlineStatement implements Statement {

    public String generateCode() {
        return TCglobals.codetemplate.stringLit("\"\\n\"") + TCglobals.codetemplate.write("string");
    }

    public String toString() {
        return "newline()\n";
    }

}
