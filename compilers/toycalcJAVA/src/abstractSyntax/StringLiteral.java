package abstractSyntax;

import abstractSyntax.Expression;

import globals.TCglobals;
import output.TCoutput;

public class StringLiteral implements Expression {

    private String stringBody;

    public StringLiteral(String s) {
        this.stringBody = s;
    }

    public String toString() {
        return "stringLit(" + stringBody + ")\n";
    }

    public String generateCode() {
        String s = TCglobals.codetemplate.stringLit(stringBody);

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

        return s;
    }
}