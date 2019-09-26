package globals;

import compilers.AbstractSyntax;
import compilers.SymbolTable;
import compilers.TargetCode;

public class TCglobals {

    public static String inputFileName=null;
    public static String outputClassFileName=null;
    public static String targetFileName=null;
    public static final String ASM_FILE_EXTENSION = "j";

    public static AbstractSyntax ast = null; 
    public static SymbolTable symtable = null; 
    public static TargetCode objectcode = null;

    public final static String COMPILER = "toycalc";
    public final static String VERSION = "v1.0"; 
    public final static String AUTHOR = "D. Resler";

    public static boolean verbose = false; 

}
