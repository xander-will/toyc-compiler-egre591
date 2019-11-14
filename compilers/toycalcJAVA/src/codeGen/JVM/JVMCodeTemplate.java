package codeGen.JVM;

import compilers.CodeTemplate;

import globals.TCglobals;

public class JVMCodeTemplate implements CodeTemplate {

    // please keep in alphabetical order

    public String directive(String dir, String arg) {
        return "." + dir + " " + arg + "\n";
    }

    public String init() {
        String s = directive("source", TCglobals.inputFileName);
        s += directive("class", "public " + TCglobals.outputClassFileName);
        s += directive("super", "java/lang/Object") + "\n";
        
        String body = functionHeader(1, 1);
        body += "\taload_0\n";
        body += "\tinvokespecial java/land/Object/<init>()V\n";
        body += "\treturn\n";

        s += functionWrapper("public <init>", "", body) + "\n";
        return s;
    }

    public String function(String name, String args, String body) {
        String fh = functionHeader(10, 10);     // hardcoded for convenience (toyCalc does this as well)
        return functionWrapper(name, args, fh + body);
    }

    private String functionWrapper(String name, String args, String body) {
        String s = ".method " + name + "(" + args + ")V\n";
        s += body;
        s += ".end method\n";
        return s;
    }

    private String functionHeader(Integer stack, Integer locals) {
        String s = "\t.limit stack " + stack.toString() + "\n";
        s += "\t.limit locals " + locals.toString() + "\n";
        return s;
    }

}