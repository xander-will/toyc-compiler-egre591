package abstractSyntax;

import abstractSyntax.Expression;
<<<<<<< Updated upstream
=======
import codeGen.JVM.JVMCodeTemplate;
import compilers.CodeTemplate;
import globals.TCglobals;
import output.TCoutput;
>>>>>>> Stashed changes

public class Identifier implements Expression {

    private String name;
    private JVMCodeTemplate ct = new JVMCodeTemplate();

    public Identifier(String name) {
        this.name = name;
    }

<<<<<<< Updated upstream
=======
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
        return ct.loadVar(id);
    }

    public String generateStore() {
        int id = getID();
        return ct.storeVar(id);
    }

    @Override
    public String generateCode() {
        return generateLoad();
    }

>>>>>>> Stashed changes
    public String toString() {
        return "id(" + name + ")";
    }
}