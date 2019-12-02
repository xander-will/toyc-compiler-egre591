package abstractSyntax;

import abstractSyntax.Expression;
import globals.TCglobals;
import output.TCoutput;

public class Identifier implements Expression {

    private String name;
    private final int GLOBAL = -1;

    public Identifier(String name) {
        this.name = name;
    }

    public int getID() {
        int id = GLOBAL;
        if (TCglobals.localsymtable != null) {
            try {
                id = TCglobals.localsymtable.get(name).getID();
            } catch (Exception e) {
            }
        }
        if (id == GLOBAL) {
            try {
                TCglobals.symtable.get(name);
            } catch (Exception e) {
                TCoutput.reportSEMANTIC_ERROR("", "Call on variable " + name + " that was not initialized.");
            }
        }
        return id;
    }

    public String generateLoad() {
        int id = getID();
        if (id == GLOBAL)
            return TCglobals.codetemplate.globalload(name)
        else
            return TCglobals.codetemplate.load(id);
    }

    public String generateStore() {
        int id = getID();
        if (id == GLOBAL)
            return TCglobals.codetemplate.globalstore(name)
        else
            return TCglobals.codetemplate.store(id);
    }

    public String generateCode() {
        String s = generateLoad();

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

        return s;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "id(" + name + ")\n";
    }
}