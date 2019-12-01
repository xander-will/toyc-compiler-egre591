package abstractSyntax;

import abstractSyntax.Statement;

import globals.TCglobals;
import output.TCoutput;

public class NewlineStatement implements Statement {

    public String generateCode() {
        String s = TCglobals.codetemplate.stringLit("\"\\n\"") + TCglobals.codetemplate.write("string");

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

        return TCglobals.codetemplate.stringLit("\"\\n\"") + TCglobals.codetemplate.write("string");
    }

    public String toString() {
        return "newline()\n";
    }

}
