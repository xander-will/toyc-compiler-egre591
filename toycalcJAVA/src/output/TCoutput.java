// D. Resler 4/2010

package output;

import compilers.Lexer;
import compilers.AbstractSyntax;
import compilers.SymbolTable;
import compilers.TargetCode;
import compilers.CompilationErrorException;

public class TCoutput {

    public static void reportDEBUG(String prefix, String type, String message) {
        System.err.println(prefix+"["+type+"] "+ message);
    }

    public static void reportWARNING(Lexer l,String prefix, String message) {
        System.err.println(prefix+"%warning: "+ message);
    }

    public static void reportSYNTAX_ERROR(Lexer l, String message) {
        int ln;
        String sep = ": ";
        System.err.println((ln=l.getLineNum())+sep+l.getLine());
        System.err.println(pad((ln+"").length())+
                           pad(l.getPos()+sep.length()-(l.getLexeme().length())+1)+  
                           "^ %error: "+ message);
        throw new CompilationErrorException();
    }

    public static void reportSEMANTIC_ERROR(Lexer l,String message) {
        int ln;
        String sep = ": ";
        System.err.println((ln=l.getLineNum())+sep+l.getLine());
        System.err.println(pad((ln+"").length())+
                           pad(l.getPos()+sep.length()-(l.getLexeme().length())+1)+  
                           "^ %error: "+ message);
        throw new CompilationErrorException();
    }

    public static void reportSEMANTIC_ERROR(String prefix, String message) {
        System.err.println(prefix+"%error: "+ message);
        throw new CompilationErrorException();
    }

    public static void dumpAST(AbstractSyntax ast) {
        System.err.println("abstract syntax tree:\n"+ast);
    }

    public static void dumpST(SymbolTable st) {
        System.err.println("symbol table:\n"+st);
    }

    public static void dumpCode(TargetCode code) {
        System.err.println("code:\n"+code);
    }

    private static String pad(int n) {
        String s = ""; 
        for (int i = 1; i < n; i++) s+=" ";
        return s;
    }

}
