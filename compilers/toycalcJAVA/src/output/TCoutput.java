// D. Resler 4/2010

package output;

import compilers.Lexer;
import compilers.AbstractSyntax;
import compilers.SymbolTable;
import compilers.TargetCode;
import compilers.CompilationErrorException;

import output.TCoutput;

public class TCoutput {

    public static void reportDEBUG(String prefix, String type, String message) {
        System.err.println(prefix + "[" + type + "] " + message);
    }

    public static void reportWARNING(Lexer l, String prefix, String message) {
        System.err.println(prefix + "%warning: " + message);
    }

    public static void reportSYNTAX_ERROR(Lexer l, String message) {
        int ln;
        String sep = ": ";
        System.err.println((ln = l.getLineNum()) + sep + l.getLine());
        System.err.println(pad((ln + "").length()) + pad(l.getPos() + sep.length() - (l.getLexeme().length()) + 1)
                + "^ %error: " + message);
        throw new CompilationErrorException();
    }

    public static void reportSEMANTIC_ERROR(Lexer l, String message) {
        int ln;
        String sep = ": ";
        System.err.println((ln = l.getLineNum()) + sep + l.getLine());
        System.err.println(pad((ln + "").length()) + pad(l.getPos() + sep.length() - (l.getLexeme().length()) + 1)
                + "^ %error: " + message);
        throw new CompilationErrorException();
    }

    public static void reportSEMANTIC_ERROR(String prefix, String message) {
        System.err.println(prefix + "%error: " + message);
        throw new CompilationErrorException();
    }

    public static void dumpAST(AbstractSyntax ast) {
        System.err.println("ABSTRACT SYNTAX TREE:\n" + ast);
    }

    public static void dumpST(SymbolTable st) {
        System.err.println("SYMBOL TABLE:\n" + st);
    }

    public static void dumpCode(TargetCode code) {
        System.err.println("TARGET CODE:\n" + code);
    }

    public static void printHelpMessage() {
        System.err.println("options:");
        System.err.println("\t-help:                  display a usage message");
        System.err.println("\t-debug <level>          display messages that aid in tracing the compilation process");
        System.err.println("\t\tLevel:");
        System.err.println("\t\t\t\t0 - All messages");
        System.err.println("\t\t\t\t1 - Scanner messages only");
        System.err.println("\t\t\t\t2 - Parser messages only");
        System.err.println("\t\t\t\t3 - Code generation messages only");
        System.err.println("\t-abstract               dump the abstract symbol tree");
        System.err.println("\t-symbol                 dump the symbol table(s)");
        System.err.println("\t-code                   dump the generated code");
        System.err.println("\t-verbose or -v          display all information");
        System.err.println("\t-version                display the program version\n");
    }

    private static String pad(int n) {
        String s = "";
        for (int i = 1; i < n; i++)
            s += " ";
        return s;
    }

}
