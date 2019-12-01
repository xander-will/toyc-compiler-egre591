package abstractSyntax;

import abstractSyntax.Statement;
import output.TCoutput;
import globals.TCglobals;

public class NullStatement implements Statement {

    public String generateCode() {

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "");

        return "";
    }

    public String toString() {
        return "null()\n";
    }

}
