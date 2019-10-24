 /*
  * EGRE 591
  * TCtoken.java --- 9/26/2019
  *  
  * Written by Samuel Coppedge and Xander Will
  * 
  */


package parser;

import compilers.Token;
import java.util.HashMap; 
import java.util.Map; 

public class TCtoken implements Token {

    public static enum Tokens { INT, CHAR, RETURN, IF, ELSE, FOR,
                                DO, WHILE, SWITCH, CASE, DEFAULT, WRITE,
                                READ, CONTINUE, BREAK, NEWLINE, ID,
                                NUMBER, CHARLITERAL, STRING, RELOP,
                                ADDOP, MULOP, ASSIGNOP, LPAREN, RPAREN,
                                LCURLY, RCURLY, LBRACKET, RBRACKET,
                                COMMA, SEMICOLON, NOT, COLON, NONE, ERROR, EOF };

    public static HashMap<String, Tokens> keywords;
    static {
        keywords = new HashMap<String, Tokens>();
        keywords.put("int", Tokens.INT);
        keywords.put("char", Tokens.CHAR);
        keywords.put("return", Tokens.RETURN);
        keywords.put("if", Tokens.IF);
        keywords.put("else", Tokens.ELSE);
        keywords.put("for", Tokens.FOR);
        keywords.put("do", Tokens.DO);
        keywords.put("while", Tokens.WHILE);
        keywords.put("switch", Tokens.SWITCH);
        keywords.put("case", Tokens.CASE);
        keywords.put("default", Tokens.DEFAULT);
        keywords.put("write", Tokens.WRITE);
        keywords.put("read", Tokens.READ);
        keywords.put("continue", Tokens.CONTINUE);
        keywords.put("break", Tokens.BREAK);
        keywords.put("newline", Tokens.NEWLINE);
    }

    public static HashMap<Character, Tokens> single_chars;
    static {
        single_chars = new HashMap<Character, Tokens>();
        single_chars.put('+', Tokens.ADDOP);
        single_chars.put('-', Tokens.ADDOP);
        single_chars.put('*', Tokens.MULOP);
        single_chars.put('%', Tokens.MULOP);
        single_chars.put('(', Tokens.LPAREN);
        single_chars.put(')', Tokens.RPAREN);
        single_chars.put('{', Tokens.LCURLY);
        single_chars.put('}', Tokens.RCURLY);
        single_chars.put('[', Tokens.RBRACKET);
        single_chars.put(']', Tokens.LBRACKET);
        single_chars.put(',', Tokens.COMMA);
        single_chars.put(';', Tokens.SEMICOLON);
        single_chars.put(':', Tokens.COLON);
    }

    private Tokens tokenType;
    private String lexeme;

    public TCtoken() { 
        tokenType = Tokens.NONE; 
    }
    public TCtoken(Tokens t) { 
        tokenType = t; 
        lexeme = null; 
    }
    public TCtoken(Tokens t, String s) { 
        tokenType = t; 
        lexeme = s; 
    }

    public Tokens getTokenType() { 
        return tokenType; 
    }
    public String getLexeme() { 
        return lexeme; 
    }

    public boolean equals(Object t) {
        TCtoken tk = (TCtoken)t;
        return ( (tk.tokenType == tokenType) &&
                 ( (tk.lexeme == null && lexeme == null) ||
                   (tk.lexeme != null && tk.lexeme.equals(lexeme)) ) );
    }

    public String toString() {
        return "(<" + tokenType.name() + ">, \"" + lexeme + "\")";
    }

}
