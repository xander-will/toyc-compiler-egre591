package abstractSyntax;

import abstractSyntax.Expression;
import codeGen.JVM.JVMCodeTemplate;
import globals.TCglobals;
import output.TCoutput;

public class Identifier implements Expression {

    private String name;

    public Identifier(String name) {
        this.name = name;
    }

    public int getID() {
        int id = -1;
        if (TCglobals.localsymtable != null) {
            try {
                id = TCglobals.localsymtable.get(name).getID();
            } catch (Exception e) {
            }
        }
        if (id == -1) {
            try {
                id = TCglobals.symtable.get(name).getID();
            } catch (Exception e) {
                TCoutput.reportSEMANTIC_ERROR("", "Call on variable " + name + " that was not initialized.");
            }
        }
        return id;
    }

    public String generateLoad() {
        int id = getID();
        return TCglobals.codetemplate.loadVar(id);
    }

    public String generateStore() {
        int id = getID();
        TCglobals.codetemplate.function("", "", "");
        return TCglobals.codetemplate.storeVar(id);
    }

    public String generateCode() {
        return generateLoad();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "id(" + name + ")\n";
    }
}