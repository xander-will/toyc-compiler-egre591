//
//  EGRE 591
//
//  "TCscanner.java"
//
//  Xander Will / Grant Coppedge
//  September 2019
//

// parts from D. Resler 3/10

package parser;

import compilers.Lexer;
import compilers.Token;

import globals.TCglobals;
import output.TCoutput;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TCscanner implements Lexer {

    private Scanner s;
    private String line = null;
    private static int pos = 0;
    private static int lineNum = 0;
    private String lexeme = "";

    private static final char EOFCHAR = '\0'; // arbitrary non-printing char

    private static Character charBuff = Character.MIN_VALUE;

    public TCscanner(String filename) throws FileNotFoundException {
        TCglobals.inputFileName = filename;
        try {
            s = new Scanner(new File(filename));
            charBuff = getChar();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("source code file '" + filename + "' does not exist");
        }
    }

    private String getNextLine() {
        String line = s.nextLine() + " "; // adding a space aids error reporting at end of line
        pos = 0;
        lineNum++;
        // if (TCglobals.verbose)
        // TCoutput.reportDEBUG("","input",lineNum+": "+line);
        return line;
    }

    private void refresh() {
        lexeme = lexeme + charBuff;
        charBuff = getChar();
    }

    private char getChar() {
        if (line == null || pos > line.length() - 1) {
            if (!s.hasNext()) {
                return EOFCHAR;
            }

            line = getNextLine();
        }

        return line.charAt(pos++);
    }

    public Token getToken() {
        Token t = StateStart();
        while (t.getTokenType().equals(TCtoken.Tokens.NONE)) {
            t = StateStart();
        }

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 1) {
            String prefix = lineNum + "." + pos + " ";
            TCoutput.reportDEBUG(prefix, "SCANNER", t.toString());
        }

        return t;
    }

    private Token StateStart() { // start state
        lexeme = "";

        while (Character.isWhitespace(charBuff))
            charBuff = getChar();

        if (Character.isLetter(charBuff) || charBuff == '_')
            return StateID(); // keyword or id state

        if (charBuff == EOFCHAR)
            return new TCtoken(TCtoken.Tokens.EOF);

        if (Character.isDigit(charBuff))
            return StateNumber(); // number state

        if (TCtoken.single_chars.containsKey(charBuff)) {
            TCtoken returnToken = new TCtoken(TCtoken.single_chars.get(charBuff), charBuff.toString());
            refresh();
            return returnToken;
        }

        switch (charBuff) {
        case '/':
            return StateForwardSlash();
        case '\'':
            return StateCharacter();
        case '"':
            return StateString();
        case '|':
            return StateOr();
        case '=':
            return StateEquals();
        case '!':
            return StateNot();
        case '<':
        case '>':
            return StateRelational();
        case '&':
            return StateAnd();
        default:
            refresh();
            return new TCtoken(TCtoken.Tokens.ERROR, lexeme);
        }
    }

    private Token StateID() { // keyword or ID state
        refresh();

        while (Character.isLetterOrDigit(charBuff) || charBuff == '_')
            refresh();

        if (TCtoken.keywords.containsKey(lexeme)) // keyword
            return new TCtoken(TCtoken.keywords.get(lexeme), lexeme);
        else // ID
            return new TCtoken(TCtoken.Tokens.ID, lexeme);
    }

    private Token StateForwardSlash() {
        refresh();

        switch (charBuff) {
        case '/': // single-line comment
            return StateSingleComment();
        case '*': // multi-line comment
            return StateMultiComment();
        default: // division operator
            return new TCtoken(TCtoken.Tokens.MULOP, lexeme);
        }
    }

    private Token StateSingleComment() { // single-line comment

        while (pos < line.length()) {
            refresh();
        }

        return new TCtoken(TCtoken.Tokens.NONE);
    }

    private Token StateMultiComment() { // multi-line comment
        while ((charBuff = getChar()) != EOFCHAR) {
            if (charBuff == '/') {
                if ((charBuff = getChar()) == '*') {
                    StateMultiComment();
                }
            }
            if (charBuff == '*') {
                if ((charBuff = getChar()) == '/') {
                    charBuff = getChar();
                    return new TCtoken(TCtoken.Tokens.NONE);
                }
            }
        }

        return new TCtoken(TCtoken.Tokens.ERROR);
    }

    private Token StateNumber() { // number start state
        refresh();

        while (Character.isDigit(charBuff)) {
            refresh();
        }

        switch (charBuff) {
        case '.':
            return StateDecimal();
        case 'e':
        case 'E':
            return StateExponent();
        default:
            return new TCtoken(TCtoken.Tokens.NUMBER, lexeme);
        }
    }

    private Token StateDecimal() { // decimal fraction state
        refresh();

        if (!Character.isDigit(charBuff)) {
            refresh();
            return new TCtoken(TCtoken.Tokens.ERROR, lexeme); // quickie solution... we should add a custom exception
                                                              // class for better error messages tho
        }

        while (Character.isDigit(charBuff))
            refresh();

        switch (charBuff) {
        case 'e':
        case 'E':
            return StateExponent();
        default:
            return new TCtoken(TCtoken.Tokens.NUMBER, lexeme);
        }
    }

    private Token StateExponent() { // exponent state
        refresh();

        switch (charBuff) {
        case '+':
        case '-':
            refresh();
        default:
            if (!Character.isDigit(charBuff)) {
                refresh();
                return new TCtoken(TCtoken.Tokens.ERROR, lexeme);
            }
        }

        while (Character.isDigit(charBuff))
            refresh();

        return new TCtoken(TCtoken.Tokens.NUMBER, lexeme);
    }

    private Token StateCharacter() { // charliteral state
        refresh();

        switch (charBuff) {
        case '\n':
            return new TCtoken(TCtoken.Tokens.ERROR, lexeme);
        default:
            refresh();
            if (charBuff != '\'') {
                refresh();
                return new TCtoken(TCtoken.Tokens.ERROR, lexeme);
            }
        case '\'':
            refresh();
            return new TCtoken(TCtoken.Tokens.CHARLITERAL, lexeme);
        }
    }

    private Token StateString() { // string state
        while (charBuff != '\n') {
            refresh();
            if (charBuff == '"') {
                refresh();
                return new TCtoken(TCtoken.Tokens.STRING, lexeme);
            }
        }

        refresh();
        return new TCtoken(TCtoken.Tokens.ERROR, lexeme);
    }

    private Token StateOr() { // boolean or operator (||)
        refresh();

        if (charBuff == '|') {
            refresh();
            return new TCtoken(TCtoken.Tokens.ADDOP, lexeme);
        } else
            return new TCtoken(TCtoken.Tokens.ERROR, lexeme);
    }

    private Token StateAnd() { // boolean and operator (&&)
        refresh();

        if (charBuff == '&') {
            refresh();
            return new TCtoken(TCtoken.Tokens.MULOP, lexeme);
        } else
            return new TCtoken(TCtoken.Tokens.ERROR, lexeme);
    }

    private Token StateEquals() { // assign and boolean equals (= and ==)
        refresh();

        if (charBuff == '=') {
            refresh();
            return new TCtoken(TCtoken.Tokens.RELOP, lexeme);
        } else
            return new TCtoken(TCtoken.Tokens.ASSIGNOP, lexeme);
    }

    private Token StateNot() { // negation and boolean not equals (! and !=)
        refresh();

        if (charBuff == '=') {
            refresh();
            return new TCtoken(TCtoken.Tokens.RELOP, lexeme);
        } else {
            return new TCtoken(TCtoken.Tokens.NOT, lexeme);
        }
    }

    private Token StateRelational() { // relational operators (>, <, >=, <=)
        refresh();

        if (charBuff == '=') {
            refresh();
            return new TCtoken(TCtoken.Tokens.RELOP, lexeme);
        } else
            return new TCtoken(TCtoken.Tokens.RELOP, lexeme);
    }

    public String getLine() {
        return line;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getPos() {
        return pos;
    }

    public int getLineNum() {
        return lineNum;
    }

}
