 /*
 *  EGRE 591
 *  tc.java --- 9/26/2019
 *  
 *  Written by Samuel Coppedge and Xander Will
 * 
 */

// D. Resler 4/2010

package parser;

import java.io.FileNotFoundException;
import java.io.IOException;

import compilers.Parser;
import compilers.Lexer;
import compilers.CodeGenerator;
import compilers.CompilationErrorException;
import compilers.CodeGenerationException;

import globals.TCglobals;
import output.TCoutput;
//import codegen.JVM.JVMcodeGenerator;
//import codegen.JVM.JVMtargetCode;

public class tc {

    public static void main(String [] args) throws FileNotFoundException,IOException {
        try {
            processCommandLine(args);

            Lexer scanner = new TCscanner(TCglobals.inputFileName);
            //Parser parser = new TCparser(scanner);
            //TCglobals.ast = parser.parse();
            

            while ((scanner.getToken().getTokenType()) != TCtoken.Tokens.EOF) 
            {
            	
            }
            
            /*CodeGenerator gen = new JVMcodeGenerator();
            TCglobals.objectcode = gen.generateCode(TCglobals.ast);
            ((JVMtargetCode)TCglobals.objectcode).writeCode(TCglobals.targetFileName);
            
            if (TCglobals.verbose) {
                System.out.println();
                TCoutput.dumpAST(TCglobals.ast);
                TCoutput.dumpST(TCglobals.symtable);
                TCoutput.dumpCode(TCglobals.objectcode);
            }*/
            
        } catch(UsageException e) { }
          catch(CompilationErrorException e) { }
          catch(CodeGenerationException e) { }
    }

    private static void processCommandLine(String [] args) {
        try {
            switch(args.length) {
                case 0: printUsageMessage(); break;
                case 1: if (args[0].charAt(0) != '-')
                          TCglobals.inputFileName = args[0];
                        else
                          printUsageMessage();
                        break;
                case 2: if (args[0].equals("-v")) {
                          TCglobals.verbose = true;
                          TCglobals.inputFileName = args[1];
                        } else printUsageMessage();
                        break;
                default: printUsageMessage();
            }
            TCglobals.outputClassFileName = getProgramName(TCglobals.inputFileName);
            TCglobals.targetFileName = TCglobals.outputClassFileName+"."+TCglobals.ASM_FILE_EXTENSION;
        } catch(Exception e) {
            System.err.println("toycalc compiler command line problem!");
        }
   }

    private static String getProgramName(String s) {
        String [] strs =  s.split("\\.");
        strs = strs[0].split("\\/");
        return strs[0];
    }

    private static void printUsageMessage() {
        System.out.println("Usage: java parser.tc [-v] input_file");
        System.out.println("  where -v means verbose output");
        throw new UsageException();
    }

    private static class UsageException extends java.lang.RuntimeException{
        public UsageException() { super(); }
    }

}
