package globals;

import compilers.AbstractSyntax;
import compilers.SymbolTable;
import compilers.TargetCode;
import compilers.CodeTemplate;

public class TCglobals {

    public static String inputFileName = null;
    public static String outputClassFileName = null;
    public static String targetFileName = null;
    public static final String ASM_FILE_EXTENSION = "j";

    public static AbstractSyntax ast = null;
    public static SymbolTable symtable = null;
    public static SymbolTable localsymtable = null;
    public static CodeTemplate codetemplate = null;
    public static TargetCode objectcode = null;

    public final static String COMPILER = "toycalc";
    public final static String VERSION = "v1.2";
    public final static String AUTHOR = "Samuel Coppedge / Xander Will --- Provided by Dan Resler";

    public static boolean verbose = false;
    public static boolean astDump = false;
    public static boolean symDump = false;
    public static boolean codeDump = false;

    public static byte debug = 127;

}
