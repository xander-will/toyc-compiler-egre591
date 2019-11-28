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
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import compilers.Parser;
import compilers.Lexer;
import compilers.CompilationErrorException;
import compilers.CodeGenerationException;

import globals.TCglobals;
import output.TCoutput;
import symTable.TCsymTable;
import codeGen.JVM.JVMCodeTemplate;
//import codegen.JVM.JVMcodeGenerator;
//import codegen.JVM.JVMtargetCode;

public class tc {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        try {
            processCommandLine(args);

            Lexer scanner = new TCscanner(TCglobals.inputFileName);
            Parser parser = new TCparser(scanner);
            TCglobals.ast = parser.parse();
            if (TCglobals.astDump) {
                TCoutput.dumpAST(TCglobals.ast);
            }

            TCglobals.codetemplate = new JVMCodeTemplate();
            TCglobals.symtable = new TCsymTable();
            String finished_code = TCglobals.ast.generateCode();

            if (TCglobals.verbose) {
                TCoutput.dumpAST(TCglobals.ast);
                TCoutput.dumpST(TCglobals.symtable);
                System.err.println(finished_code);
            } else {
                if (TCglobals.astDump) {
                    TCoutput.dumpAST(TCglobals.ast);
                }
                if (TCglobals.symDump) {
                    TCoutput.dumpST(TCglobals.symtable);
                }
                if (TCglobals.codeDump) {
                    System.err.println(finished_code);
                }
            }
            if (TCglobals.targetFileName != null) {
                try {
                    System.err.println("Output name is " + TCglobals.targetFileName);
                    FileWriter fw = new FileWriter(new File(TCglobals.targetFileName));
                    fw.append(finished_code);
                    fw.close();
                } catch (Exception ex) {
                    System.out.println("An error occured with the given filename.");
                }
            }

            /*
             * CodeGenerator gen = new JVMcodeGenerator(); TCglobals.objectcode =
             * gen.generateCode(TCglobals.ast);
             * ((JVMtargetCode)TCglobals.objectcode).writeCode(TCglobals.targetFileName);
             * 
             * if (TCglobals.verbose) { System.out.println();
             * TCoutput.dumpAST(TCglobals.ast); TCoutput.dumpST(TCglobals.symtable);
             * TCoutput.dumpCode(TCglobals.objectcode); }
             */

        } catch (UsageException e) {
        } catch (CompilationErrorException e) {
        } catch (CodeGenerationException e) {
        }
    }

    private static void processCommandLine(String[] args) {
        try {
            switch (args.length) {
            case 0:
                printUsageMessage();
                break;
            case 1:
                if (args[0].charAt(0) != '-')
                    TCglobals.inputFileName = args[0];
                else
                    printUsageMessage();
                break;

            default:
                List<String> argsList = Arrays.asList(args);
                TCglobals.inputFileName = argsList.get(argsList.size() - 1);
                if (argsList.contains("-abstract")) {
                    TCglobals.astDump = true;
                }
                if (argsList.contains("-class")) {
                    int index = argsList.indexOf("-class");
                    if (argsList.size() >= index + 1) {
                        TCglobals.outputClassFileName = argsList.get(index + 1);
                    }
                }
                if (argsList.contains("-code")) {
                    TCglobals.codeDump = true;
                }
                if (argsList.contains("-debug")) {
                    int index = argsList.indexOf("-debug");
                    if (argsList.size() >= index + 1) {
                        String debug = argsList.get(index + 1);
                        TCglobals.debug = Byte.parseByte(debug);
                    }
                }
                if (argsList.contains("-help")) {
                    printHelpMessage();
                }
                if (argsList.contains("-output")) {
                    int index = argsList.indexOf("-output");
                    if (argsList.size() >= index + 1) {
                        TCglobals.targetFileName = argsList.get(index + 1) + "." + TCglobals.ASM_FILE_EXTENSION;
                    }
                }
                if (!argsList.contains("-output")) {
                    TCglobals.outputClassFileName = getProgramName(TCglobals.inputFileName);
                    TCglobals.targetFileName = TCglobals.outputClassFileName + "." + TCglobals.ASM_FILE_EXTENSION;
                }
                if (argsList.contains("-symbol")) {
                    TCglobals.symDump = true;
                }

                if (argsList.contains("-verbose") || argsList.contains("-v")) {
                    TCglobals.verbose = true;
                }
                if (argsList.contains("-version")) {
                    System.out.println(TCglobals.VERSION);
                }
            }

        } catch (Exception e) {
            System.err.println("toycalc compiler command line problem!");
        }
    }

    private static void printHelpMessage() {

        // put this in TC output
        System.out.println("options:");
        System.out.println("\t-help:                  display a usage message");
        System.out.println("\t-debug <level>          display messages that aid in tracing the compilation process");
        System.out.println("\t\tLeveL:");
        System.out.println("\t\t\t\t0 - All messages");
        System.out.println("\t\t\t\t1 - Scanner messages only");
        System.out.println("\t\t\t\t2 - Parser messages only");
        System.out.println("\t-abstract               dump the abstract symbol tree");
        System.out.println("\t-symbol                 dump the symbol table(s)");
        System.out.println("\t-verbose or -v          display all information");
        System.out.println("\t-version                display the program version\n");
    }

    private static String getProgramName(String s) {
        String[] strs = s.split("\\.");
        strs = strs[0].split("\\/");
        return strs[1];
    }

    private static void printUsageMessage() {
        System.out.println("Usage: java parser.tc [-v] input_file");
        System.out.println("  where -v means verbose output");
        throw new UsageException();
    }

    private static class UsageException extends java.lang.RuntimeException {
        public UsageException() {
            super();
        }
    }

}
