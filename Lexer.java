package compilers;

public interface Lexer {
    public Token getToken();

    // for error reporting:
    public String getLine();
    public String getLexeme();
    public int getLineNum();
    public int getPos();
}
